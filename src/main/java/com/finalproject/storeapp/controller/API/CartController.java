package com.finalproject.storeapp.controller.API;

import com.finalproject.storeapp.model.CartProduct;
import com.finalproject.storeapp.model.Product;
import com.finalproject.storeapp.service.CartService;
import com.finalproject.storeapp.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CartController {

    private CartService cartService;
    private ProductsService productsService;

    @GetMapping("/api/cart")
    public List<CartProduct> index() {
        return cartService.findAll();
    }

    @PostMapping(path = "/api/cart", consumes = "application/json", produces = "application/json")
    public CartProduct store(@RequestBody CartProduct cartProduct) {
        Product product = productsService.show(cartProduct.getProductId());

        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        CartProduct cartProductFromDb = cartService.showByProductId(cartProduct.getProductId());

        if (product.getAvailable() < cartProduct.getQuantity() || cartProduct.getQuantity() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity doesn't match criteria");
        }

        if (cartProductFromDb != null) {
            cartProductFromDb.setQuantity(cartProduct.getQuantity());
            return cartService.update(cartProductFromDb);
        }

        return cartService.store(cartProduct);
    }

    @PatchMapping(path = "/api/cart/{id}", consumes = "application/json", produces = "application/json")
    public CartProduct update(@RequestBody CartProduct cartProduct, @PathVariable("id") String id) {
        CartProduct cartProductUpdated = cartService.show(Integer.parseInt(id));
        Product product = productsService.show(cartProduct.getProductId());

        if (cartProductUpdated == null || product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        if (product.getAvailable() < cartProduct.getQuantity() || cartProduct.getQuantity() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity doesn't match criteria");
        }

        cartProductUpdated.setQuantity(cartProduct.getQuantity());

        return cartService.update(cartProductUpdated);
    }

    @DeleteMapping(path = "/api/cart/{id}", produces = "application/json")
    public int destroy(@PathVariable("id") String id) {
        int deletedRows = cartService.destroy(Integer.parseInt(id));

        if (deletedRows == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        return deletedRows;
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }
}
