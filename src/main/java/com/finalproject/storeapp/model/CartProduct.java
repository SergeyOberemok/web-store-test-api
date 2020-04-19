package com.finalproject.storeapp.model;

public class CartProduct extends Product {
    private int quantity;
    private double subtotalPrice;
    private int productId;

    public CartProduct(int id, String title, int available, double price, int quantity) {
        super(id, title, available, price);

        this.setQuantity(quantity);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;

        this.setSubtotalPrice(this.getPrice() * this.quantity);
    }

    public double getSubtotalPrice() {
        return subtotalPrice;
    }

    public void setSubtotalPrice(double subtotalPrice) {
        this.subtotalPrice = subtotalPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
