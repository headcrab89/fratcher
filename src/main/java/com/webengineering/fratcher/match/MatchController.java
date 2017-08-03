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
        public String url;
    }

    @Autowired
    private AddressService addressService;

    @Autowired
    private MatchService matchService;

    @Autowired
    private UserService userService;

//    @RequestMapping(value = "/api/user/{userId}/match", method = RequestMethod.GET)
//    public ResponseEntity<Object> getMatchFromUser(@PathVariable Long userId) {
//
//        if (userId != userService.getCurrentUser().getId()) {
//           return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//
//        return ResponseEntity.ok(matchService.getMatches(userId));
//    }

    @RequestMapping(value = "/api/match", method = RequestMethod.POST)
    public ResponseEntity<Object> addMatch(@RequestBody Match match) {
        if (userService.isAnonymous()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else if (match.getMatchUser().equals(userService.getCurrentUser())
                || match.getMatchStatus() == null || match.getMatchStatus().equals(MatchStatus.BOTH_LIKE)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        long matchId = matchService.addMatch(match);
        MatchCreated matchCreated = new MatchCreated();
        matchCreated.url = addressService.getServerURL() +"api/match/" + matchId;

        return ResponseEntity.ok(matchCreated);
    }

    @RequestMapping(value = "api/match/{id}", method = RequestMethod.GET)
    public Match getMatch(@PathVariable Long id) {
        return matchService.getMatch(id);
    }


//    @RequestMapping(value = "/api/match/{id}", method = RequestMethod.DELETE)
//    public void deleteMatch(@PathVariable Long id) {
//        // TODO ML
//    }
}
