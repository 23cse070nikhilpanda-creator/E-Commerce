<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Products</title>
<link rel="stylesheet" href="admin-styles.css"> <%-- Common styles for tables, buttons etc. --%>
</head>
<body>
    <jsp:include page="dashboard.jsp" />
    <div class="main-content">
        <h1>Product Management</h1>
        <a href="dashboard?action=showAddProductForm" class="btn btn-primary">Add New Product</a>
        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${productList}">
                    <tr>
                        <td>${product.id}</td>
                        <td>${product.name}</td>
                        <td><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="$"/></td>
                        <td>
                            <a href="dashboard?action=showEditProductForm&id=${product.id}" class="btn btn-secondary">Edit</a>
                            <form action="dashboard" method="post" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this product?');">
                                <input type="hidden" name="action" value="deleteProduct">
                                <input type="hidden" name="id" value="${product.id}">
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>