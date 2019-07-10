<%@ page import="hr.fer.zemris.java.hw13.Voting.VoteObject" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<style type="text/css">
		table.rez td {text-align: center;}
	</style>
	<title>Rezultati glasanja</title>
</head>
<body bgcolor="<% out.print(session.getAttribute("pickedBgCol")); %>">
	<a href="index.jsp">Back to home page</a>

	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" cellspacing="0" class="rez">
		<thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
		<tbody>
			<c:forEach var="object" items="${results}">
				<tr><td>${object.name }</td><td>${object.voteCount }</td></tr>
			</c:forEach>
		</tbody>
	</table>

	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />
	
	<h2>Rezultati u XLS formatu</h2>
	<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>
	
	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
		<c:forEach var="winners" items="${winners}">
			<li><a href="${winners.url }">${winners.name }</a></li>
		</c:forEach>
	</ul>
	
	<a href="index.jsp">Back to home page</a>
</body>

</html>