package com.finalproject.storeapp.service;

import com.finalproject.storeapp.model.CartProduct;
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
    public List<CartProduct> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public CartProduct show(int cartProductId) {
        return cartRepository.show(cartProductId);
    }

    @Override
    public CartProduct store(CartProduct cartProduct) {
        return cartRepository.store(cartProduct);
    }

    @Override
    public CartProduct update(CartProduct cartProduct) {
        return cartRepository.update(cartProduct);
    }

    @Override
    public int destroy(int cartProductId) {
        return cartRepository.destroy(cartProductId);
    }

    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
}
