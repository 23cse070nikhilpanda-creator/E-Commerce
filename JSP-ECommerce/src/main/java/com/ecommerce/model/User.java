// Package declaration
package com.ecommerce.model;

// This is a simple Java Bean (or POJO - Plain Old Java Object) representing a user
public class User {

    // Private member variables (fields) to store user data
    private int id;
    private String name;
    private String email;
    private String password; // In a real app, this should be a password hash
    private String phone;

    // Public getter method for the 'id' field
    public int getId() {
        return id;
    }

    // Public setter method for the 'id' field
    public void setId(int id) {
        this.id = id;
    }

    // Public getter method for the 'name' field
    public String getName() {
        return name;
    }

    // Public setter method for the 'name' field
    public void setName(String name) {
        this.name = name;
    }

    // Public getter method for the 'email' field
    public String getEmail() {
        return email;
    }

    // Public setter method for the 'email' field
    public void setEmail(String email) {
        this.email = email;
    }

    // Public getter method for the 'password' field
    public String getPassword() {
        return password;
    }

    // Public setter method for the 'password' field
    public void setPassword(String password) {
        this.password = password;
    }
    
    // Public getter method for the 'phone' field
    public String getPhone() {
        return phone;
    }

    // Public setter method for the 'phone' field
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    private String role;

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}