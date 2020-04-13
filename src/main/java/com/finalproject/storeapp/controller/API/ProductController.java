package com.finalproject.storeapp.controller.API;

import com.finalproject.storeapp.model.Product;
import com.finalproject.storeapp.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private  ProductsService productsService;

    @GetMapping("/api/products")
    public List<Product> index() {
        return productsService.findAll();
    }

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }
}
