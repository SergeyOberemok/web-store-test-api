package com.finalproject.storeapp.controller.API;

import com.finalproject.storeapp.core.exceptions.NotFoundException;
import com.finalproject.storeapp.core.exceptions.OutOfRangeException;
import com.finalproject.storeapp.model.Cart;
import com.finalproject.storeapp.model.Product;
import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.service.CartService;
import com.finalproject.storeapp.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CartController {

    private final CartService cartService;
    private final ProductsService productsService;
    private final HttpSession httpSession;

    @GetMapping("/api/cart")
    public List<Cart> index() {
        User user = (User) httpSession.getAttribute("authUser");

        return cartService.findAll(user);
    }

    @PostMapping(path = "/api/cart", consumes = "application/json", produces = "application/json")
    public Cart store(@RequestBody StoreCartRequestBody body) {
        User user = (User) httpSession.getAttribute("authUser");
        Cart cart = new Cart();

        try {
            Product product = productsService.show(body.productId);

            cart.setQuantity(body.quantity);
            cart.setProduct(product);
            cart.setUser(user);
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }

        return saveCart(cart);
    }

    @PatchMapping(path = "/api/cart/{id}", consumes = "application/json", produces = "application/json")
    public Cart update(@RequestBody UpdateCartRequestBody body, @PathVariable("id") String id) {
        Cart cartDb;

        try {
            cartDb = cartService.show(Integer.parseInt(id));

            cartDb.setQuantity(body.quantity);
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found");
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }

        return saveCart(cartDb);
    }

    @DeleteMapping(path = "/api/cart/{id}", produces = "application/json")
    public void destroy(@PathVariable("id") String id) {
        cartService.delete(Long.parseLong(id));
    }

    @PostMapping(path = "/api/cart/checkout")
    public void checkout() {
        User user = (User) httpSession.getAttribute("authUser");

        try {
            cartService.checkout(user);
        } catch (OutOfRangeException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity doesn't match criteria");
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }

    private Cart saveCart(Cart cart) {
        try {
            return cartService.save(cart);
        } catch (OutOfRangeException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity doesn't match criteria");
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }

    private static class StoreCartRequestBody {
        public int quantity;
        public long productId;
    }

    private static class UpdateCartRequestBody {
        public int quantity;
    }
}
