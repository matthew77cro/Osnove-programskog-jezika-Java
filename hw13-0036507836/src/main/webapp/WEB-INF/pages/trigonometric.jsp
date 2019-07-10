<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title>Trigonometric</title>
</head>

<body bgcolor="<% out.print(session.getAttribute("pickedBgCol")); %>">
	<a href="index.jsp">Back to home page</a>
	<table border="1">	
		<tr><td>x</td><td>sin(x)</td><td>cos(x)</td></tr>
		<c:forEach var="row" items="${table}">
			<tr><td>${row.key}</td><td>${row.value.a}</td><td>${row.value.b}</td></tr>
		</c:forEach>
	</table>
	<a href="index.jsp">Back to home page</a>
</body>

</html>