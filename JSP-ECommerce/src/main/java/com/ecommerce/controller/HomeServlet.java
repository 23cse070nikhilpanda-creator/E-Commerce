package com.ecommerce.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecommerce.dao.ProductDao;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;

/**
 * Servlet controller for the homepage.
 * Handles product searching and filtering by category.
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDao productDao;

    public void init() {
        productDao = new ProductDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get search and filter parameters from the request URL
            String searchQuery = request.getParameter("search");
            String categoryIdStr = request.getParameter("category");
            Integer categoryId = null;

            // Safely parse the category ID string to an Integer
            if (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) {
                try {
                    categoryId = Integer.parseInt(categoryIdStr);
                } catch (NumberFormatException e) {
                    // Log the error or handle it as needed
                    System.err.println("Invalid category ID format: " + categoryIdStr);
                }
            }

            // Fetch filtered products and all categories from the DAO
            List<Product> productList = productDao.getFilteredProducts(searchQuery, categoryId);
            List<Category> categoryList = productDao.getAllCategories();

            // Set attributes to pass data to the JSP
            request.setAttribute("products", productList);
            request.setAttribute("categories", categoryList);
            request.setAttribute("selectedCategoryId", categoryId); // To highlight the active category
            request.setAttribute("searchQuery", searchQuery); // To keep text in the search box

            // Forward the request to the index.jsp for display
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (SQLException e) {
            // In case of a database error, throw a ServletException
            throw new ServletException("Database error while fetching data for homepage.", e);
        }
    }
}