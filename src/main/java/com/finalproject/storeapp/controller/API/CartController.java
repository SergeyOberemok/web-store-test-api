package com.finalproject.storeapp.controller.API;

import com.finalproject.storeapp.model.Cart;
import com.finalproject.storeapp.model.Product;
import com.finalproject.storeapp.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CartController {

    private final CartService cartService;

    @GetMapping("/api/cart")
    public List<Cart> index() {
        return cartService.findAll();
    }

    @PostMapping(path = "/api/cart", consumes = "application/json", produces = "application/json")
    public Cart store(@RequestBody Cart cart) {
        Product product = cart.getProduct();

        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        if (product.getAvailable() < cart.getQuantity() || cart.getQuantity() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity doesn't match criteria");
        }

        return cartService.save(cart);
    }

    @PatchMapping(path = "/api/cart/{id}", consumes = "application/json", produces = "application/json")
    public Cart update(@RequestBody Cart cart, @PathVariable("id") String id) {
        Cart cartUpdated = cartService.show(Integer.parseInt(id));
        Product product = cart.getProduct();

        if (cartUpdated == null || product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        if (product.getAvailable() < cart.getQuantity() || cart.getQuantity() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity doesn't match criteria");
        }

        cartUpdated.setQuantity(cart.getQuantity());

        return cartService.save(cartUpdated);
    }

    @DeleteMapping(path = "/api/cart/{id}", produces = "application/json")
    public int destroy(@PathVariable("id") String id) {
        cartService.delete(Long.parseLong(id));

        return 1;
    }
}
