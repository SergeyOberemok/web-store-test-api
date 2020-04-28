package com.finalproject.storeapp.repository;

import com.finalproject.storeapp.model.User;

public interface UserRepository {
    User fetch(String email);
    User store(User user);
}
