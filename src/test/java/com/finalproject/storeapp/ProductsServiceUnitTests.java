package com.finalproject.storeapp;

import com.finalproject.storeapp.model.Product;
import com.finalproject.storeapp.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ProductsServiceUnitTests {

    private final ProductsService productsService;

    @Test
    void findAll() {
        List<Product> productList = this.productsService.findAll();

        assertThat(productList.size()).isEqualTo(5);
        assertThat(productList.size()).isGreaterThan(0);
    }

    @Test
    void show() {
        long id = 1;
        Product product = this.productsService.show(id);

        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo(id);
    }
}
