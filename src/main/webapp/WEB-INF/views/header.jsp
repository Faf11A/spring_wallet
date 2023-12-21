<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/header.css">
</head>
<body>

<header>
    <nav>
        <a href="/wallet">Wallet</a>
        <a href="/goals">Goals</a>
        <a href="/history">History</a>
        <a href="/stats">Statistics</a>

    </nav>

    <div id="profile">
        <span id="username">${username}</span>
        <span id="arrow">&#9662;</span>
        <div id="additionalLinks">
            <ul>
                <li><a href="/edit-profile">Edit Profile</a></li>
                <li><a href="/logout">Logout</a></li>
            </ul>
        </div>
    </div>
</header>

</body>
</html>
