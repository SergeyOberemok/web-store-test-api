package com.finalproject.storeapp.service;

import com.finalproject.storeapp.core.exceptions.NotFoundException;
import com.finalproject.storeapp.core.exceptions.PasswordDoesntMatchException;
import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("loginService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    @Override
    public User authenticate(User user) throws Exception {
        User userFromDb = userRepository.findOneByEmail(user.getEmail()).orElseThrow(NotFoundException::new);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userFromDb.getEmail(), user.getPassword()
        );

        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            return (User) authentication.getPrincipal();
        } catch (BadCredentialsException exception) {
            throw new PasswordDoesntMatchException();
        }
    }
}
