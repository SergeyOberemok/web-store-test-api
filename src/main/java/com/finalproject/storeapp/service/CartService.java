package com.finalproject.storeapp.service;

import com.finalproject.storeapp.model.CartProduct;

import java.util.List;

public interface CartService {
    List<CartProduct> findAll();

    CartProduct show(int cartProductId);

    CartProduct showByProductId(int productId);

    CartProduct store(CartProduct cartProduct);

    CartProduct update(CartProduct cartProduct);

    int destroy(int cartProductId);
}
