<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Wallet</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/wallet.css">
</head>
<body>

<%@ include file="header.jsp" %>

<div class="container">
    <div class="wallet-card">
        <h2>${name_wall}</h2>

        <fieldset>
            <legend>Add Funds</legend>
            <form action="/wallet" method="post">
                <label for="amount">Amount:</label>
                <input type="text" id="amount" name="amount" required>
                <button type="submit" class="green-button">Add Funds</button>
            </form>
        </fieldset>

        <div class="balance">
            Balance: $<c:out value="${balance}"/>
        </div>

        <fieldset class="operation-details">
            <legend>Last Operation</legend>
            <div class="last-operation">
                <p>Last operation: ${lastTrCategory} | Amount: $${lastTrAmount} | Date: ${lastTrDate}</p>
            </div>
        </fieldset>

        <button id="toggleTransactionForm" class="green-button">Add new transaction</button>

        <div id="transactionForm">
            <fieldset>
                <legend>New Transaction</legend>
                <form action="/add-transaction" method="post">
                    <!-- Amount -->
                    <label for="transactionAmount">Amount:</label>
                    <input type="text" id="transactionAmount" name="amount" required>

                    <!-- Date -->
                    <label for="transactionDate">Date:</label>
                    <input type="date" id="transactionDate" name="date" required>

                    <!-- Category -->
                    <label for="transactionCategory">Category:</label>
                    <select id="transactionCategory" name="category" required>
                        <option value="" disabled selected>Choose category</option>
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
                    </select>

                    <label for="transactionDescription">Description:</label>
                    <input type="text" id="transactionDescription" name="description">

                    <button type="submit" class="green-button">Add Transaction</button>
                </form>
            </fieldset>
        </div>
    </div>
</div>

<script>
    var transactionForm = document.getElementById('transactionForm');
    var toggleButton = document.getElementById('toggleTransactionForm');

    toggleButton.addEventListener('click', function () {
        transactionForm.style.maxHeight = transactionForm.style.maxHeight ? null : transactionForm.scrollHeight + 'px';
        toggleButton.innerText = transactionForm.style.maxHeight ? 'Hide' : 'Add new transaction';
    });
</script>

</body>
</html>