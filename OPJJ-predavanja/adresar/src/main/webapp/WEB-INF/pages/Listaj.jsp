<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Kontakti</title>
	</head>

	<body>
		<h1>Lista postojećih kontakata</h1>
		<c:choose>
			<c:when test="${zapisi.isEmpty()}">
				<p>Trenutno nemate evidentiranih kontakata.</p>
			</c:when>
			<c:otherwise>
				<ol>
				<c:forEach var="zapis" items="${zapisi}">
				<li>
				  <c:out value="${zapis.ime}"/> 
				  <c:out value="${zapis.prezime}"/>
				  (<c:out value="${zapis.email}"/>)
				  <a href="edit?id=<c:out value="${zapis.id}"/>">Uredi</a>
				</li>
				</c:forEach>
				</ol>
			</c:otherwise>
		</c:choose>
		
		<p><a href="new">Novi</a></p>
	</body>
</html>