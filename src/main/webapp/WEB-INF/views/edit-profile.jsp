<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
            margin: 0;
        }

        .container {
            width: 50%;
            margin: 0 auto;
        }

        .form-container {
            background-color: #ffffff;
            border: 1px solid #ced4da;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin-top: 50px;
        }

        h2 {
            color: #495057;
        }

        form {
            margin-top: 20px;
        }

        fieldset {
            margin-bottom: 20px;
            border: 1px solid #ced4da;
            border-radius: 4px;
            padding: 10px;
        }

        legend {
            color: #007bff;
            font-weight: bold;
        }

        label {
            display: block;
            margin-bottom: 8px;
        }

        input {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            box-sizing: border-box;
        }

        button {
            background-color: #007bff;
            color: #fff;
            padding: 10px 30px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            display: block;
            margin: 0 auto;
        }

        button:hover {
            background-color: #0056b3;
        }

        .hr-divider {
            border-top: 1px solid #ced4da;
            margin: 20px 0;
        }

        .error-message {
            color: #dc3545;
            margin-top: 10px;
        }

        .success-message {
            color: #28a745;
            margin-top: 10px;
        }
    </style>
    <title>Edit Profile</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
</head>
<body>

<div class="container">
    <div class="form-container">
        <h2>Edit Profile</h2>

        <form action="/edit-profile" method="post">
            <fieldset>
                <legend>Account Information</legend>
                <label for="username">Login:</label>
                <small>Login cannot be changed after registration</small>
                <input type="text" id="username" name="username" value="${user.login}" required>

                <label for="password">Password:</label>
                <input type="password" id="password" name="password" value="${user.password}" required>
            </fieldset>

            <fieldset>
                <legend>Personal Information</legend>
                <label for="firstName">First Name:</label>
                <input type="text" id="firstName" name="firstName" value="${userDetails.firstName}" required>

                <label for="lastName">Last Name:</label>
                <input type="text" id="lastName" name="lastName" value="${userDetails.lastName}" required>

                <label>Date of Birth:</label>
                <div>
                    <fmt:formatDate value="${formattedDate != null ? formattedDate : userDetails.birthDate}"
                                    pattern="yyyy-MM-dd"/>
                </div>
                <small>Date of Birth cannot be changed after registration</small>

            </fieldset>


            <fieldset>
                <legend>Contact Information</legend>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${userDetails.email}" required>
            </fieldset>
            <button type="submit">Submit</button>

        </form>

        <div class="hr-divider"></div>

        <div class="error-message">
            ${error}
        </div>

        <div class="success-message">
            ${success}
        </div>
    </div>
</div>
</body>
</html>
