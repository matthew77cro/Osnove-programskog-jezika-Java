<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<!DOCTYPE html>
<html>

<head>
	<title>Colors</title>
</head>

<body bgcolor="<% out.print(session.getAttribute("pickedBgCol")); %>">
	<a href="setcolor?color=white">WHITE</a> <br>
	<a href="setcolor?color=red">RED</a> <br>
	<a href="setcolor?color=green">GREEN</a> <br>
	<a href="setcolor?color=cyan">CYAN</a> <br> <br>
	<a href="index.jsp">Back to home page</a>
</body>

</html>