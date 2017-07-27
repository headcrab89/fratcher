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
     * Remove a single comment.
     *
     * @param id the comments's id.
     */
    public void deleteComment(Long id) {
        // Validate that user is allowed to delete comment.
        Comment comment = repository.findOne(id);
        if (!comment.getAuthor().equals(userService.getCurrentUser())) {
            LOG.info("Deleting comment not allowed. user={}, id={}", userService.getCurrentUser().getEmail(), id);
            throw new IllegalStateException("User not allowed to delete comment");
        }
        LOG.info("Deleting comment. user={}, id={}", userService.getCurrentUser().getEmail(), id);

        matchService.removeComment(comment);
    }

    public void update(Long id, Comment updateComment) {
        // Validate that user is allowed to update comment.
        Comment comment = repository.findOne(id);
        if (!comment.getAuthor().equals(userService.getCurrentUser())) {
            LOG.info("Updating comment not allowed. user={}, id={}", userService.getCurrentUser().getEmail(), id);
            throw new IllegalStateException("User not allowed to update comment");
        }
        LOG.info("Updating comment. user={}, id={}", userService.getCurrentUser().getEmail(), id);

        comment.setText(updateComment.getText());
        repository.save(comment);
    }

    /**
     * Add a comment to an existing match.
     *
     * @param matchId id of a match
     * @param text   text of the comment
     * @return id of the corresponding comment
     */
    @Transactional
    public Long addComment(Long matchId, String text) {
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
        LOG.info("Retrieving comment. user={}, id={}", userService.getCurrentUser().getEmail(), id);
        return repository.findOne(id);
    }
}
