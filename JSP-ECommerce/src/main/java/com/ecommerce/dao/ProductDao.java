// Package declaration
package com.ecommerce.dao;

// Import necessary classes
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Data Access Object (DAO) for the Product model
public class ProductDao 
{
    private Connection connection;

    // Constructor gets the database connection
    public ProductDao() {
        connection = DBConnection.getConnection();
    }

    // Method to retrieve all products from the database
    public List<Product> getAllProducts() {
        // Create a list to hold the Product objects
        List<Product> products = new ArrayList<>();
        try {
            // SQL query to select all records from the 'products' table
            String query = "SELECT * FROM products";
            
            // Create a PreparedStatement to execute the query
            PreparedStatement pstmt = connection.prepareStatement(query);
            
            // Execute the query and get the results in a ResultSet
            ResultSet rs = pstmt.executeQuery();

            // Loop through each row in the ResultSet
            while (rs.next()) {
                // For each row, create a new Product object
                Product product = new Product();
                
                // Set the product's properties from the current row's data
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setImageUrl(rs.getString("image_url"));
                product.setCategoryId(rs.getInt("category_id"));
                
                // Add the populated Product object to the list
                products.add(product);
            }
        } catch (SQLException e) {
            // Print any SQL errors to the console
            e.printStackTrace();
        }
        // Return the list of products
        return products;
    }
    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories ORDER BY name";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            categories.add(category);
        }
        return categories;
    }

    public List<Product> getFilteredProducts(String searchQuery, Integer categoryId) throws SQLException {
        List<Product> products = new ArrayList<>();
        // Base SQL query with a 'WHERE 1=1' trick to easily append AND clauses
        StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE 1=1");

        // Dynamically append conditions based on input parameters
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            sql.append(" AND name LIKE ?");
        }
        if (categoryId != null && categoryId > 0) {
            sql.append(" AND category_id = ?");
        }

        PreparedStatement pstmt = connection.prepareStatement(sql.toString());

        // Set parameters for the PreparedStatement in the correct order
        int paramIndex = 1;
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            pstmt.setString(paramIndex++, "%" + searchQuery.trim() + "%");
        }
        if (categoryId != null && categoryId > 0) {
            pstmt.setInt(paramIndex++, categoryId);
        }

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getBigDecimal("price"));
            product.setImageUrl(rs.getString("image_url"));
            products.add(product);
        }
        return products;
    }
 // Add this method inside your ProductDao.java class

    public Product getProductById(int id) 
    {
        Product product = null;
        try {
            // SQL query to get a product by its ID
            String query = "SELECT * FROM products WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id); // Set the ID for the placeholder

            ResultSet rs = pstmt.executeQuery();

            // If a product is found, create a Product object
            if (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setImageUrl(rs.getString("image_url"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product; // Return the product (or null if not found)
    }
    
 // CREATE a new product
    public void addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (name, description, price, image_url, category_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, product.getName());
        pstmt.setString(2, product.getDescription());
        pstmt.setBigDecimal(3, product.getPrice());
        pstmt.setString(4, product.getImageUrl());
        pstmt.setInt(5, product.getCategoryId());
        pstmt.executeUpdate();
    }

    // UPDATE an existing product
    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, image_url = ? WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, product.getName());
        pstmt.setString(2, product.getDescription());
        pstmt.setBigDecimal(3, product.getPrice());
        pstmt.setString(4, product.getImageUrl());
        pstmt.setInt(5, product.getCategoryId());
        pstmt.setInt(5, product.getId());
        pstmt.executeUpdate();
    }

    // DELETE a product
    public void deleteProduct(int productId) throws SQLException {
        // Be careful with deletes! In a real app, you might have to handle related order items.
        String sql = "DELETE FROM products WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, productId);
        pstmt.executeUpdate();
    }
}