package com.finalproject.storeapp.repository;

import com.finalproject.storeapp.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}
