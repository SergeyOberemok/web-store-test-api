package com.finalproject.storeapp.service;

import com.finalproject.storeapp.model.Cart;
import com.finalproject.storeapp.model.User;

import java.util.List;

public interface CartService {
    List<Cart> findAll(User user);

    Cart show(long cartId) throws Exception;

    Cart save(Cart cart) throws Exception;

    void delete(long cartId);

    void checkout(User user) throws Exception;
}
