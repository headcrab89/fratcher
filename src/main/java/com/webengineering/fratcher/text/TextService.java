package com.webengineering.fratcher.text;

import com.webengineering.fratcher.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void addText( Text text) {
        text.setAuthor(userService.getCurrentUser());
        repository.save(text);
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
