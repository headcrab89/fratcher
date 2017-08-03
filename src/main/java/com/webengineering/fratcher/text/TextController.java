package com.webengineering.fratcher.text;

import com.webengineering.fratcher.user.UserService;
import com.webengineering.fratcher.util.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private UserService userService;

    @RequestMapping(value = "api/text", method = RequestMethod.GET)
    public ResponseEntity<Object> getNewTextList() {
        return ResponseEntity.ok(textService.getNewTexts());
    }

    @RequestMapping(value = "api/text", method = RequestMethod.POST)
    public ResponseEntity<Object> addOrReplaceText(@RequestBody Text text) {

        if (userService.isAnonymous()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        long textId = textService.addOrReplaceText(text);
        TextCreated textCreated =  new TextCreated();
        textCreated.url = addressService.getServerURL() +"api/text/" + textId;
        return ResponseEntity.ok(textCreated);
    }

    @RequestMapping(value = "api/text/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getText(@PathVariable Long id) {
        return ResponseEntity.ok(textService.getText(id));
    }

    @RequestMapping(value = "api/text/{id}", method = RequestMethod.DELETE)
    public void deleteText(@PathVariable Long id) {
        textService.deleteText(id);
    }
}
