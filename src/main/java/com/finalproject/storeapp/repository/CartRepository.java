package com.finalproject.storeapp.repository;

import com.finalproject.storeapp.model.Product;

import java.util.List;

public interface CartRepository {

    List<Product> findAll();
}
