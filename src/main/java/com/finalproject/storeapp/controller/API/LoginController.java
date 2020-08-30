package com.finalproject.storeapp.controller.API;

import com.finalproject.storeapp.core.NotFoundException;
import com.finalproject.storeapp.core.PasswordIsNotEqualException;
import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

    private final LoginService loginService;

    @PostMapping(path = "/api/login", consumes = "application/json", produces = "application/json")
    public User login(@RequestBody User user) {
        try {
            return loginService.authenticate(user);
        } catch (NotFoundException error) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } catch (PasswordIsNotEqualException error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad credentials");
        } catch (Exception error) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }
}
