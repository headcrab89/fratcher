package com.webengineering.fratcher.text;

import com.webengineering.fratcher.match.Match;
import com.webengineering.fratcher.match.MatchService;
import com.webengineering.fratcher.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TextService {
    private static final Logger LOG = LoggerFactory.getLogger(TextService.class);

    @Autowired
    private TextRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private MatchService matchService;

    /**
     * A List of all Text the current user has not seen yet
     * @return A List of new Text
     */
    public Iterable<Text> getNewTexts() {
        LOG.info("Returning fresh texts for user={}", userService.getCurrentUser().getUserName());

        Long currentUserId = userService.getCurrentUser().getId();
        List<Match> matches = matchService.getMatches(currentUserId);
        List<Text> texts = repository.findAll();

        // Returns all Text that are not in the matches from the current user
        return texts.stream()
                .filter(text ->  text.getAuthor().getId() != currentUserId
                        && matches.stream().noneMatch(match -> text.getAuthor().equals(match.getMatchUser())
                        || text.getAuthor().equals(match.getInitUser())))
                .collect(Collectors.toList());
    }

    /**
     * Returns with a text with the matching user id
     * @param userId
     * @return Text
     */
    public Text getTextByUserId(Long userId) {
        LOG.info("Retrieving text from user with id={}", userId);
        Text userText = repository.findByUserId(userId);

        return userText;
    }

    /**
     * Add or replace the Text from the current user
     * @param newText
     * @return text id
     */
    public long addOrReplaceText(Text newText) {
        // If the current user has no text he can create one, otherwise the text will be replaced
        Text text = repository.findByUser(userService.getCurrentUser());

        if (text == null) {
            LOG.info("Adding text. user={}, text={}", userService.getCurrentUser().getUserName(), newText.getUserText());
            newText.setAuthor(userService.getCurrentUser());
            repository.save(newText);

            return newText.getId();
        } else {
            LOG.info("Replacing text. user={}, text={}", userService.getCurrentUser().getUserName(), newText.getUserText());
            text.setUserText(newText.getUserText());
            text.setCreatedAt(new Date());

            repository.save(text);

            return text.getId();
        }
    }

    /**
     * Returns text with the matching Id
     * @param id
     * @return Text matching the text id
     */
    public Text getText(Long id) {
        LOG.info("Retrieving text. user={}, id={}", userService.getCurrentUser().getUserName(), id);
        return repository.findOne(id);
    }

    /**
     * Delete User text
     * @param id
     */
    public void deleteText(Long id) {
        // Validate that user is allowed to delete his text.
        Text text = repository.findOne(id);
        if (!text.getAuthor().equals(userService.getCurrentUser())) {
            LOG.info("Deleting text not allowed. user={}, id={}", userService.getCurrentUser().getUserName(), id);
            throw new IllegalStateException("User not allowed to delete text");
        }
        LOG.info("Deleting text. user={}, id={}", userService.getCurrentUser().getUserName(), id);

        repository.delete(id);
    }
}
