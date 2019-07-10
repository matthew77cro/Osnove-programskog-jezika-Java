<%@ page import="hr.fer.zemris.java.p12.dao.DAO.PollOption" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<style type="text/css">
		table.rez td {text-align: center;}
	</style>
	<title>Rezultati glasanja</title>
</head>
<body>
	<a href="index.html">Back to home page</a>

	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" cellspacing="0" class="rez">
		<thead><tr><th>Kandidat</th><th>Broj glasova</th></tr></thead>
		<tbody>
			<c:forEach var="option" items="${results}">
				<tr><td>${option.title }</td><td>${option.votesCount }</td></tr>
			</c:forEach>
		</tbody>
	</table>

	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="<%=request.getContextPath()%>/servleti/glasanje-grafika?id=${id }" width="400" height="400" />
	
	<h2>Rezultati u XLS formatu</h2>
	<p>Rezultati u XLS formatu dostupni su <a href="<%=request.getContextPath()%>/servleti/glasanje-xls?id=${id }">ovdje</a></p>
	
	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
		<c:forEach var="winners" items="${winners}">
			<li><a href="${winners.link }">${winners.title }</a></li>
		</c:forEach>
	</ul>
	
	<a href="index.html">Back to home page</a>
</body>

</html>