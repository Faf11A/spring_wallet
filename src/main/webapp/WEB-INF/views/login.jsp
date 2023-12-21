<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/login.css">
</head>
<body>

<c:choose>
    <c:when test="${param.mode == 'register'}">
        <form action="/register" method="post">
            <h2>Sign up</h2>

            <label for="firstname">First Name:</label>
            <input type="text" id="firstname" name="firstname" required>

            <label for="lastname">Last Name:</label>
            <input type="text" id="lastname" name="lastname" required>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>

            <label for="login">Login:</label>
            <input type="text" id="login_reg" name="login" required>

            <label for="password">Password:</label>
            <input type="password" id="password_reg" name="password" required>

            <label for="dob">Date of Birth:</label>
            <small>Date of Birth cannot be changed after registration</small>
            <input type="date" id="dob" name="dob" required>


            <button type="submit">Sing up</button>

            <p class="login-link">Already have an account? <a href="?mode=login">Login here</a></p>
        </form>
    </c:when>

    <c:otherwise>
        <form action="/login" method="post">
            <h2>Sign in</h2>
            <label for="login">Login:</label>
            <input type="text" id="login" name="login" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>

            <button type="submit">Sign in</button>

            <p class="login-link">Don't have an account? <a href="?mode=register">Register here</a></p>
        </form>
    </c:otherwise>
</c:choose>

</body>
</html>