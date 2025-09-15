// Package declaration for organizing our Java classes
package com.ecommerce.util;

// Import necessary classes from the java.sql package for database connectivity
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// This class provides a static method to get a database connection
public class DBConnection {

    // Database connection details are stored as private static final strings
    // This makes them easy to change in one place
    private static final String URL = "jdbc:mysql://localhost:3306/ecommerce_db"; // The URL to your database
    private static final String USER = "root"; // Your MySQL username (default is often 'root')
    private static final String PASSWORD = "Nikhil@6886"; // Your MySQL password

    // A static Connection object to be shared
    private static Connection connection = null;

    // A static method to get the database connection
    public static Connection getConnection() {
        // Check if a connection already exists
        if (connection == null) {
            try {
                // Step 1: Load the MySQL JDBC driver class
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Step 2: Establish the connection to the database
                connection = DriverManager.getConnection(URL, USER, PASSWORD);

            } catch (ClassNotFoundException | SQLException e) {
                // If the driver class is not found or if there's an SQL error, print the stack trace
                // In a real application, you would use a logger here
                e.printStackTrace();
            }
        }
        // Return the existing or newly created connection
        return connection;
    }
}