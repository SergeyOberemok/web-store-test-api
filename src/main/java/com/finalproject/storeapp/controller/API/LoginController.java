package com.finalproject.storeapp.controller.API;

import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private LoginService loginService;

    @PostMapping(path = "/api/login", consumes = "application/json", produces = "application/json")
    public User login(@RequestBody User user) {
        return user;
    }

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
}
