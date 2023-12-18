<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Wallet</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
            margin: 0;
        }

        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 0 20px;
        }

        .wallet-card {
            background-color: #ffffff;
            border: 1px solid #ced4da;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin-top: 20px;
            position: relative;
        }

        h2 {
            color: #495057;
            margin-top: 0;
        }

        label {
            display: block;
            margin-bottom: 8px;
        }

        input, select {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            box-sizing: border-box;
        }

        .green-button {
            background-color: rgba(0, 128, 0, 0.8);
            color: #fff;
            padding: 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            display: block;
            margin: 10px auto;
            width: 50%;
        }

        .green-button:hover {
            background-color: rgba(0, 128, 0, 1);
        }

        .balance,
        .wallet-name {
            position: absolute;
            top: 20px;
            right: 20px;
            font-size: 1.2em;
        }

        .operation-details {
            margin-top: 20px;
            border-radius: 8px;
            overflow: hidden;
        }

        .last-operation {
            padding: 20px;
            text-align: center;
        }

        fieldset {
            border: 1px solid #ced4da;
            border-radius: 8px;
            margin-bottom: 20px;
            padding: 10px;
        }

        #transactionForm {
            max-height: 0;
            overflow: hidden;
            transition: max-height 0.3s ease-out;
        }

        #toggleTransactionForm {
            cursor: pointer;
            border: none;
            background: none;
            font-size: 1.2em;
            color: #007bff;
            outline: none;
        }
    </style>
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
            Balance: $<c:out value="${balance}" />
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
                        <option value="2" >Entertainment</option>
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

                    <!-- Description -->
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
