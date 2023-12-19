<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/edit-profile.css">

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
