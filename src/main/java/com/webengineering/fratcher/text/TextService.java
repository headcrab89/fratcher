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
        User currentUser = userService.getCurrentUser();
        LOG.info("Current user {}", currentUser);

        return repository.findAll();
    }

    public long addOrReplaceText(Text newText) {
        // If the current user has no text he can create one, otherwise the text will be replaced
        Text text = repository.findByUser(userService.getCurrentUser());

        if (text == null) {
            newText.setAuthor(userService.getCurrentUser());
            repository.save(newText);

            return newText.getId();
        } else {
            text.setUserText(newText.getUserText());
            text.setCreatedAt(new Date());

            repository.save(text);

            return text.getId();
        }
    }

    public Text getText(Long id) {
        return repository.findOne(id);
    }

    public void deleteText(Long id) {
        // Validate that user is allowed to delete his text.
        Text text = repository.findOne(id);
        if (!text.getAuthor().equals(userService.getCurrentUser())) {
            throw new IllegalStateException("User not allowed to delete text");
        }

        repository.delete(id);
    }
}
