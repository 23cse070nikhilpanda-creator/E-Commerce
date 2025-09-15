<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register - E-Commerce</title>
<style>
    body { font-family: sans-serif; }
    .container { width: 300px; margin: 50px auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }
    input[type=text], input[type=password], input[type=email] { width: 100%; padding: 8px; margin: 5px 0 15px 0; display: inline-block; border: 1px solid #ccc; box-sizing: border-box; }
    button { background-color: #4CAF50; color: white; padding: 10px 15px; margin: 8px 0; border: none; cursor: pointer; width: 100%; }
    .login-link { text-align: center; }
</style>
</head>
<body>

<div class="container">
    <h2>Create an Account</h2>
    <form action="user" method="post">
        <input type="hidden" name="action" value="register">

        <label for="name"><b>Full Name</b></label>
        <input type="text" placeholder="Enter Full Name" name="name" required>

        <label for="email"><b>Email</b></label>
        <input type="email" placeholder="Enter Email" name="email" required>

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password" required>
        
        <label for="phone"><b>Phone</b></label>
        <input type="text" placeholder="Enter Phone Number" name="phone">

        <button type="submit">Register</button>
    </form>
    <div class="login-link">
        <p>Already have an account? <a href="login.jsp">Login here</a></p>
    </div>
</div>

</body>
</html>