package com.finalproject.storeapp;

import com.finalproject.storeapp.model.Cart;
import com.finalproject.storeapp.model.Product;
import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.repository.UserRepository;
import com.finalproject.storeapp.service.CartService;
import com.finalproject.storeapp.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class CartServiceUnitTests {

    private final CartService cartService;
    private final ProductsService productsService;
    private final UserRepository userRepository;

    @Test
    void findAll() {
        List<Cart> cartList = this.cartService.findAll();

        assertThat(cartList.size()).isGreaterThan(0);
    }

    @Test
    void show() {
        List<Cart> cartList = this.cartService.findAll();

        assertThat(cartList.size()).isGreaterThan(0);

        Cart firstCart = cartList.get(0);
        Cart cart = this.cartService.show(firstCart.getId());

        assertThat(cart).isNotNull();
        assertThat(cart.getId()).isEqualTo(firstCart.getId());
    }

    @Test
    void save() {
        User user = this.userRepository.findOneByEmail("asdf@asdf").orElse(null);

        assertThat(user).isNotNull();

        List<Product> productList = this.productsService.findAll();

        assertThat(productList.size()).isGreaterThan(0);

        Product product = productList.get(0);
        Cart cartToSave = new Cart();
        cartToSave.setQuantity(2);
        cartToSave.setProduct(product);
        cartToSave.setUser(user);

        Cart cart = this.cartService.save(cartToSave);

        assertThat(cart.getId()).isGreaterThan(0);
        assertThat(cart.getQuantity()).isEqualTo(cartToSave.getQuantity());
        assertThat(cart.getProduct().getId()).isEqualTo(product.getId());
    }

    @Test
    void delete() {
        List<Cart> cartList = this.cartService.findAll();

        assertThat(cartList.size()).isGreaterThan(0);

        Cart cart = cartList.get(0);

        cartService.delete(cart.getId());

        assertThat(this.cartService.show(cart.getId())).isNull();
    }
}
