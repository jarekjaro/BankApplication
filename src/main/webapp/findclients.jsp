<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <noscript>
        Sorry...JavaScript is needed to go ahead.
    </noscript>
    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
    <title>Find Clients</title>
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
</head>
<body bgcolor="#EFECCA">
<h1 align="center">Find Clients</h1>
<br>
<h3 align="center">Search client form:</h3>
<table align="center">
    <form id="clientSearchForm" name="clientSearchForm" action="/findclients" method="post">
        <tr>
            <td>City:<input type="text" id="inputCity" name="inputCity"></td>
            <td>Name:<input type="text" id="inputaName" name="inputName"></td>
            <td><input type="submit" id="searchSubmit" name="searchSubmit" value="Search"></td>
        </tr>
    </form>
</table>
<br>
<table align="center">
    <tr>
        <td><b>City</b></td>
        <td><b>Name</b></td>
        <td><b>Balance</b></td>
    </tr>
    <c:forEach var="client" items="${clients}">
        <tr>
            <td></td>
            <td><c:out value="${client.name}"/></td>
            <td><%=session.getAttribute("balance")%></td>
            <td></td>
        </tr>
        <%--City: <c:out value="${client.city}"/>--%>
        <%--Balance: <c:out value="${client}"/><br>--%>
    </c:forEach>
</table>
</body>
</html>