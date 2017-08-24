package com.webengineering.fratcher.comment;

import com.webengineering.fratcher.match.Match;
import com.webengineering.fratcher.match.MatchService;
import com.webengineering.fratcher.match.MatchStatus;
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
        public String text;
    }

    @Autowired
    private AddressService addressService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private MatchService matchService;

    @RequestMapping(value = "/api/match/{matchId}/comment/{id}", method = RequestMethod.GET)
    public ResponseEntity<Comment> getComment(@PathVariable Long matchId, @PathVariable Long id) {
        Match match = matchService.getMatch(matchId);

        if (match == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Since only a BOTH_LIKE-Match has comments an user can be either an initUser or a matchUser to see his comments
        if (match.getMatchStatus() != MatchStatus.BOTH_LIKE &&
                !(match.getInitUser().equals(userService.getCurrentUser()) ^ !match.getMatchUser().equals(userService.getCurrentUser()))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(commentService.getComment(id));
    }

    @RequestMapping(value = "/api/match/{matchId}/comment", method = RequestMethod.POST)
    public ResponseEntity<CommentCreated> addComment(@PathVariable Long matchId, @RequestBody NewComment newComment) {
        if (userService.isAnonymous()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Long id = commentService.addComment(matchId, newComment.text);
        CommentCreated commentCreated = new CommentCreated();
        commentCreated.url = addressService.getServerURL() + "/api/match/" + matchId + "/comment/" + id;
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
