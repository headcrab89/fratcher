package com.webengineering.fratcher.text;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TextController {
    @Autowired
    private TextService textService;

    @RequestMapping(value = "/text", method = RequestMethod.GET)
    public Iterable<Text> getPostList() {
        return textService.getTexts();
    }

    @RequestMapping(value = "/text", method = RequestMethod.POST)
    public void addText(@RequestBody Text text) {
        textService.addText(text);
    }

    @RequestMapping(value = "/text/{id}", method = RequestMethod.GET)
    public Text getText(@PathVariable Long id) {
        return textService.getText(id);
    }

    @RequestMapping(value = "/text/{id}", method = RequestMethod.DELETE)
    public void deleteText(@PathVariable Long id) {
        textService.deleteText(id);
    }
}
