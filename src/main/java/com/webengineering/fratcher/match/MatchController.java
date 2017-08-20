package com.webengineering.fratcher.match;

import com.webengineering.fratcher.user.UserService;
import com.webengineering.fratcher.util.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MatchController {

    private static class MatchCreated {
        public Long id;
        public MatchStatus status;
    }

    @Autowired
    private AddressService addressService;

    @Autowired
    private MatchService matchService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/user/{userId}/match", method = RequestMethod.GET)
    public ResponseEntity<Object> getMatchesFromUser(@PathVariable Long userId) {

        if (userId != userService.getCurrentUser().getId()) {
           return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(matchService.getMatches(userId));
    }

    @RequestMapping(value = "/api/match", method = RequestMethod.POST)
    public ResponseEntity<Object> addMatch(@RequestBody Match match) {
        if (userService.isAnonymous()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else if (match.getMatchUser().equals(userService.getCurrentUser())
                || match.getMatchStatus() == null || match.getMatchStatus().equals(MatchStatus.BOTH_LIKE)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Match newMatch = matchService.addMatch(match);
        MatchCreated matchCreated = new MatchCreated();
        matchCreated.id = newMatch.getId();
        matchCreated.status = newMatch.getMatchStatus();

        return ResponseEntity.ok(matchCreated);
    }

    @RequestMapping(value = "api/match/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getMatch(@PathVariable Long id) {
        Match match = matchService.getMatch(id);

        // An User can only see the match if it is his own match
        if (match.getInitUser().equals(userService.getCurrentUser())
                || (match.getMatchUser().equals(userService.getCurrentUser()) && match.getMatchStatus().equals(MatchStatus.BOTH_LIKE))) {
            return ResponseEntity.ok(match);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


//    @RequestMapping(value = "/api/match/{id}", method = RequestMethod.DELETE)
//    public void deleteMatch(@PathVariable Long id) {
//        // TODO ML
//    }
}
