<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Checkout</title>
<style>
    body { font-family: sans-serif; }
    .container { width: 60%; margin: 20px auto; display: flex; gap: 20px;}
    .order-summary, .shipping-info { flex: 1; border: 1px solid #ccc; padding: 20px; }
    button { background-color: #4CAF50; color: white; padding: 12px 20px; border: none; cursor: pointer; font-size: 16px; width: 100%;}
</style>
</head>
<body>

<div class="container">
    <div class="order-summary">
        <h2>Order Summary</h2>
        <c:if test="${not empty sessionScope.cart.items}">
             <ul>
                 <c:forEach var="item" items="${sessionScope.cart.items}">
                     <li>${item.product.name} (x${item.quantity}) - <fmt:formatNumber value="${item.totalPrice}" type="currency" currencySymbol="$"/></li>
                 </c:forEach>
             </ul>
             <hr>
             <h4>Total: <fmt:formatNumber value="${sessionScope.cart.total}" type="currency" currencySymbol="$"/></h4>
        </c:if>
    </div>
    <div class="shipping-info">
        <h2>Shipping Information</h2>
        <%-- In a real app, this form would have address fields. We'll keep it simple. --%>
        <form action="order" method="post">
            <p>Ready to place your order?</p>
            <button type="submit">Place Order</button>
        </form>
         <c:if test="${empty sessionScope.user}">
            <p style="color:red;">You must be logged in to place an order.</p>
            <a href="login.jsp">Login Here</a>
        </c:if>
    </div>
</div>
</body>
</html>