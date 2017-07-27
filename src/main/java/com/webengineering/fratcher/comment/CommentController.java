package com.webengineering.fratcher.comment;

import com.webengineering.fratcher.user.UserService;
import com.webengineering.fratcher.util.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    private static class CommentCreated {
        public String url;
    }

    private static class NewComment {
        public Long matchId;
        public String text;
    }

    @Autowired
    private AddressService addressService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/match/{matchId}/comment/{id}", method = RequestMethod.GET)
    public Comment getComment(@PathVariable Long id) {
        return commentService.getComment(id);
    }

    @RequestMapping(value = "/api/match/{matchId}/comment", method = RequestMethod.POST)
    public ResponseEntity<CommentCreated> addComment(@PathVariable Long matchid, @RequestBody NewComment newComment) {
        if (userService.isAnonymous()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Long id = commentService.addComment(newComment.matchId, newComment.text);
        CommentCreated commentCreated = new CommentCreated();
        commentCreated.url = addressService.getServerURL() + "/api/match/" + matchid + "/comment/" + id;
        return ResponseEntity.ok(commentCreated);
    }

    @RequestMapping(value = "/api/match/{matchId}/comment/{id}", method = RequestMethod.POST)
    public void editComment(@PathVariable Long id, @RequestBody Comment comment) {
        commentService.update(id, comment);
    }

    @RequestMapping(value = "/api/match/{matchId}/comment/{id}", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
