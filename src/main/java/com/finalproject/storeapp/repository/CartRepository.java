package com.finalproject.storeapp.repository;

import com.finalproject.storeapp.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
