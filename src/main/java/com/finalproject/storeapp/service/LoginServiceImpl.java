package com.finalproject.storeapp.service;

import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public User authenticate(User user) {
        User userFromDb = userRepository.fetch(user.getEmail());

        if (userFromDb == null) {
            throw new Error("NOT_FOUND");
        }

        if (passwordEncoder.matches(user.getPassword(), userFromDb.getPassword())) {
            user.setId(userFromDb.getId());

            return user;
        }

        throw new Error("PASSWORD_ISNT_EQUAL");
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
