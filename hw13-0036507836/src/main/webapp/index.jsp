<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<!DOCTYPE html>
<html>

<head>
	<title>Index</title>
</head>

<body bgcolor="<% out.print(session.getAttribute("pickedBgCol")); %>">
	<a href="colors.jsp">Background color chooser</a> <br>
	<a href="trigonometric?a=0&b=90">Trigonometric functions</a> <br> <br>
	<form action="trigonometric" method="GET">
 		Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
 		Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
 	<input type="submit" value="Tabeliraj"><input type="reset" value="Reset"> <br> <br>
 	<a href="stories/funny.jsp">Funny short stories</a> <br>
 	<a href="report.jsp">OS usage</a> <br>
 	<a href="powers?a=1&b=100&n=3">Powers</a> <br>
 	<a href="appinfo.jsp">App info</a> <br>
 	<a href="glasanje">Glasanje</a> <br>
</form>
</body>

</html>