// Package declaration
package com.ecommerce.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecommerce.dao.OrderDao;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.User;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderDao orderDao;

    public void init() {
        orderDao = new OrderDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Get the logged-in user and the cart from the session
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");

        // Check if the user is logged in
        if (user == null) {
            response.sendRedirect("login.jsp"); // If not, redirect to login page
            return;
        }

        // Check if the cart is empty
        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("cart.jsp"); // If empty, redirect to cart page
            return;
        }

        // Create the Order object
        Order order = new Order();
        order.setUserId(user.getId());
        order.setOrderDate(new Date());
        order.setTotalAmount(cart.getTotal());
        order.setStatus("Pending");

        // Create the list of OrderItems from the CartItems
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPricePerUnit(cartItem.getProduct().getPrice());
            orderItems.add(orderItem);
        }
        order.setItems(orderItems);

        // Try to place the order by calling the DAO
        boolean isOrderPlaced = orderDao.placeOrder(order);

        if (isOrderPlaced) {
            // If successful, clear the cart and redirect to a confirmation page
            session.removeAttribute("cart");
            response.sendRedirect("order-confirmation.jsp");
        } else {
            // If it fails, redirect back to the cart with an error message
            request.setAttribute("error", "There was an error placing your order. Please try again.");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        }
    }
}