<%@ page import="com.luxoft.bankapp.servlets.*" %>
<%@ page import="java.lang.String" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <noscript>
        Sorry...JavaScript is needed to go ahead.
    </noscript>
    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
    <style>
        h1 {
            color: #046380;
            alignment: center;
        }
        h2 {
            alignment: center;
            color: #046380;
        }
        tr {
            color: #046380;
        }
    </style>
    <title>Sessions</title>
</head>
<body bgcolor="#EFECCA">
<h1 align="center">Sessions</h1>
<br>
<h2 align="center">Number of sessions is: <%=String.valueOf(SessionListener.getClientsConnected()-2)%></h2>
</body>
</html>