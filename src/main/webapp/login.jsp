<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <noscript>
        Sorry...JavaScript is needed to go ahead.
    </noscript>
    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        function validateForm() {
            var name = $('#clientName').val();
            if (name.length < 2) {
                $("#nameError").html("please, provide your name");
                return false;
            } else {
                return true;
            }
        }
    </script>
    <style>
        h1 {
            color: #046380;
        }

        #nameError {
            color: crimson;
            font-size: 12px;
        }

        tr {
            color: #046380;
        }
    </style>
    <title id="title">Authorization</title>
</head>
<body bgcolor="#EFECCA">
<form id="authorizationForm" method="POST" action="/login" target="_self">
    <h1 align="center">Bank Application</h1><br>
    <table align="center">
        <tr>
            <td align="right">Username:</td>
            <td><input align="left" type="text" id="clientName" name="clientName"/>
                <div id="nameError"></div>
            </td>
        </tr>
        <tr>
            <td align="right">Password:</td>
            <td><input align="left" type="password" id="password" name="password"/></td>
        </tr>
        <tr>
            <td align="right" colspan="2">
                <input type="submit" onclick="return validateForm();"></td>
        </tr>
    </table>
</form>

<h4>Clients:</h4>
    <ol>
        <li>Zuzanna</li>
        <li>Zenon</li>
        <li>Helga</li>
        <li>Zbigniew</li>
        <li>Quentin</li>
        <li>Wacław</li>
    </ol>
</body>
</html>