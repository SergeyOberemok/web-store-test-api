package com.finalproject.storeapp.service;

import com.finalproject.storeapp.model.Product;
import com.finalproject.storeapp.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cartService")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;

    @Override
    public List<Product> findAll() {
        return cartRepository.findAll();
    }

    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
}
