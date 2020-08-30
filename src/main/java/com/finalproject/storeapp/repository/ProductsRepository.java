package com.finalproject.storeapp.repository;

import com.finalproject.storeapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Product, Long> {
}
