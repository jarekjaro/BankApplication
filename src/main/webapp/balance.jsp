<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <noscript>
        Sorry...JavaScript is needed to go ahead.
    </noscript>
    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
    <title>Balance</title>
    <style>
        h1 {
            color: #046380;
            alignment: center;
        }

        h2 {
            alignment: center;
            color: #046380;
        }

        #amountError {
            color: crimson;
            font-size: 12px;
        }

        tr {
            color: #046380;
        }
    </style>
    <title>Balance</title>
</head>
<body bgcolor="#EFECCA">
<h1 align="center">Balance</h1>
<br>
<h2 align="center">Balance is: <%=session.getAttribute("balance")%>
</h2>

</body>
</html>