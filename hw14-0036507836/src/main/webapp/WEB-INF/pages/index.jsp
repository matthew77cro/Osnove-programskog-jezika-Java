<%@ page import="hr.fer.zemris.java.p12.dao.DAO.Poll" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title>Glasanje</title>
</head>

<body>
	<ol>
		<c:forEach var="poll" items="${polls}">
			<li><a href="<%=request.getContextPath()%>/servleti/glasanje?pollID=${poll.id }">${poll.title }</a></li>
		</c:forEach>
	</ol>
</body>

</html>