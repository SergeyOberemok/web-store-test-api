package com.finalproject.storeapp.service;

import com.finalproject.storeapp.core.NotFoundException;
import com.finalproject.storeapp.core.PasswordIsNotEqualException;
import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loginService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    @Override
    public User authenticate(User user) throws Exception {
        User userFromDb = userRepository.findOneByEmail(user.getEmail()).orElse(null);

        if (userFromDb == null) {
            throw new NotFoundException();
        }

        if (userFromDb.getPassword().equals(user.getPassword())) {
            user.setId(userFromDb.getId());

            return user;
        }

        throw new PasswordIsNotEqualException();
    }
}
