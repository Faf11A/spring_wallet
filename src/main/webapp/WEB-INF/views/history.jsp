<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Transaction History</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha384-GLhlTQ8iKt6UuNl7v7tayvNcZl9pKpRRnMPWSCKJ2UqDhm4h7jFTt8+Oqt6+HR6P" crossorigin="anonymous">

  <style>
    body {
      font-family: 'Arial', sans-serif;
      background-color: #f8f9fa;
      margin: 0;
    }

    .container {
      max-width: 1600px;
      margin: 20px auto;
      padding: 0 20px;
    }

    .history-card {
      background-color: #ffffff;
      border: 1px solid #ced4da;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      padding: 20px;
      margin-top: 20px;
    }

    h2 {
      text-align: center;
      color: #495057;
      margin-top: 0;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
    }

    th, td {
      border: 1px solid #ced4da;
      padding: 12px;
      text-align: left;
    }

    th {
      background-color: #007bff;
      color: #fff;
    }

    .filter-fieldset, .sort-fieldset, .sort-order-fieldset, .transaction-table-fieldset {
      border: 1px solid #ced4da;
      border-radius: 8px;
      padding: 10px;
      margin-top: 10px;
      text-align: center; /* Добавлен стиль для выравнивания по центру */
    }

    .filter-legend, .sort-legend, .sort-order-legend, .transaction-table-legend {
      color: #007bff;
      font-weight: bold;
    }

    .filter-label, .sort-label, .sort-order-label {
      margin-right: 10px;
    }

    .sort-links {
      margin-top: 10px;
    }

    .sort-links a {
      margin-right: 10px;
      text-decoration: none;
      color: #007bff;
    }

    .fa-sort-up, .fa-sort-down {
      margin-left: 5px;
      color: #007bff;
    }

    .sort-links a:hover {
      text-decoration: underline;
    }

    .green-button {
      background-color: rgba(106, 192, 106, 0.8);
      color: #000000;
      padding: 12px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      display: block;
      margin: 10px auto;
      width: 50%;
      margin-bottom: 30px;
    }

    .green-button:hover {
      background-color: rgba(0, 128, 0, 1);
    }
  </style>
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
          <select name="selectedCategory">
            <option value="all">All Categories</option>
            <c:forEach var="category" items="${categories}">
              <option value="${category.categoryId}" ${param.selectedCategory == category.categoryId ? 'selected' : ''}>${category.categoryName}</option>
            </c:forEach>
          </select>
        </label>
      </fieldset>

      <fieldset class="sort-fieldset">
        <legend class="sort-legend">Sort by</legend>
        <label class="sort-label">
          <input type="radio" name="sortBy" value="amount" ${param.sortBy == 'amount' ? 'checked' : ''}> Amount
        </label>
        <label class="sort-label">
          <input type="radio" name="sortBy" value="date" ${param.sortBy == 'date' ? 'checked' : ''}> Date
        </label>
        <label class="sort-label">
          <input type="radio" name="sortBy" value="category" ${param.sortBy == 'category' ? 'checked' : ''}> Category
        </label>

        <input type="hidden" name="sortOrder" value="${param.sortOrder}" />
      </fieldset>

      <fieldset class="sort-order-fieldset">
        <legend class="sort-order-legend">Sort Order</legend>
        <label class="sort-order-label"><input type="radio" name="sortOrder" value="asc" ${param.sortOrder == 'asc' ? 'checked' : ''}>Ascending</label>
        <label class="sort-order-label"><input type="radio" name="sortOrder" value="desc" ${param.sortOrder == 'desc' ? 'checked' : ''}>Descending</label>
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
