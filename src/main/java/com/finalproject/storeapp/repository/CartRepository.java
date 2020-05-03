package com.finalproject.storeapp.repository;

import com.finalproject.storeapp.model.CartProduct;

import java.util.List;

public interface CartRepository {
    List<CartProduct> findAll();

    CartProduct show(int cartProductId);

    CartProduct showByProductId(int productId);

    CartProduct store(CartProduct cartProduct);

    CartProduct update(CartProduct cartProduct);

    int destroy(int cartProductId);
}
