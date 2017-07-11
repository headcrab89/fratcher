package com.webengineering.fratcher.text;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextService {
    @Autowired
    private TextRepository repository;

    public Iterable<Text> getTexts() {
        return repository.findAll();
    }

    public void addText( Text text) {
        repository.save(text);
    }

    public Text getText(Long id) {
        return repository.findOne(id);
    }

    public void deleteText(Long id) {
        repository.delete(id);
    }
}
