package com.finalproject.storeapp.controller.API;

import com.finalproject.storeapp.model.CartProduct;
import com.finalproject.storeapp.model.Product;
import com.finalproject.storeapp.service.CartService;
import com.finalproject.storeapp.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        CartProduct cartProductFromDb = cartService.showByProductId(cartProduct.getProductId());

        if (product.getAvailable() < cartProduct.getQuantity() || cartProduct.getQuantity() == 0) {
            return null;
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

        if (cartProductUpdated == null) {
            return null;
        }

        Product product = productsService.show(cartProduct.getProductId());

        if (product.getAvailable() < cartProduct.getQuantity() || cartProduct.getQuantity() == 0) {
            return null;
        }

        cartProductUpdated.setQuantity(cartProduct.getQuantity());

        return cartService.update(cartProductUpdated);
    }

    @DeleteMapping(path = "/api/cart/{id}", produces = "application/json")
    public int destroy(@PathVariable("id") String id) {
        return cartService.destroy(Integer.parseInt(id));
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
