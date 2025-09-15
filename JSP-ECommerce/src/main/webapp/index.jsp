<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>E-Commerce Home</title>
    <link rel="stylesheet" href="assets/css/style.css"> <%-- Link to the new CSS --%>
</head>
<body>
    <header class="navbar">
        <a href="home" class="logo">E-Commerce</a>
        <div class="navbar-right">
             <a href="cart.jsp">Cart (${empty sessionScope.cart ? 0 : sessionScope.cart.items.size()})</a>
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <a>Welcome, ${sessionScope.user.name}</a>
                    <a href="user?action=logout">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="login.jsp">Login</a>
                    <a href="register.jsp">Register</a>
                </c:otherwise>
            </c:choose>
        </div>
    </header>

    <div class="page-container">
        <aside class="filter-sidebar">
            <h3>Categories</h3>
            <a href="home" class="${empty selectedCategoryId ? 'active' : ''}">All Products</a>
            <c:forEach var="category" items="${categories}">
                <a href="home?category=${category.id}" class="${selectedCategoryId == category.id ? 'active' : ''}">
                    ${category.name}
                </a>
            </c:forEach>
        </aside>

        <main class="product-main">
            <div class="search-container">
                <form action="home" method="get">
                    <input type="text" name="search" placeholder="Search for products..." value="${searchQuery}" class="search-bar">
                    <%-- If a category is selected, keep it as a hidden field so search works within the category --%>
                    <c:if test="${not empty selectedCategoryId}">
                        <input type="hidden" name="category" value="${selectedCategoryId}">
                    </c:if>
                    <button type="submit" class="search-btn">Search</button>
                </form>
            </div>
            
            <div class="product-grid">
                <%-- If the products list is empty, show a message --%>
                <c:if test="${empty products}">
                     <p class="no-products-msg">No products found matching your criteria.</p>
                </c:if>

                <%-- Loop through and display each product card --%>
                <c:forEach var="product" items="${products}">
                    <div class="product-card">
                        <img src="${not empty product.imageUrl ? product.imageUrl : 'images/placeholder.png'}" alt="${product.name}">
                        <h3>${product.name}</h3>
                        <p class="price">$${product.price}</p>
                        <p>${product.description}</p>
                        <form action="cart" method="post">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="productId" value="${product.id}">
                            <button type="submit" class="add-to-cart-btn">Add to Cart</button>
                        </form>
                    </div>
                </c:forEach>
            </div>
        </main>
    </div>
</body>
</html>