package com.finalproject.storeapp.service;

import com.finalproject.storeapp.model.Cart;

import java.util.List;

public interface CartService {
    List<Cart> findAll();

    Cart show(long cartId);

    Cart save(Cart cart);

    void delete(long cartId);
}
