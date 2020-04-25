package com.finalproject.storeapp.service;

import com.finalproject.storeapp.model.User;

public interface LoginService {
    User authenticate(User user);
}
