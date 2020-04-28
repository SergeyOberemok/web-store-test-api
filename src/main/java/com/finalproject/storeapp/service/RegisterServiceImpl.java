package com.finalproject.storeapp.service;

import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("registerService")
public class RegisterServiceImpl implements RegisterService {

    private UserRepository userRepository;

    @Override
    public User register(User user) {
        if (userRepository.fetch(user.getEmail()) != null) {
            return null;
        }

        return userRepository.store(user);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
