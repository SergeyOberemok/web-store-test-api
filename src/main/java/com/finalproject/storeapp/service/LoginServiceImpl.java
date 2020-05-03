package com.finalproject.storeapp.service;

import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
    private UserRepository userRepository;

    @Override
    public User authenticate(User user) {
        User userFromDb = userRepository.fetch(user.getEmail());

        if (userFromDb == null) {
            throw new Error("NOT_FOUND");
        }

        if (userFromDb.getPassword().equals(user.getPassword())) {
            user.setId(userFromDb.getId());

            return user;
        }

        throw new Error("PASSWORD_ISNT_EQUAL");
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
