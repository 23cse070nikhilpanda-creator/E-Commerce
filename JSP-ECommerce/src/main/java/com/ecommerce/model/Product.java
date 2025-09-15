// Package declaration
package com.ecommerce.model;

import java.math.BigDecimal; // Using BigDecimal for precise currency values

// A Java Bean representing a product
public class Product {

    // Private fields for product data
    private int id;
    private String name;
    private String description;
    private BigDecimal price; // Best practice for monetary values
    private String imageUrl;
    private int categoryId;

    // Getter for id
    public int getId() {
        return id;
    }

    // Setter for id
    public void setId(int id) {
        this.id = id;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Setter for description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter for price
    public BigDecimal getPrice() {
        return price;
    }

    // Setter for price
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    // Getter for imageUrl
    public String getImageUrl() {
        return imageUrl;
    }

    // Setter for imageUrl
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}