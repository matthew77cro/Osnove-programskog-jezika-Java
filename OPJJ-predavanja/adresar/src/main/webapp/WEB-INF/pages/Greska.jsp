<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Pogreška</title>
	</head>

	<body>
		<h1>Dogodila se pogreška</h1>
		<p><c:out value="${poruka}"/></p>

		<p><a href="listaj">Povratak na početnu stranicu</a></p>
	</body>
</html>