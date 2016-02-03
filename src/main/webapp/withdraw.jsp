<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <noscript>
        Sorry...JavaScript is needed to go ahead.
    </noscript>
    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        function validateAmount() {
            var amount = $('#amount').val();
            if (amount.isNumeric()) {
                $("#amountError").html("withdrawn: " + amount);
                return true;
            } else {
                $("#amountError").html("please use positive integers");
                return false;
            }
        }
    </script>
    <style>
        h1 {
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
    <title>Withdraw</title>
</head>
<body bgcolor="#EFECCA">
<h1 align="center">Withdraw</h1>
<form id="withdrawForm" method="POST" target="_self" action="/withdraw">
    <table id="menu" align="center">
        <tr>
            <td>Withdraw amount:</td>
            <td><input type="text" id="amount" name="amount"/>
                <div id="amountError"></div>
            </td>
        </tr>
        <tr>
            <td></td>
            <td align="right"><input type="submit" onclick="return validateAmount();">
            </td>
        </tr>
    </table>
</form>
</body>
</html>