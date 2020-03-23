package com.finalproject.storeapp.controller.API;

import com.finalproject.storeapp.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/api/products")
    public List<Product> index() {
        List<Product> productList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            productList.add(new Product(i + 1, "Product" + (i + 1), i, i * (i + 1)));
        }

        return productList;
    }
}
