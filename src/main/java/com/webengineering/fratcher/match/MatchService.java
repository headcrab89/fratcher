package com.webengineering.fratcher.match;

import com.webengineering.fratcher.comment.Comment;
import com.webengineering.fratcher.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {
    private static final Logger LOG = LoggerFactory.getLogger(MatchService.class);

    @Autowired
    private MatchRepository repository;

    @Autowired
    private UserService userService;

    public List<Match> getMatches (Long userId) {
        return repository.findByUserId(userId);
    }

    public Match addMatch (Match newMatch) {
        newMatch.setInitUser(userService.getCurrentUser());

        switch (newMatch.getMatchStatus()) {
            case DISLIKE:
                LOG.info("Add dislike. initUser={}, matchUser={}", userService.getCurrentUser().getEmail(), newMatch.getMatchUser().getEmail());
                repository.save(newMatch);
                return newMatch;
            case LIKE:
                // check if the other user already likes this user
                Match otherMatch = repository.findOtherMatch(userService.getCurrentUser(), newMatch.getMatchUser());

                if (otherMatch == null) {
                    LOG.info("Add like. initUser={}, matchUser={}", userService.getCurrentUser().getEmail(), newMatch.getMatchUser().getEmail());
                    repository.save(newMatch);

                    return newMatch;
                } else {
                    LOG.info("Found a match. initUser={}, matchUser={}", userService.getCurrentUser().getEmail(), newMatch.getMatchUser().getEmail());
                    otherMatch.setMatchStatus(MatchStatus.BOTH_LIKE);
                    repository.save(otherMatch);
                    return otherMatch;
                }

            default:
                LOG.info("Don't add match. initUser={}, matchUser={}", userService.getCurrentUser().getEmail(), newMatch.getMatchUser().getEmail());
                return null;
        }
    }

    public Match getMatch(Long id) {
        LOG.info("Retrieving match. user={}, id={}", userService.getCurrentUser().getEmail(), id);
        return repository.findOne(id);
    }

    public void deleteMatch (Long id) {
        LOG.info("Deleting match. user={}, id={}", userService.getCurrentUser().getEmail(), id);
        repository.delete(id);
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
