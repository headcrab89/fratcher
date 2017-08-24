package com.webengineering.fratcher.comment;

import com.webengineering.fratcher.match.MatchService;
import com.webengineering.fratcher.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CommentService {
    private static final Logger LOG = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    private CommentRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private MatchService matchService;

    /**
     * Add a comment to an existing match.
     *
     * @param matchId id of a match
     * @param text   text of the comment
     * @return id of the corresponding comment
     */
    @Transactional
    public Long addComment(Long matchId, String text) {
        LOG.info("Write comment. user={}, id={}", userService.getCurrentUser().getUserName(), matchId);
        // Persist comment.
        Comment comment = new Comment();
        comment.setText(text);
        comment.setAuthor(userService.getCurrentUser());
        repository.save(comment);

        // Append technically to match.
        matchService.addComment(matchId, comment);

        return comment.getId();
    }


    /**
     * Return a single comment.
     *
     * @param id comment id
     * @return a comment
     */
    public Comment getComment(Long id) {
        LOG.info("Retrieving comment. user={}, id={}", userService.getCurrentUser().getUserName(), id);
        return repository.findOne(id);
    }
}
