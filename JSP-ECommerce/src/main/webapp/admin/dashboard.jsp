<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
<style>
    body { font-family: sans-serif; display: flex; margin: 0;}
    .sidebar { width: 220px; background: #2c3e50; color: white; height: 100vh; padding-top: 20px; position: fixed;}
    .sidebar h2 { text-align: center; }
    .sidebar a { display: block; color: white; padding: 15px 20px; text-decoration: none; border-left: 3px solid transparent;}
    .sidebar a:hover, .sidebar a.active { background: #34495e; border-left-color: #3498db; }
    .main-content { margin-left: 220px; flex-grow: 1; padding: 20px;}
</style>
</head>
<body>

<div class="sidebar">
    <h2>Admin Panel</h2>
    <a href="${pageContext.request.contextPath}/admin/dashboard.jsp">Dashboard</a>
    <hr style="border-color: #444;">
    <p class="sidebar-header">MANAGEMENT</p>
    <a href="${pageContext.request.contextPath}/admin/dashboard?action=listProducts">Manage Products</a>
    <a href="${pageContext.request.contextPath}/admin/dashboard?action=listOrders">Manage Orders</a>
    <a href="${pageContext.request.contextPath}/admin/dashboard?action=listUsers">Manage Users</a>
    <hr style="border-color: #444;">
    <p class="sidebar-header">REPORTS</p>
    <a href="${pageContext.request.contextPath}/admin/dashboard?action=salesReport">Sales Report</a>
    <a href="${pageContext.request.contextPath}/admin/dashboard?action=topProductsReport">Top Products</a>
    <hr style="border-color: #444;">
    <a href="${pageContext.request.contextPath}/user?action=logout">Logout</a>
</div>

<div class="main-content">
    <h1>Welcome, ${sessionScope.user.name}!</h1>
    <p>This is the admin dashboard. Select an option from the sidebar to manage the e-commerce site.</p>
    <%-- Dashboard stats will go here --%>
</div>

</body>
</html>