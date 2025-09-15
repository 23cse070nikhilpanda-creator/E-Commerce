// Package declaration
package com.ecommerce.dao;

// Import necessary classes
import com.ecommerce.model.User;
import com.ecommerce.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList; // Make sure to import ArrayList and List
import java.util.List;

// Data Access Object (DAO) for the User model
public class UserDao {

    // Get the database connection from our utility class
    private Connection connection;

    // Constructor initializes the connection
    public UserDao() {
        connection = DBConnection.getConnection();
    }

    // Method to register a new user in the database
    public void registerUser(User user) {
        try {
            // SQL query to insert a new user into the 'users' table
            // Using '?' as placeholders for security (prevents SQL injection)
            String query = "INSERT INTO users (name, email, password, phone) VALUES (?, ?, ?, ?)";

            // Create a PreparedStatement object to safely execute the query
            PreparedStatement pstmt = connection.prepareStatement(query);

            // Set the values for the placeholders '?'
            pstmt.setString(1, user.getName());    // Set the first '?' to the user's name
            pstmt.setString(2, user.getEmail());   // Set the second '?' to the user's email
            pstmt.setString(3, user.getPassword());// Set the third '?' to the user's password
            pstmt.setString(4, user.getPhone());   // Set the fourth '?' to the user's phone

            // Execute the query to insert the data
            pstmt.executeUpdate();

        } catch (SQLException e) {
            // Print any SQL errors to the console
            e.printStackTrace();
        }
    }

    // Method to check if a user exists with the given email and password
    public User loginUser(String email, String password) {
        // Create a User object to hold the user's data if found
        User user = null;
        try {
            // SQL query to select a user by email and password
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            
            // Create a PreparedStatement
            PreparedStatement pstmt = connection.prepareStatement(query);
            
            // Set the values for the placeholders
            pstmt.setString(1, email);      // Set the first '?' to the provided email
            pstmt.setString(2, password);   // Set the second '?' to the provided password

            // Execute the query and get the result set
            ResultSet rs = pstmt.executeQuery();

            // Check if the result set has any rows
            if (rs.next()) {
                // If a user is found, create a new User object
                user = new User();
                // Populate the User object with data from the database
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                // We don't retrieve the password for security reasons
            }
        } catch (SQLException e) {
            // Print any SQL errors
            e.printStackTrace();
        }
        // Return the User object (will be null if login fails)
        return user;
    }
    
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, email, phone, role FROM users";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setRole(rs.getString("role"));
            users.add(user);
        }
        return users;
    }
 // CREATE a new user (for admin panel)
    public void addUser(User user) throws SQLException {
        // IMPORTANT: In a real-world application, you MUST hash the password before storing it.
        // String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        // For this project's simplicity, we are storing it in plain text.
        String sql = "INSERT INTO users (name, email, password, phone, role) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, user.getName());
        pstmt.setString(2, user.getEmail());
        pstmt.setString(3, user.getPassword()); // In a real app, use hashedPassword
        pstmt.setString(4, user.getPhone());
        pstmt.setString(5, user.getRole());
        pstmt.executeUpdate();
    }

    // DELETE a user
    public void deleteUser(int userId) throws SQLException {
        // WARNING: Deleting a user who has placed orders can cause database integrity issues
        // if you don't have cascading deletes set up. A better approach in a real app
        // is to "deactivate" the user (e.g., set an 'is_active' flag to false).
        // This implementation performs a hard delete for simplicity.
        String sql = "DELETE FROM users WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, userId);
        pstmt.executeUpdate();
    }
}