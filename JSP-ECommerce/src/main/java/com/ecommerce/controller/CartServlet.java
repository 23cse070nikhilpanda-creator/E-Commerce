// Package declaration
package com.ecommerce.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecommerce.dao.ProductDao;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;

// Map this servlet to the URL /cart
@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handles HTTP GET requests (e.g., when a user clicks a link to view the cart)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to the cart display page
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    // Handles HTTP POST requests (e.g., when a user submits a form to add/update/remove items)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the current session, or create one if it doesn't exist
        HttpSession session = request.getSession();
        
        // Retrieve the cart from the session. If it doesn't exist, create a new one.
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // Get the 'action' parameter from the form to determine what to do
        String action = request.getParameter("action");

        // Use a switch statement to handle different cart actions
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            
            switch (action) {
                case "add":
                    // Get the quantity from the request, default to 1 if not provided
                    int quantity = 1;
                    if (request.getParameter("quantity") != null) {
                       quantity = Integer.parseInt(request.getParameter("quantity"));
                    }
                    // Create a ProductDao to fetch product details from the database
                    ProductDao productDao = new ProductDao();
                    Product product = productDao.getProductById(productId);
                    // Add the item to the cart
                    cart.addItem(new CartItem(product, quantity));
                    break;

                case "update":
                    // Get the new quantity from the request
                    int newQuantity = Integer.parseInt(request.getParameter("quantity"));
                    // Update the item's quantity in the cart
                    cart.updateItem(productId, newQuantity);
                    break;
                    
                case "remove":
                    // Remove the item from the cart
                    cart.removeItem(productId);
                    break;
            }
        } catch (NumberFormatException e) {
            // Handle cases where productId or quantity are not valid numbers
            e.printStackTrace();
        }
        
        // Update the cart in the session
        session.setAttribute("cart", cart);
        // Redirect the user to the cart page to see the changes
        response.sendRedirect("cart.jsp");
    }
}