<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<!DOCTYPE html>
<html>

<head>
	<title>Report</title>
</head>

<body bgcolor="<% out.print(session.getAttribute("pickedBgCol")); %>">
	<h1> OS usage </h1>
	<p> Here are the results of OS usage in survey that we completed. </p>
	
	<img src = "reportImage"> <br>
	
	<a href="index.jsp">Back to home page</a>
</body>

</html>