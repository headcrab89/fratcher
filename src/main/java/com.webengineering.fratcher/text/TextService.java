package com.webengineering.fratcher.text;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TextService {
    private List<Text> texts = new LinkedList<>();

    public List<Text> getTexts() {
        return texts;
    }

    public void addText( String userText) {
        Text text = new Text(userText);
        texts.add(text);
    }
}
