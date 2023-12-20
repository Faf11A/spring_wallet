<%@ page import="java.time.LocalDate" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Edit Goal</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
        crossorigin="anonymous">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/goals.css">
</head>
<body>
<%@ include file="header.jsp" %>

<div class="container mt-5">
  <h2>Edit Goal</h2>

  <form action="${pageContext.request.contextPath}/updateGoal" method="post">
    <input type="hidden" name="goalId" value="${goalId}">
    <div class="form-group">
      <label for="goalName">Goal Name:</label>
      <input type="text" class="form-control" id="goalName" name="goalName" value="${goalName}" required>
    </div>
    <div class="form-group">
      <label for="targetAmount">Target Amount:</label>
      <input type="number" class="form-control" id="targetAmount" name="targetAmount" value="${targetAmount}" required>
    </div>
    <div class="form-group">
      <label for="targetDate">Target Date:</label>
      <input type="date" class="form-control" id="targetDate" name="targetDate" value="${targetDate}" required>
    </div>
    <button type="submit" class="btn btn-primary">Save Changes</button>
  </form>

  <button class="btn btn-secondary mt-3" onclick="goBack()">Back</button>
</div>

<script>
  function goBack() {
    window.history.back();
  }
</script>
</body>
</html>

