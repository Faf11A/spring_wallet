<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Transaction History</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
          integrity="sha384-GLhlTQ8iKt6UuNl7v7tayvNcZl9pKpRRnMPWSCKJ2UqDhm4h7jFTt8+Oqt6+HR6P" crossorigin="anonymous">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/history.css">
</head>
<body>

<%@ include file="header.jsp" %>

<div class="container">
    <div class="history-card">
        <h2>Transaction History</h2>

        <form action="/history" method="post">

            <fieldset class="filter-fieldset">
                <legend class="filter-legend">Filter by Category</legend>
                <label class="filter-label">
                    <select id="transactionCategory" name="category" required>
                        <option value=12>All categories</option>
                        <option value="1">Groceries</option>
                        <option value="2">Entertainment</option>
                        <option value="3">Transportation</option>
                        <option value="4">Housing</option>
                        <option value="5">Health</option>
                        <option value="6">Personal Expenses</option>
                        <option value="7">Education</option>
                        <option value="8">Bank Transfers</option>
                        <option value="9">Travel</option>
                        <option value="10">Electronics</option>
                        <option value="11">Deposit</option>
                        <option value="12">Your goals</option>
                    </select>
                </label>
            </fieldset>

            <fieldset class="sort-fieldset">
                <legend class="sort-legend">Sort by</legend>
                <label class="sort-label">
                    <input type="radio" name="sortBy" value="amount" ${param.sortBy == 'amount' ? 'checked' : ''}>
                    Amount
                </label>
                <label class="sort-label">
                    <input type="radio" name="sortBy" value="date" ${param.sortBy == 'date' ? 'checked' : ''}> Date
                </label>
                <label class="sort-label">
                    <input type="radio" name="sortBy" value="category" ${param.sortBy == 'category' ? 'checked' : ''}>
                    Category
                </label>

                <input type="hidden" name="sortOrder" value="${param.sortOrder}"/>
            </fieldset>

            <fieldset class="sort-order-fieldset">
                <legend class="sort-order-legend">Sort Order</legend>
                <label class="sort-order-label"><input type="radio" name="sortOrder"
                                                       value="asc" ${param.sortOrder == 'asc' ? 'checked' : ''}>
                    Ascending</label>
                <label class="sort-order-label"><input type="radio" name="sortOrder"
                                                       value="desc" ${param.sortOrder == 'desc' ? 'checked' : ''}>
                    Descending</label>
            </fieldset>

            <button type="submit" class="green-button">Submit</button>

        </form>

        <fieldset class="transaction-table-fieldset">
            <legend class="transaction-table-legend">Transaction Table</legend>
            <table>
                <thead>
                <tr>
                    <th>Amount</th>
                    <th>Date</th>
                    <th>Category</th>
                    <th>Description</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="transaction" items="${transactions}">
                    <tr>
                        <td>${transaction.amount}</td>
                        <td>${transaction.date}</td>
                        <td>${transaction.category.category_name}</td>
                        <td>${transaction.description}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </fieldset>
    </div>
</div>

</body>
</html>
