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

    public Iterable<Text> getNewTexts() {
        LOG.info("Returning fresh texts for user={}", userService.getCurrentUser().getEmail());

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

    public long addOrReplaceText(Text newText) {
        // If the current user has no text he can create one, otherwise the text will be replaced
        Text text = repository.findByUser(userService.getCurrentUser());

        if (text == null) {
            LOG.info("Adding text. user={}, text={}", userService.getCurrentUser().getEmail(), newText.getUserText());
            newText.setAuthor(userService.getCurrentUser());
            repository.save(newText);

            return newText.getId();
        } else {
            LOG.info("Replacing text. user={}, text={}", userService.getCurrentUser().getEmail(), newText.getUserText());
            text.setUserText(newText.getUserText());
            text.setCreatedAt(new Date());

            repository.save(text);

            return text.getId();
        }
    }

    public Text getText(Long id) {
        LOG.info("Retrieving text. user={}, id={}", userService.getCurrentUser().getEmail(), id);
        return repository.findOne(id);
    }

    public void deleteText(Long id) {
        // Validate that user is allowed to delete his text.
        Text text = repository.findOne(id);
        if (!text.getAuthor().equals(userService.getCurrentUser())) {
            LOG.info("Deleting text not allowed. user={}, id={}", userService.getCurrentUser().getEmail(), id);
            throw new IllegalStateException("User not allowed to delete text");
        }
        LOG.info("Deleting text. user={}, id={}", userService.getCurrentUser().getEmail(), id);

        repository.delete(id);
    }
}
