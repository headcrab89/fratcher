package com.webengineering.fratcher.text;

import com.webengineering.fratcher.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TextService {
    private static final Logger LOG = LoggerFactory.getLogger(TextService.class);

    @Autowired
    private TextRepository repository;

    @Autowired
    private UserService userService;

    public Iterable<Text> getTexts() {
        LOG.info("Returning texts. user={}", userService.getCurrentUser().getEmail());

        return repository.findAll();
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
