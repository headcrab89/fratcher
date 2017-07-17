package com.webengineering.fratcher.text;

import com.webengineering.fratcher.util.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TextController {
    private static class TextCreated {
        public String url;
    }

    @Autowired
    private AddressService addressService;

    @Autowired
    private TextService textService;

    @RequestMapping(value = "/text", method = RequestMethod.GET)
    public Iterable<Text> getPostList() {
        return textService.getTexts();
    }

    @RequestMapping(value = "/text", method = RequestMethod.POST)
    public TextCreated addText(@RequestBody Text text) {
        textService.addText(text);
        TextCreated textCreated =  new TextCreated();
        textCreated.url = addressService.getServerURL() +"/text/" + text.getId();
        return textCreated;
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
