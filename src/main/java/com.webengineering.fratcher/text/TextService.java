package com.webengineering.fratcher.text;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;

@Service
public class TextService {
    private List<String> texts = new LinkedList<>();

    public List<String> getTexts() {
        return texts;
    }

    public void addText( String title) {
        texts.add(title);
    }
}
