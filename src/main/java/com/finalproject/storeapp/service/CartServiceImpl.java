package com.finalproject.storeapp.service;

import com.finalproject.storeapp.core.exceptions.NotFoundException;
import com.finalproject.storeapp.core.exceptions.OutOfRangeException;
import com.finalproject.storeapp.model.Cart;
import com.finalproject.storeapp.model.Product;
import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.repository.CartRepository;
import com.finalproject.storeapp.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("cartService")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductsRepository productsRepository;

    @Override
    public List<Cart> findAll(User user) {
        return cartRepository.findAllByUserId(user.getId());
    }

    @Override
    public Cart show(long cartId) throws Exception {
        return cartRepository.findById(cartId).orElseThrow(NotFoundException::new);
    }

    @Transactional
    @Override
    public Cart save(Cart cart) throws Exception {
        Product product = cart.getProduct();

        if (product == null) {
            throw new NotFoundException();
        }

        if (product.getAvailable() < cart.getQuantity() || cart.getQuantity() == 0) {
            throw new OutOfRangeException();
        }

        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void delete(long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Transactional(rollbackFor = {RuntimeException.class, Error.class, OutOfRangeException.class})
    @Override
    public void checkout(User user) throws Exception {
        List<Cart> cartList = findAll(user);

        if (cartList.size() == 0) {
            return;
        }

        List<Product> productList = new ArrayList<>();

        for (Cart cart : cartList) {
            Product product = cart.getProduct();

            if (product.getAvailable() >= cart.getQuantity()) {
                product.setAvailable(product.getAvailable() - cart.getQuantity());
                productList.add(product);
            } else {
                throw new OutOfRangeException();
            }
        }

        productList.forEach((productsRepository::save));
        cartRepository.deleteAllByUserId(user.getId());
    }
}
