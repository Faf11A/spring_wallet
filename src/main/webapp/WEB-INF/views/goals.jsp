<%@ page import="java.time.LocalDate" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Goals</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/goals.css">
    <style>
        .toggle-button-container {
            text-align: center;
            margin-bottom: 10px;
        }

        .toggle-btn {
            margin-right: 10px;
        }

        .hidden-section {
            display: none;
        }
    </style>
</head>
<body>
<%@ include file="header.jsp" %>

<div class="container mt-5">
    <div class="row">
        <div class="col">
            <h2>Goals</h2>
        </div>
        <div class="col text-right">
            <h5>Balance: $${balance}</h5>
        </div>
    </div>

    <fieldset>
        <div class="card mt-3">
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/goals" method="post">
                    <h5>Add Goal</h5>
                    <div class="form-group">
                        <label for="goalName">Goal Name:</label>
                        <input type="text" class="form-control" id="goalName" name="goalName" required>
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
            </div>
        </div>
    </fieldset>

    <!-- Вывод списка текущих целей -->
    <c:if test="${not empty currentGoals}">
        <hr>
        <h4>Current Goals:</h4>
        <c:set var="currentDate" value="<%= LocalDate.now() %>" scope="page"/>
        <c:forEach var="goal" items="${currentGoals}" varStatus="status">
            <c:if test="${goal.targetDate.isAfter(currentDate)}"> <!-- Проверка на истекшие цели -->
                <div class="card mt-3">
                    <div class="card-body">
                        <h5 class="card-title">${goal.goalName}</h5>
                        <p class="card-text">
                            Target Amount: ${goal.targetAmount}<br>
                            Current Amount: ${goal.currentAmount}<br>
                            Target Date: ${goal.targetDate}
                        </p>
                        <div class="progress mt-2" style="height: 30px;">
                            <div class="progress-bar" style="width: ${((goal.currentAmount / goal.targetAmount) * 100)}%;">
                                    ${((goal.currentAmount / goal.targetAmount) * 100)}%
                            </div>
                        </div>
                        <div class="row mt-2">
                            <div class="col">
                                <!-- Форма для ввода суммы и кнопки "Add Amount" -->
                                <form action="${pageContext.request.contextPath}/addAmountToGoal" method="post" class="form-inline">
                                    <input type="hidden" name="goalId" value="${goal.goalId}">
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-success">Add Amount</button>
                                        <input type="number" class="form-control" id="amount_${goal.goalId}" name="amount" required>
                                    </div>
                                </form>
                            </div>
                            <div class="col text-right">
                                <!-- Желтая кнопка "Edit Goal" и Красная кнопка "Delete Goal" -->
                                <form action="${pageContext.request.contextPath}/editGoal" method="post" style="display: inline;">
                                    <input type="hidden" name="goalId" value="${goal.goalId}">
                                    <button type="submit" class="btn btn-warning">Edit Goal</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/deleteGoal" method="post" style="display: inline;">
                                    <input type="hidden" name="goalId" value="${goal.goalId}">
                                    <button type="submit" class="btn btn-danger">Delete Goal</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </c:if>

    <!-- Вывод списка выполненных целей -->
    <c:if test="${not empty completedGoals}">
        <hr>
        <div class="toggle-button-container">
            <button id="toggleCompletedGoalsBtn" class="btn btn-secondary toggle-btn">Show Completed Goals</button>
            <button id="toggleExpiredGoalsBtn" class="btn btn-secondary toggle-btn">Show Expired Goals</button>
        </div>
        <div id="completedGoalsSection" class="hidden-section" style="display: none;">
            <h4>Completed Goals:</h4>
            <c:forEach var="goal" items="${completedGoals}" varStatus="status">
                <div class="card mt-3">
                    <div class="card-body">
                        <h5 class="card-title">${goal.goalName}</h5>
                        <p class="card-text">
                            Target Amount: ${goal.targetAmount}<br>
                            Current Amount: ${goal.currentAmount}<br>
                            Target Date: ${goal.targetDate}
                        </p>
                        <div class="progress mt-2" style="height: 30px;">
                            <div class="progress-bar" style="width: ${((goal.currentAmount / goal.targetAmount) * 100)}%;">
                                    ${((goal.currentAmount / goal.targetAmount) * 100)}%
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>

    <!-- Вывод списка истекших целей -->
    <c:if test="${not empty expiredGoals}">
        <hr>
        <div id="expiredGoalsSection" class="hidden-section" style="display: none;">
            <h4>Expired Goals:</h4>
            <c:forEach var="goal" items="${expiredGoals}" varStatus="status">
                <div class="card mt-3">
                    <div class="card-body">
                        <h5 class="card-title">${goal.goalName}</h5>
                        <p class="card-text">
                            Target Amount: ${goal.targetAmount}<br>
                            Target Date: ${goal.targetDate}
                        </p>
                        <div class="row mt-2">
                            <div class="col">
                                <!-- Поле для изменения даты -->
                                <form action="${pageContext.request.contextPath}/editGoalDate" method="post" class="form-inline">
                                    <input type="hidden" name="goalId" value="${goal.goalId}">
                                    <div class="form-group">
                                        <input type="date" class="form-control" id="newDate_${goal.goalId}" name="newDate" required>
                                        <button type="submit" class="btn btn-danger">Change Date</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>

    <script>
        var completedGoalsSection = document.getElementById("completedGoalsSection");
        var expiredGoalsSection = document.getElementById("expiredGoalsSection");
        var toggleCompletedGoalsBtn = document.getElementById("toggleCompletedGoalsBtn");
        var toggleExpiredGoalsBtn = document.getElementById("toggleExpiredGoalsBtn");

        toggleCompletedGoalsBtn.addEventListener("click", function () {
            if (completedGoalsSection.style.display === "none" || completedGoalsSection.style.display === "") {
                completedGoalsSection.style.display = "block";
                toggleCompletedGoalsBtn.innerText = "Hide Completed Goals";
            } else {
                completedGoalsSection.style.display = "none";
                toggleCompletedGoalsBtn.innerText = "Show Completed Goals";
            }
            expiredGoalsSection.style.display = "none";
            toggleExpiredGoalsBtn.innerText = "Show Expired Goals";
        });

        toggleExpiredGoalsBtn.addEventListener("click", function () {
            if (expiredGoalsSection.style.display === "none" || expiredGoalsSection.style.display === "") {
                expiredGoalsSection.style.display = "block";
                toggleExpiredGoalsBtn.innerText = "Hide Expired Goals";
            } else {
                expiredGoalsSection.style.display = "none";
                toggleExpiredGoalsBtn.innerText = "Show Expired Goals";
            }
            completedGoalsSection.style.display = "none";
            toggleCompletedGoalsBtn.innerText = "Show Completed Goals";
        });
    </script>
</div>

</body>
</html>
