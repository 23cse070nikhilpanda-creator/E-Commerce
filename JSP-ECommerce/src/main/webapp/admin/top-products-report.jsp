<%-- Create this new file at admin/top-products-report.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Top Products Report</title>
<link rel="stylesheet" href="admin-styles.css">
</head>
<body>
    <jsp:include page="dashboard.jsp" />
    <div class="main-content">
        <h1>Top Selling Products</h1>
        <table class="data-table">
            <thead>
                <tr>
                    <th>Rank</th>
                    <th>Product ID</th>
                    <th>Product Name</th>
                    <th>Total Quantity Sold</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${topProductsList}" varStatus="loop">
                    <tr>
                        <td>${loop.count}</td>
                        <td>${item.product.id}</td>
                        <td>${item.product.name}</td>
                        <td>${item.totalQuantitySold}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>