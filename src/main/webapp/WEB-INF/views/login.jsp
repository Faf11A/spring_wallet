<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Login Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }

        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        label {
            display: block;
            margin-bottom: 10px;
        }

        input {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            box-sizing: border-box;
        }

        button {
            background-color: #4caf50;
            color: white;
            padding: 12px 20px;
            font-size: 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-bottom: 15px;
            width: 100%;
        }

        button:hover {
            background-color: #45a049;
        }

        a {
            color: #4caf50;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        .login-link {
            margin-top: 20px;
        }
    </style>
</head>
<body>

<c:choose>
    <c:when test="${param.mode == 'register'}">
        <form action="/register" method="post">
            <h2>Registration</h2>

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
            <input type="date" id="dob" name="dob" required>

            <button type="submit">Register</button>

            <p class="login-link">Already have an account? <a href="?mode=login">Login here</a></p>
        </form>
    </c:when>

    <c:otherwise>
        <form action="/login" method="post">
            <h2>Login</h2>
            <label for="login">Login:</label>
            <input type="text" id="login" name="login" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>

            <button type="submit">Login</button>

            <p class="login-link">Don't have an account? <a href="?mode=register">Register here</a></p>
        </form>
    </c:otherwise>
</c:choose>

</body>
</html>