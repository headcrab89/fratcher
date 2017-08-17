package com.webengineering.fratcher.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AuthenticationController {
    public static class UserLogin {
        public String email;
        public String password;
    }

    @Autowired
    private AuthenticationService service;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationService.UserToken> login(@RequestBody UserLogin userLogin) {
        AuthenticationService.UserToken token = service.login(userLogin.email, userLogin.password);

        if (token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationService.UserToken> register(@RequestBody UserLogin userLogin) {
        Boolean usernameIsNew = service.register(userLogin.email, userLogin.password);

        if (usernameIsNew == false || userLogin.password.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AuthenticationService.UserToken token = service.login(userLogin.email, userLogin.password);

        if (token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
