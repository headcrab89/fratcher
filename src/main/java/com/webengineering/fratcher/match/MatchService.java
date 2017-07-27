package com.webengineering.fratcher.match;

import com.webengineering.fratcher.comment.Comment;
import com.webengineering.fratcher.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {
    private static final Logger LOG = LoggerFactory.getLogger(MatchService.class);

    @Autowired
    private MatchRepository repository;

    @Autowired
    private UserService userService;

    public Iterable<Match> getMatches (Long userId) {
        return repository.findByUserId(userId);
    }

    public void addMatch (Match newMatch) {
        Match match = repository.findMatchForUsers(userService.getCurrentUser(), newMatch.getSecondUser());

        if (match == null) {
            newMatch.setFirstUser(userService.getCurrentUser());
            newMatch.setBothMatching(false);

            repository.save(newMatch);
        } else {
            if (!match.isBothMatching()) {
                match.setBothMatching(true);
                repository.save(match);
            }
        }
    }


    public void removeComment(Comment comment) {
        LOG.debug("Trying to remove comment. id={}", comment.getId());
        Match match = repository.findMatchForComment(comment);
        if (match == null) {
            throw new IllegalArgumentException("No match found for comment");
        }
        match.getComments().remove(comment);
        repository.save(match);
    }

    /**
     * Append new comment to an existing match.
     *
     * @param id      id of the match
     * @param comment comment to append
     */
    public void addComment(Long id, Comment comment) {
        LOG.info("Adding comment to match. user={}, id={}, commentId={}", userService.getCurrentUser().getEmail(),
                id, comment.getId());
        Match match = repository.findOne(id);
        if (match == null) {
            throw new IllegalArgumentException("Match not found. id=" + id);
        }

        match.getComments().add(comment);
        repository.save(match);
    }
}
