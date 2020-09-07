package com.finalproject.storeapp;

import com.finalproject.storeapp.model.Product;
import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.repository.UserRepository;
import com.finalproject.storeapp.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ProductsServiceUnitTests {

    private final ProductsService productsService;
    private final UserRepository userRepository;

    @Test
    void findAll() {
        List<Product> productList = productsService.findAll();

        assertThat(productList.size()).isEqualTo(5);
        assertThat(productList.size()).isGreaterThan(0);
    }

    @Test
    void show() {
        long id = 1;
        Product product = productsService.show(id);

        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo(id);
    }

    @Test
    void save() {
        int random = ThreadLocalRandom.current().nextInt(0, 1000);
        Product product = new Product();
        product.setTitle("tempProduct" + random);
        product.setAvailable(random);
        product.setPrice(random);

        AtomicReference<Product> storedProduct = new AtomicReference<>();

        assertThatCode(() -> storedProduct.set(productsService.save(product))).doesNotThrowAnyException();

        assertThat(storedProduct.get().getId()).isGreaterThan(0);
        assertThat(storedProduct.get().getAvailable()).isEqualTo(random);
        assertThat(storedProduct.get().getPrice()).isEqualTo(random);
    }

    @Test
    void delete() {
        List<Product> productList = productsService.findAll();

        assertThat(productList.size()).isGreaterThan(0);

        Product product = productList.get(productList.size() - 1);

        productsService.delete(product.getId());

        assertThat(productsService.show(product.getId())).isNull();
    }
}
