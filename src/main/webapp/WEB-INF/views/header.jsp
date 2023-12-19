<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
        }

        header {
            background-color: rgb(114, 114, 114);
            color: #fff;
            padding: 15px 0;
            text-align: center;
            display: flex;
            justify-content: space-between;
            align-items: center;
            position: relative;
        }

        nav {
            display: flex;
            flex-grow: 1;
            margin-left: 20px;
        }

        nav a {
            color: #fff;
            text-decoration: none;
            margin: 0 10px;
        }

        #profile {
            position: relative;
            user-select: none;
            z-index: 1;
            margin-right: 20px;
        }

        #arrow {
            margin-left: 5px;
            margin-right: 10px;
            transition: transform 0.3s;
        }

        #additionalLinks {
            display: none;
            position: absolute;
            top: 100%;
            right: 0;
            background-color: rgba(255, 255, 255, 0.9);
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 4px;
            white-space: nowrap;
            text-align: left;
        }

        #profile:hover #additionalLinks {
            display: block;
        }

        #additionalLinks ul {
            list-style: none;
            margin: 0;
            padding: 0;
        }

        #additionalLinks li {
            display: block;
        }

        #additionalLinks a {
            color: #333;
            text-decoration: none;
            padding: 8px;
            transition: background-color 0.3s;
            display: block;
        }

        #additionalLinks a:hover {
            background-color: #f4f4f4;
        }
    </style>
</head>
<body>

<header>
    <nav>
        <a href="/wallet">Wallet</a>
        <a href="#">Goals</a>
        <a href="/history">History</a>

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
