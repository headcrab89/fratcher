package com.webengineering.fratcher.match;

import com.webengineering.fratcher.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MatchController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/match", method = RequestMethod.GET)
    public Iterable<Match> getMatchList() {
        // TODO ML
        return null;
    }

    @RequestMapping(value = "/api/match", method = RequestMethod.POST)
    public ResponseEntity<Object> addMatch(@RequestBody Match match) {
        if (userService.isAnonymous()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/api/match/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> editMatch(@PathVariable Long id, @RequestBody Match match) {
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/api/match/{id}", method = RequestMethod.DELETE)
    public void deleteMatch(@PathVariable Long id) {
        // TODO ML
    }
}
