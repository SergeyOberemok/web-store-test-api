package com.finalproject.storeapp.service;

import com.finalproject.storeapp.core.exceptions.ExistingUserException;
import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("registerService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) throws Exception {
        if (userRepository.findOneByEmail(user.getEmail()).orElse(null) != null) {
            throw new ExistingUserException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
}
