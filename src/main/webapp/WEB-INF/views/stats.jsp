<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Statistics</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/stats.css">
</head>
<body>

<%@ include file="header.jsp" %>

<div class="main-content">
    <h2>Your stats</h2>

    <div class="goals-statistics">
        <h2>Goals</h2>

        <div class="goals-details">
            <p>
                <span class="goal-marker" style="background-color: #ababff;"></span>
                Current goals: ${percentCurrentGoals}% || ${countOfCurrentGoals} goals
            </p>
            <p>
                <span class="goal-marker" style="background-color: #6fab6f;"></span>
                Completed goals: ${percentDoneGoals}% || ${countOfDoneGoals} goals
            </p>
            <p>
                <span class="goal-marker" style="background-color: #ff8282;"></span>
                Expired goals: ${percentExpiredGoals}% || ${countOfExpiredGoals} goals
            </p>
            <p>Count of all goals: ${countOfGoals} goals</p>
        </div>

        <div class="progress-bar">
            <div class="progress-done" style="width: ${percentDoneGoals}%; background-color: #6fab6f;"></div>
            <div class="progress-current" style="width: ${percentCurrentGoals}%; background-color: #ababff;"></div>
            <div class="progress-expired" style="width: ${percentExpiredGoals}%; background-color: #ff8282;"></div>
        </div>
    </div>

    <div class="goals-statistics">
        <h2>Transactions and deposits</h2>

        <div class="goals-details">
            <p>Transaction counter: ${countOfTransactions}</p>
            <p>Amount of transactions: $${sumOfTr}</p>
            <p>Deposits counter: ${countOfAddAmount}</p>
            <p>Amount of deposits: $${amountOfAdds}</p>
        </div>

    </div>

    <div class="goals-statistics">
        <h2>Transactions by category</h2>
        <fieldset>
            <legend>Groceries</legend>
            <p>Transactions counter: ${countOfAddAmountCat1}</p>
            <p>Amount of transactions: $${sumOfTrCat1}</p>
        </fieldset>

        <fieldset>
            <legend>Entertainment</legend>
            <p>Transactions counter: ${countOfAddAmountCat2}</p>
            <p>Amount of transactions: $${sumOfTrCat2}</p>
        </fieldset>

        <fieldset>
            <legend>Transportation</legend>
            <p>Transactions counter: ${countOfAddAmountCat3}</p>
            <p>Amount of transactions: $${sumOfTrCat3}</p>
        </fieldset>

        <fieldset>
            <legend>Housing</legend>
            <p>Transactions counter: ${countOfAddAmountCat4}</p>
            <p>Amount of transactions: $${sumOfTrCat4}</p>
        </fieldset>

        <fieldset>
            <legend>Health</legend>
            <p>Transactions counter: ${countOfAddAmountCat5}</p>
            <p>Amount of transactions: $${sumOfTrCat5}</p>
        </fieldset>

        <fieldset>
            <legend>Personal Expenses</legend>
            <p>Transactions counter: ${countOfAddAmountCat6}</p>
            <p>Amount of transactions: $${sumOfTrCat6}</p>
        </fieldset>

        <fieldset>
            <legend>Education</legend>
            <p>Transactions counter: ${countOfAddAmountCat7}</p>
            <p>Amount of transactions: $${sumOfTrCat7}</p>
        </fieldset>

        <fieldset>
            <legend>Bank Transfers</legend>
            <p>Transactions counter: ${countOfAddAmountCat8}</p>
            <p>Amount of transactions: $${sumOfTrCat8}</p>
        </fieldset>

        <fieldset>
            <legend>Travel</legend>
            <p>Transactions counter: ${countOfAddAmountCat9}</p>
            <p>Amount of transactions: $${sumOfTrCat9}</p>
        </fieldset>

        <fieldset>
            <legend>Electronics</legend>
            <p>Transactions counter: ${countOfAddAmountCat10}</p>
            <p>Amount of transactions: $${sumOfTrCat10}</p>
        </fieldset>

        <fieldset>
            <legend>Your goals</legend>
            <p>Transactions counter: ${countOfAddAmountCat12}</p>
            <p>Amount of transactions: $${sumOfTrCat12}</p>
        </fieldset>
    </div>

</div>
</body>
</html>
