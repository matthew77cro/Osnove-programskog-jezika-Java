<%@page import="hr.fer.zemris.java.hw13.Util"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>

<!DOCTYPE html>
<html>

	<head>
		<title>AppInfo</title>
	</head>
	
	<body bgcolor="<% out.print(session.getAttribute("pickedBgCol")); %>">
		<p>This server has been up for: <% out.print(Util.intervalFormatter((Long)application.getAttribute("startup-time"), System.currentTimeMillis())); %>.</p>
		
		<a href="index.jsp">Back to home page</a>
	</body>

</html>