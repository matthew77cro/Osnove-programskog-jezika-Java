<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title>Glasanje</title>
</head>

<body bgcolor="<% out.print(session.getAttribute("pickedBgCol")); %>">
	<h1>Glasanje za omiljeni bend:</h1>
	<p>Od sljedećih bendova, koji Vam je najdraži? Kliknite na link kako biste glasali!</p>
	<ol>
		<c:forEach var="object" items="${voteObjects}">
			<li><a href="glasanje-glasaj?id=${object.key }">${object.value }</a></li>
		</c:forEach>
	</ol>
	<a href="index.jsp">Back to home page</a>
</body>

</html>