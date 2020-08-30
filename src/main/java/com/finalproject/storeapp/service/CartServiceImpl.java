package com.finalproject.storeapp.service;

import com.finalproject.storeapp.model.Cart;
import com.finalproject.storeapp.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("cartService")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public Cart show(long cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }

    @Transactional
    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void delete(long cartId) {
        cartRepository.deleteById(cartId);
    }
}
