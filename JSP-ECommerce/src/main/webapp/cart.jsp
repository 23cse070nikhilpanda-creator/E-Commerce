<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <%-- For number formatting --%>
<%@ page import="com.ecommerce.model.User" %>

<%-- Retrieve the logged-in user from the session --%>
<%
    User user = (User) session.getAttribute("user");
    pageContext.setAttribute("loggedInUser", user);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Shopping Cart</title>
<style>
    body { font-family: sans-serif; }
    .container { width: 80%; margin: 20px auto; }
    .navbar { background-color: #333; overflow: hidden; color: white; padding: 14px 20px; }
    .navbar-right { float: right; }
    .navbar a { color: white; padding: 14px 16px; text-decoration: none; }
    table { width: 100%; border-collapse: collapse; margin-top: 20px; }
    th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
    th { background-color: #f2f2f2; }
    .cart-total { float: right; width: 30%; margin-top: 20px; border: 1px solid #ccc; padding: 15px; }
    .checkout-btn { background-color: #4CAF50; color: white; padding: 12px 20px; border: none; cursor: pointer; font-size: 16px; margin-top: 10px; width: 100%;}
    .empty-cart { text-align: center; font-size: 1.2em; color: grey; padding: 50px; }
</style>
</head>
<body>

<div class="navbar">
    <a href="index.jsp">Home</a>
    <div class="navbar-right">
        <a href="cart.jsp">Cart (${sessionScope.cart.items.size()})</a>
        <c:choose>
            <c:when test="${not empty loggedInUser}">
                <a>Welcome, ${loggedInUser.name}</a>
                <a href="user?action=logout">Logout</a>
            </c:when>
            <c:otherwise>
                <a href="login.jsp">Login</a>
                <a href="register.jsp">Register</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<div class="container">
    <h1>Your Shopping Cart</h1>

    <%-- Check if the cart is empty or doesn't exist --%>
    <c:if test="${empty sessionScope.cart or empty sessionScope.cart.items}">
        <div class="empty-cart">
            <p>Your cart is empty.</p>
            <a href="index.jsp">Continue Shopping</a>
        </div>
    </c:if>

    <%-- If the cart is not empty, display the items --%>
    <c:if test="${not empty sessionScope.cart.items}">
        <table>
            <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Total</th>
                <th>Action</th>
            </tr>
            <c:forEach var="item" items="${sessionScope.cart.items}">
                <tr>
                    <td>${item.product.name}</td>
                    <td><fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="$"/></td>
                    <td>
                        <%-- Form to update quantity --%>
                        <form action="cart" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="productId" value="${item.product.id}">
                            <input type="number" name="quantity" value="${item.quantity}" min="1" style="width: 50px;">
                            <input type="submit" value="Update">
                        </form>
                    </td>
                    <td><fmt:formatNumber value="${item.totalPrice}" type="currency" currencySymbol="$"/></td>
                    <td>
                        <%-- Form to remove item --%>
                        <form action="cart" method="post" style="display:inline;">
                             <input type="hidden" name="action" value="remove">
                             <input type="hidden" name="productId" value="${item.product.id}">
                             <input type="submit" value="Remove">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        
        <div class="cart-total">
            <h3>Cart Summary</h3>
            <p>Subtotal: <fmt:formatNumber value="${sessionScope.cart.total}" type="currency" currencySymbol="$"/></p>
            <hr>
            <h4>Total: <fmt:formatNumber value="${sessionScope.cart.total}" type="currency" currencySymbol="$"/></h4>
            <form action="checkout.jsp" method="get">
                 <button type="submit" class="checkout-btn">Proceed to Checkout</button>
            </form>
        </div>
    </c:if>
</div>
</body>
</html>