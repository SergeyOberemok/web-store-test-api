package com.finalproject.storeapp.controller.API;

import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LoginController {
    private LoginService loginService;

    @PostMapping(path = "/api/login", consumes = "application/json", produces = "application/json")
    public User login(@RequestBody User user) {
        try {
            return loginService.authenticate(user);
        } catch (Error error) {
            switch (error.getMessage()) {
                case "NOT_FOUND":
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                case "PASSWORD_ISNT_EQUAL":
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad credentials");
                default:
                    return null;
            }
        }
    }

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
}
