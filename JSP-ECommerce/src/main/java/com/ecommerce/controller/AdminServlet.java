package com.ecommerce.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecommerce.dao.OrderDao;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.dao.UserDao;
import com.ecommerce.model.Product;

import com.ecommerce.model.User;

@WebServlet("/admin/dashboard")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao;
    private ProductDao productDao;
    private OrderDao orderDao;

    public void init() {
        userDao = new UserDao();
        productDao = new ProductDao();
        orderDao = new OrderDao();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "dashboard"; // Default action
        }

        try {
            switch (action) {
                case "listUsers":
                    request.setAttribute("userList", userDao.getAllUsers());
                    request.getRequestDispatcher("manage-users.jsp").forward(request, response);
                    break;
                case "listProducts":
                    request.setAttribute("productList", productDao.getAllProducts());
                    request.getRequestDispatcher("manage-products.jsp").forward(request, response);
                    break;
                case "listOrders":
                    request.setAttribute("orderList", orderDao.getAllOrders());
                    request.getRequestDispatcher("manage-orders.jsp").forward(request, response);
                    break;
                case "showAddProductForm":
                	request.setAttribute("categoryList", productDao.getAllCategories());
                    request.getRequestDispatcher("product-form.jsp").forward(request, response);
                    break;
                case "showEditProductForm":
                    int id = Integer.parseInt(request.getParameter("id"));
                    Product existingProduct = productDao.getProductById(id);
                    request.setAttribute("product", existingProduct);
                    request.setAttribute("categoryList", productDao.getAllCategories());
                    request.getRequestDispatcher("product-form.jsp").forward(request, response);
                    break;
                case "showAddUserForm":
                    request.getRequestDispatcher("user-form.jsp").forward(request, response);
                    break;
                case "salesReport":
                    request.setAttribute("orderList", orderDao.getAllOrders());
                    request.getRequestDispatcher("sales-report.jsp").forward(request, response);
                    break;
                case "topProductsReport":
                    request.setAttribute("topProductsList", orderDao.getTopSellingProducts());
                    request.getRequestDispatcher("top-products-report.jsp").forward(request, response);
                    break;
                default: // dashboard
                    request.getRequestDispatcher("dashboard.jsp").forward(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "addProduct":
                    addProduct(request, response);
                    break;
                case "updateProduct":
                    updateProduct(request, response);
                    break;
                case "deleteProduct":
                    deleteProduct(request, response);
                    break;
                case "addUser":
                    addUser(request, response);
                    break;
                case "deleteUser":
                    deleteUser(request, response);
                    break;
                case "updateOrderStatus":
                    updateOrderStatus(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Product newProduct = new Product();
        newProduct.setName(request.getParameter("name"));
        newProduct.setDescription(request.getParameter("description"));
        newProduct.setPrice(new BigDecimal(request.getParameter("price")));
        newProduct.setImageUrl(request.getParameter("imageUrl"));
        newProduct.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
        productDao.addProduct(newProduct);
        response.sendRedirect("dashboard?action=listProducts");
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Product product = new Product();
        product.setId(Integer.parseInt(request.getParameter("id")));
        product.setName(request.getParameter("name"));
        product.setDescription(request.getParameter("description"));
        product.setPrice(new BigDecimal(request.getParameter("price")));
        product.setImageUrl(request.getParameter("imageUrl"));
        product.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
        productDao.updateProduct(product);
        response.sendRedirect("dashboard?action=listProducts");
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productDao.deleteProduct(id);
        response.sendRedirect("dashboard?action=listProducts");
    }

    private void updateOrderStatus(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String status = request.getParameter("status");
        orderDao.updateOrderStatus(orderId, status);
        response.sendRedirect("dashboard?action=listOrders");
    }
    
    private void addUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        User newUser = new User();
        newUser.setName(request.getParameter("name"));
        newUser.setEmail(request.getParameter("email"));
        newUser.setPassword(request.getParameter("password")); // Remember to hash in a real app!
        newUser.setPhone(request.getParameter("phone"));
        newUser.setRole(request.getParameter("role"));
        userDao.addUser(newUser);
        response.sendRedirect("dashboard?action=listUsers");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        userDao.deleteUser(userId);
        response.sendRedirect("dashboard?action=listUsers");
    }
}