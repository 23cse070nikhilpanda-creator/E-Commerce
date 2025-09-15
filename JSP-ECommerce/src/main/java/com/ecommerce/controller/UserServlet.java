// Package declaration
package com.ecommerce.controller;

// Import necessary classes for servlets, I/O, and our project's model/DAO
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecommerce.dao.UserDao;
import com.ecommerce.model.User;

// WebServlet annotation maps this servlet to the URL /user
@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Recommended for servlets
    private UserDao userDao; // Data Access Object for user-related database operations

    // The init method is called by the servlet container once when the servlet is first loaded
    public void init() {
        userDao = new UserDao(); // Initialize the UserDao
    }

    // The doPost method handles HTTP POST requests sent to the /user URL
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get the 'action' parameter from the request to determine if it's a login or register action
        String action = request.getParameter("action");

        // Use a switch statement to handle different actions
        switch (action) {
            case "register":
                // If action is 'register', call the registerUser method
                registerUser(request, response);
                break;
            case "login":
                // If action is 'login', call the loginUser method
                loginUser(request, response);
                break;
            default:
                // For any other action, redirect to the login page
                response.sendRedirect("login.jsp");
                break;
        }
    }
    
    // The doGet method handles HTTP GET requests
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the 'action' parameter from the request URL
        String action = request.getParameter("action");
        
        // Check if the action is 'logout'
        if ("logout".equals(action)) {
            // Get the current session
            HttpSession session = request.getSession(false); // false means don't create a new session if one doesn't exist
            if (session != null) {
                // Invalidate the session, removing all attributes (like the logged-in user)
                session.invalidate();
            }
            // Redirect the user to the login page
            response.sendRedirect("login.jsp");
        } else {
            // For any other GET request to /user, just redirect to login
            response.sendRedirect("login.jsp");
        }
    }


    // Private helper method to handle user registration
    private void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // Create a new User object to hold the registration data
        User newUser = new User();
        // Get registration details from the request parameters and set them in the User object
        newUser.setName(request.getParameter("name"));
        newUser.setEmail(request.getParameter("email"));
        newUser.setPassword(request.getParameter("password")); // IMPORTANT: Passwords should be hashed!
        newUser.setPhone(request.getParameter("phone"));

        // Call the DAO method to save the new user to the database
        userDao.registerUser(newUser);

        // Set a success message in the request to be displayed on the login page
        request.setAttribute("message", "Registration successful! Please login.");
        // Forward the request and response to the login.jsp page
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    // Private helper method to handle user login
    private void loginUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // Get the email and password from the login form
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Attempt to log the user in using the DAO
        User user = userDao.loginUser(email, password);

        // Check if the login was successful (i.e., the user object is not null)
        if (user != null) {
            // If successful, get the current session (or create one if it doesn't exist)
            HttpSession session = request.getSession();
            // Store the logged-in user object in the session
            session.setAttribute("user", user);
            // Redirect the user to the homepage (index.jsp)
            response.sendRedirect("index.jsp");
        } else {
            // If login fails, set an error message in the request
            request.setAttribute("error", "Invalid email or password.");
            // Forward the request back to the login.jsp page to display the error
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}