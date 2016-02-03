<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>BankApplication_Main</title>
    <style>
        #menu td {
            padding: 30px;
            border: 1px groove #046380;
            color: red;
            font-weight: bold;
            text-align: center;
            cursor: hand;
        }

        h5 {
            color: #046380;
        }

        tr {
            color: #046380;
        }
    </style>
</head>
<body bgcolor="#EFECCA">
<h5 align="center"><%=session.getAttribute("clientName")%>
</h5>
<table id="menu" align="center">
    <tr>
        <td><a href="/balance">Balance</a></td>
        <td><a href="/withdraw.jsp">Withdraw</a></td>
    </tr>
    <tr>
        <%--<td><a href="/deposit.jsp">Deposit</a></td>--%>
        <td><a href="/sessions.jsp">Sessions</a></td>
        <td><a href="/logout">Exit</a></td>
    </tr>
</table>
</body>
</html>