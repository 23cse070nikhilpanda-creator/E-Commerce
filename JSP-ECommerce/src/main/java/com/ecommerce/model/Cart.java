// Package declaration
package com.ecommerce.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// This class represents the user's shopping cart
public class Cart {
    // A list to hold all the CartItem objects
    private List<CartItem> items;

    // Constructor initializes an empty list of items
    public Cart() {
        this.items = new ArrayList<>();
    }

    // Getter for the list of items
    public List<CartItem> getItems() {
        return items;
    }

    // Method to add an item to the cart
    public void addItem(CartItem item) {
        // Check if the product is already in the cart
        for (CartItem currentItem : items) {
            if (currentItem.getProduct().getId() == item.getProduct().getId()) {
                // If it exists, just increase the quantity
                currentItem.setQuantity(currentItem.getQuantity() + item.getQuantity());
                return; // Exit the method
            }
        }
        // If the product is not in the cart, add the new item to the list
        items.add(item);
    }

    // Method to remove an item from the cart by product ID
    public void removeItem(int productId) {
        // Use the removeIf method with a lambda expression to find and remove the item
        items.removeIf(item -> item.getProduct().getId() == productId);
    }

    // Method to update the quantity of an existing item in the cart
    public void updateItem(int productId, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == productId) {
                // If the new quantity is positive, update it
                if (quantity > 0) {
                    item.setQuantity(quantity);
                } else {
                    // If quantity is zero or less, remove the item
                    removeItem(productId);
                }
                return; // Exit the loop once the item is found and updated
            }
        }
    }
    
    // Method to clear all items from the cart
    public void clear() {
        items.clear();
    }

    // Calculated property to get the total price of all items in the cart
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO; // Initialize total to zero
        // Loop through all items and add their total price to the cart's total
        for (CartItem item : items) {
            total = total.add(item.getTotalPrice());
        }
        return total;
    }
}