package com.finalproject.storeapp.controller.API;

import com.finalproject.storeapp.core.exceptions.NotFoundException;
import com.finalproject.storeapp.core.exceptions.OutOfRangeException;
import com.finalproject.storeapp.model.Cart;
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
        return saveCart(cart);
    }

    @PatchMapping(path = "/api/cart/{id}", consumes = "application/json", produces = "application/json")
    public Cart update(@RequestBody Cart cart, @PathVariable("id") String id) {
        Cart cartDb = cartService.show(Integer.parseInt(id));

        cartDb.setQuantity(cart.getQuantity());

        return saveCart(cartDb);
    }

    @DeleteMapping(path = "/api/cart/{id}", produces = "application/json")
    public int destroy(@PathVariable("id") String id) {
        cartService.delete(Long.parseLong(id));

        return 1;
    }

    private Cart saveCart(Cart cart) {
        try {
            return cartService.save(cart);
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        } catch (OutOfRangeException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity doesn't match criteria");
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }
}
