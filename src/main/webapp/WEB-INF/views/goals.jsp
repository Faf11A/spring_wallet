<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Goals</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
        crossorigin="anonymous">

  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/goals.css">
</head>
<body>
<%@ include file="header.jsp" %>c

<div class="container mt-5">
  <h2>Goals</h2>

  <!-- Форма для добавления новой цели -->
  <form action="${pageContext.request.contextPath}/goals" method="post">
    <div class="form-group">
      <label for="name">Goal Name:</label>
      <input type="text" class="form-control" id="name" name="name" required>
    </div>
    <div class="form-group">
      <label for="targetAmount">Target Amount:</label>
      <input type="number" class="form-control" id="targetAmount" name="targetAmount" required>
    </div>
    <div class="form-group">
      <label for="targetDate">Target Date:</label>
      <input type="date" class="form-control" id="targetDate" name="targetDate" required>
    </div>
    <button type="submit" class="btn btn-primary">Add Goal</button>
  </form>

  <!-- Вывод списка целей -->
  <c:if test="${not empty goals}">
    <hr>
    <h4>Current Goals:</h4>
    <c:forEach var="goal" items="${goals}" varStatus="status">
      <div class="card mt-3">
        <div class="card-body">
          <h5 class="card-title ">${goal.name}</h5>
          <p class="card-text">
            Target Amount: ${goal.targetAmount}<br>
            Current Amount: ${goal.currentAmount}<br>
            Progress: ${((goal.currentAmount / goal.targetAmount) * 100)}%
          <div class="progress mt-2">
            <div class="progress-bar" style="width: ${((goal.currentAmount / goal.targetAmount) * 100)}%"></div>
          </div>
          </p>
        </div>
      </div>
    </c:forEach>
  </c:if>
</div>

</body>
</html>
