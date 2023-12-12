<!DOCTYPE html>
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
    }

    nav {
      display: flex;
      justify-content: center;
      flex-grow: 1;
    }

    nav a {
      color: #fff;
      text-decoration: none;
      margin: 0 20px;
    }

    #profile {
      margin-right: 20px;
    }
  </style>
</head>
<body>

<header>
  <nav>
    <a href="#">Home</a>
    <a href="#">Goals</a>
    <a href="#">History</a>
  </nav>

  <div id="profile">
    <img src="/images/profile.png">
    ${username}
  </div>
</header>

</body>
</html>
