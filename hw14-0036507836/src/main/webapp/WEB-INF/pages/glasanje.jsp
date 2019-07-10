<%@ page import="hr.fer.zemris.java.p12.dao.DAO.Poll" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title>Glasanje</title>
</head>

<body>
	<h1>${pollTitle}</h1>
	<p>${pollMessage}</p>
	<ol>
		<c:forEach var="option" items="${pollOptions}">
			<li><a href="<%=request.getContextPath()%>/servleti/glasanje-glasaj?id=${option.id }">${option.title }</a></li>
		</c:forEach>
	</ol>
	<a href="index.html">Back to home page</a>
</body>

</html>