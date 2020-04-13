package com.finalproject.storeapp.controller.API;

import com.finalproject.storeapp.model.Product;
import com.finalproject.storeapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    private CartService cartService;

    @GetMapping("/api/cart")
    public List<Product> index() {
        return cartService.findAll();
    }

    @PostMapping(path = "/api/cart", consumes = "application/json", produces = "application/json")
    public Product store(@RequestBody Product product) {
        return product;
    }

    @DeleteMapping(path = "/api/cart/{id}", produces = "application/json")
    public String destroy(@PathVariable("id") String id) {
        return id;
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }
}
