package com.finalproject.storeapp.service;

import com.finalproject.storeapp.model.Product;

import java.util.List;

public interface ProductsService {

    List<Product> findAll();

    Product show(long productId);

    Product save(Product product) throws Exception;

    void delete(long productId);
}
