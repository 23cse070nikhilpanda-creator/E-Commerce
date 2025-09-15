<%-- Create this new file at admin/sales-report.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sales Report</title>
<link rel="stylesheet" href="admin-styles.css">
</head>
<body>
    <jsp:include page="dashboard.jsp" />
    <div class="main-content">
        <h1>Sales Report</h1>
        <table class="data-table">
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Order Date</th>
                    <th>Status</th>
                    <th>Total Amount</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="grandTotal" value="0" />
                <c:forEach var="order" items="${orderList}">
                    <tr>
                        <td>${order.id}</td>
                        <td><fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                        <td>${order.status}</td>
                        <td><fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="$"/></td>
                    </tr>
                    <c:set var="grandTotal" value="${grandTotal + order.totalAmount}" />
                </c:forEach>
            </tbody>
        </table>
        <div class="report-summary">
            <h2>Total Revenue: <fmt:formatNumber value="${grandTotal}" type="currency" currencySymbol="$"/></h2>
        </div>
    </div>
</body>
</html>
