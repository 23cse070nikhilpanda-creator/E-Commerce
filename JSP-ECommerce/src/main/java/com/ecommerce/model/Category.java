package com.ecommerce.model;

/**
 * Model class for a Category.
 * This is a simple Java Bean (POJO) with fields for category ID and name.
 */
public class Category {

    private int id;
    private String name;

    // Getter for the category ID
    public int getId() {
        return id;
    }

    // Setter for the category ID
    public void setId(int id) {
        this.id = id;
    }

    // Getter for the category name
    public String getName() {
        return name;
    }

    // Setter for the category name
    public void setName(String name) {
        this.name = name;
    }
}