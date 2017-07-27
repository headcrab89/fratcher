package com.webengineering.fratcher.match;

import com.webengineering.fratcher.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MatchController {
    @Autowired
    private MatchService matchService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/user/{userId}/match", method = RequestMethod.GET)
    public ResponseEntity<Object> getMatchFromUser(@PathVariable Long userId) {

        if (userId != userService.getCurrentUser().getId()) {
           return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(matchService.getMatches(userId));
    }

    @RequestMapping(value = "/api/match", method = RequestMethod.POST)
    public ResponseEntity<Object> addMatch(@RequestBody Match match) {
        if (userService.isAnonymous()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        matchService.addMatch(match);

        return ResponseEntity.ok(null);
    }


//    @RequestMapping(value = "/api/match/{id}", method = RequestMethod.DELETE)
//    public void deleteMatch(@PathVariable Long id) {
//        // TODO ML
//    }
}
