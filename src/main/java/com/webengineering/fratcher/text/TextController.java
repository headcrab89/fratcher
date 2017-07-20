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

    @RequestMapping(value = "api/text", method = RequestMethod.GET)
    public Iterable<Text> getPostList() {
        return textService.getTexts();
    }

    @RequestMapping(value = "api/text", method = RequestMethod.POST)
    public TextCreated addOrReplaceText(@RequestBody Text text) {
        long textId = textService.addOrReplaceText(text);
        TextCreated textCreated =  new TextCreated();
        textCreated.url = addressService.getServerURL() +"api/text/" + textId;
        return textCreated;
    }

    @RequestMapping(value = "api/text/{id}", method = RequestMethod.GET)
    public Text getText(@PathVariable Long id) {
        return textService.getText(id);
    }

    @RequestMapping(value = "api/text/{id}", method = RequestMethod.DELETE)
    public void deleteText(@PathVariable Long id) {
        textService.deleteText(id);
    }
}
