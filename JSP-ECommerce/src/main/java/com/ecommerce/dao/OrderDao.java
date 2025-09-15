// Package declaration
package com.ecommerce.dao;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList; // Make sure to import ArrayList and List
import java.util.List;

import com.ecommerce.model.ReportItem;
import com.ecommerce.model.Product;

// DAO for handling all order-related database operations
public class OrderDao {
    private Connection connection;

    public OrderDao() {
        connection = DBConnection.getConnection();
    }

    // This method saves an order to the database and uses a transaction
    public boolean placeOrder(Order order) {
        // SQL queries
        String insertOrderSQL = "INSERT INTO orders (user_id, total_amount, status) VALUES (?, ?, ?)";
        String insertOrderItemSQL = "INSERT INTO order_items (order_id, product_id, quantity, price_per_unit) VALUES (?, ?, ?, ?)";
        
        try {
            // STEP 1: Turn off auto-commit to start the transaction
            connection.setAutoCommit(false);

            // STEP 2: Insert into the 'orders' table
            PreparedStatement orderPstmt = connection.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS);
            orderPstmt.setInt(1, order.getUserId());
            orderPstmt.setBigDecimal(2, order.getTotalAmount());
            orderPstmt.setString(3, order.getStatus());
            int affectedRows = orderPstmt.executeUpdate();

            // Check if the order insert was successful
            if (affectedRows == 0) {
                connection.rollback(); // Rollback transaction if no rows were inserted
                return false;
            }

            // Get the generated order ID
            ResultSet generatedKeys = orderPstmt.getGeneratedKeys();
            int orderId;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            } else {
                connection.rollback(); // Rollback if we can't get the order ID
                return false;
            }

            // STEP 3: Insert each item into the 'order_items' table
            PreparedStatement itemPstmt = connection.prepareStatement(insertOrderItemSQL);
            for (OrderItem item : order.getItems()) {
                itemPstmt.setInt(1, orderId);
                itemPstmt.setInt(2, item.getProduct().getId());
                itemPstmt.setInt(3, item.getQuantity());
                itemPstmt.setBigDecimal(4, item.getPricePerUnit());
                itemPstmt.addBatch(); // Add the insert statement to a batch for efficiency
            }
            itemPstmt.executeBatch(); // Execute all batched inserts

            // STEP 4: If everything was successful, commit the transaction
            connection.commit();
            return true;

        } catch (SQLException e) {
            // STEP 5: If any exception occurs, roll back the transaction
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            // STEP 6: Always turn auto-commit back on in a finally block
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
 // GET a list of all orders
    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        // Join with users table to get the user's email
        String sql = "SELECT o.*, u.email FROM orders o JOIN users u ON o.user_id = u.id ORDER BY o.order_date DESC";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setUserId(rs.getInt("user_id"));
            order.setOrderDate(rs.getTimestamp("order_date"));
            order.setTotalAmount(rs.getBigDecimal("total_amount"));
            order.setStatus(rs.getString("status"));
            // We can set user email in a new field in Order model if needed, or handle it in the JSP
            orders.add(order);
        }
        return orders;
    }

    // UPDATE an order's status
    public void updateOrderStatus(int orderId, String status) throws SQLException {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, status);
        pstmt.setInt(2, orderId);
        pstmt.executeUpdate();
    }
    
    public List<ReportItem> getTopSellingProducts() throws SQLException {
        List<ReportItem> topProducts = new ArrayList<>();
        // This SQL query joins order_items and products, groups by product,
        // sums the quantity for each product, and orders them by the most sold.
        String sql = "SELECT p.id, p.name, p.price, SUM(oi.quantity) AS total_quantity_sold " +
                     "FROM order_items oi " +
                     "JOIN products p ON oi.product_id = p.id " +
                     "GROUP BY p.id, p.name, p.price " +
                     "ORDER BY total_quantity_sold DESC " +
                     "LIMIT 10"; // Get top 10 products

        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getBigDecimal("price"));
            
            int totalQuantity = rs.getInt("total_quantity_sold");

            topProducts.add(new ReportItem(product, totalQuantity));
        }
        return topProducts;
    }
}