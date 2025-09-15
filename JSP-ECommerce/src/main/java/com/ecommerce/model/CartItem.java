// Package declaration
package com.ecommerce.model;

import java.math.BigDecimal;

// This class represents a single item within the shopping cart
public class CartItem {

    // The product being stored in this cart item
    private Product product;
    // The quantity of this product
    private int quantity;

    // Constructor to create a new CartItem
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getter for the product
    public Product getProduct() {
        return product;
    }

    // Setter for the product
    public void setProduct(Product product) {
        this.product = product;
    }

    // Getter for the quantity
    public int getQuantity() {
        return quantity;
    }

    // Setter for the quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // A calculated property to get the total price for this item (product price * quantity)
    public BigDecimal getTotalPrice() {
        // Multiply the product's price by the quantity
        return product.getPrice().multiply(new BigDecimal(quantity));
    }
}