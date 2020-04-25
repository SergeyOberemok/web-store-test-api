package com.finalproject.storeapp.controller.API;

import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private RegisterService registerService;

    @PostMapping(path = "/api/register", consumes = "application/json", produces = "application/json")
    public User register(@RequestBody User user) {
        return registerService.register(user);
    }

    @Autowired
    public void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
    }
}
