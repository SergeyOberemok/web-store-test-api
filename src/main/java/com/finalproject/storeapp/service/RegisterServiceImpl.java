package com.finalproject.storeapp.service;

import com.finalproject.storeapp.core.ExistingUserException;
import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("registerService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;

    @Override
    public User register(User user) throws Exception {
        if (userRepository.findOneByEmail(user.getEmail()).orElse(null) != null) {
            throw new ExistingUserException();
        }

        return userRepository.save(user);
    }
}
