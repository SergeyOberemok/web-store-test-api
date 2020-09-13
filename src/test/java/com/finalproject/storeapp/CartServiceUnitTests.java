package com.finalproject.storeapp;

import com.finalproject.storeapp.core.exceptions.NotFoundException;
import com.finalproject.storeapp.model.Cart;
import com.finalproject.storeapp.model.Product;
import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.repository.UserRepository;
import com.finalproject.storeapp.service.CartService;
import com.finalproject.storeapp.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class CartServiceUnitTests {

    private final CartService cartService;
    private final ProductsService productsService;
    private final UserRepository userRepository;

    @Test
    void findAll() {
        List<Cart> cartList = cartService.findAll(getUser());

        assertThat(cartList.size()).isGreaterThan(0);
    }

    @Test
    void show() {
        List<Cart> cartList = cartService.findAll(getUser());

        assertThat(cartList.size()).isGreaterThan(0);

        Cart firstCart = cartList.get(0);
        AtomicReference<Cart> cart = new AtomicReference<>();

        assertThatCode(() -> cart.set(cartService.show(firstCart.getId()))).doesNotThrowAnyException();

        assertThat(cart.get().getId()).isEqualTo(firstCart.getId());
    }

    @Test
    void save() {
        List<Product> productList = productsService.findAll();

        assertThat(productList.size()).isGreaterThan(0);

        Product product = productList.get(0);
        Cart cartToSave = new Cart();
        cartToSave.setQuantity(ThreadLocalRandom.current().nextInt(1, product.getAvailable()));
        cartToSave.setProduct(product);
        cartToSave.setUser(getUser());

        AtomicReference<Cart> cart = new AtomicReference<>();

        assertThatCode(() -> cart.set(cartService.save(cartToSave))).doesNotThrowAnyException();

        assertThat(cart.get().getId()).isGreaterThan(0);
        assertThat(cart.get().getQuantity()).isEqualTo(cartToSave.getQuantity());
        assertThat(cart.get().getProduct().getId()).isEqualTo(product.getId());
    }

    // TODO: add patch test

    @Test
    void delete() {
        List<Cart> cartList = cartService.findAll(getUser());

        assertThat(cartList.size()).isGreaterThan(0);

        Cart cart = cartList.get(0);

        cartService.delete(cart.getId());

        Throwable throwable = catchThrowable(() -> cartService.show(cart.getId()));

        assertThat(throwable).isInstanceOf(NotFoundException.class).hasMessageContaining("NOT_FOUND");
    }

    @Test
    void checkout() {
        User user = getUser();

        assertThatCode(() -> cartService.checkout(user)).doesNotThrowAnyException();

        List<Cart> cartList = cartService.findAll(user);

        assertThat(cartList.size()).isEqualTo(0);

    }


    private User getUser() {
        AtomicReference<User> user = new AtomicReference<>();

        assertThatCode(() -> user.set(
                userRepository.findOneByEmail("asdf@asdf").orElseThrow(NotFoundException::new)
        )).doesNotThrowAnyException();

        return user.get();
    }
}
