<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Users</title>
<link rel="stylesheet" href="admin-styles.css">
</head>
<body>
    <jsp:include page="dashboard.jsp" />
    <div class="main-content">
        <h1>User Management</h1>
        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Role</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${userList}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.email}</td>
                        <td>${user.phone}</td>
                        <td><span class="role-${user.role}">${user.role}</span></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="main-content">
    <h1>User Management</h1>
    <a href="dashboard?action=showAddUserForm" class="btn btn-primary">Add New User</a>
    <table class="data-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Role</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${userList}">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                    <td>${user.phone}</td>
                    <td><span class="role-${user.role}">${user.role}</span></td>
                    <td>
                        <%-- Prevent admin from deleting themselves --%>
                        <c:if test="${sessionScope.user.id != user.id}">
                            <form action="dashboard" method="post" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this user? This action cannot be undone.');">
                                <input type="hidden" name="action" value="deleteUser">
                                <input type="hidden" name="id" value="${user.id}">
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>