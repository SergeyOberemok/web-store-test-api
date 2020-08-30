package com.finalproject.storeapp.controller.API;

import com.finalproject.storeapp.core.ExistingUserException;
import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping(path = "/api/register", consumes = "application/json", produces = "application/json")
    public User register(@RequestBody User user) {
        try {
            return registerService.register(user);
        } catch (ExistingUserException error) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        } catch (Exception error) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }
}
