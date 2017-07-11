package com.webengineering.fratcher.text;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TextController {
    @Autowired
    private TextService textService;

    @RequestMapping("text")
    public List<Text> getPostList() {
        return textService.getTexts();
    }

    @RequestMapping(value = "text/add")
    public void addText(@RequestParam("text") String userText) {
        textService.addText(userText);
    }
}
