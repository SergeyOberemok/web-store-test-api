package com.finalproject.storeapp.repository;

import com.finalproject.storeapp.model.User;

public interface UserRepository {
    User fetch(User user);
    User store(User user);
}
