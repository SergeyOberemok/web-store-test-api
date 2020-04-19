package com.finalproject.storeapp.repository;

import com.finalproject.storeapp.model.Product;

import java.util.List;

public interface ProductsRepository {

    List<Product> findAll();

    Product show(int productId);
}
