package com.finalproject.storeapp.controller.API;

import com.finalproject.storeapp.core.exceptions.NotFoundException;
import com.finalproject.storeapp.core.exceptions.PasswordDoesntMatchException;
import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

    private final LoginService loginService;
    private final HttpSession httpSession;

    @PostMapping(path = "/api/login", consumes = "application/json", produces = "application/json")
    public User login(@RequestBody User user) {
        httpSession.setAttribute("authUser", null);

        try {
            User authUser = loginService.authenticate(user);

            httpSession.setAttribute("authUser", authUser);

            return authUser;
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } catch (PasswordDoesntMatchException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad credentials");
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }
}
