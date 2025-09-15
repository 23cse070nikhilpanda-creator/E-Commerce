<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login - E-Commerce</title>
<style>
    body { font-family: sans-serif; }
    .container { width: 300px; margin: 50px auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }
    input[type=text], input[type=password], input[type=email] { width: 100%; padding: 8px; margin: 5px 0 15px 0; display: inline-block; border: 1px solid #ccc; box-sizing: border-box; }
    button { background-color: #008CBA; color: white; padding: 10px 15px; margin: 8px 0; border: none; cursor: pointer; width: 100%; }
    .register-link { text-align: center; }
    .message { color: green; text-align: center; }
    .error { color: red; text-align: center; }
</style>
</head>
<body>

<div class="container">
    <h2>Login</h2>
    
    <c:if test="${not empty message}">
        <p class="message">${message}</p>
    </c:if>

    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <form action="user" method="post">
        <input type="hidden" name="action" value="login">

        <label for="email"><b>Email</b></label>
        <input type="email" placeholder="Enter Email" name="email" required>

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password" required>

        <button type="submit">Login</button>
    </form>
    <div class="register-link">
        <p>Don't have an account? <a href="register.jsp">Register here</a></p>
    </div>
</div>

</body>
</html>