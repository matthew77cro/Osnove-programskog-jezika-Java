<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ol>
<c:forEach var="u" items="${userList}">
<li>${u.lastName}, ${u.firstName}</li>
</c:forEach>
</ol>

<ol>
<c:forEach var="e" items="${primjerci}">
<li>${e.key} ==&gt; ${e.value.lastName}, ${e.value.firstName}</li>
</c:forEach>
</ol>

Tko je Odlikaš? ${primjerci["Odlikaš"].lastName}