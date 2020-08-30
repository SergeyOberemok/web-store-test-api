package com.finalproject.storeapp.controller.API;

import com.finalproject.storeapp.model.Product;
import com.finalproject.storeapp.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private final ProductsService productsService;

    @GetMapping("/api/products")
    public List<Product> index() {
        return productsService.findAll();
    }
}
