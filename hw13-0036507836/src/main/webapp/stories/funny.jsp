<%@page import="java.util.Random"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%! 
	private String randomColor() {
		String[] colors = new String[] {"#000000","#FF0000","#009933","#0000FF","#FF3399","#FF00FF"};
		Random rnd = new Random(System.currentTimeMillis());
		int color = rnd.nextInt(colors.length);
		return colors[color];
	}
%>
<!DOCTYPE html>
<html>

<head>
	<title>Index</title>
</head>

<body bgcolor="<% out.print(session.getAttribute("pickedBgCol")); %>" text="<% out.print(randomColor()); %>">
	<h1> Funny short stories page </h1> <br>

	<h2>Double positive</h2>
	
	<p>A linguistics professor was lecturing his class one day. <br> <br>

	'In English', he said, 'A double negative forms a positive. In some languages, though, <br>
	such as Russian, a double negative is still a negative. However, there is no language wherein <br>
	a double positive can form a negative.' <br> <br>

	A loud voice from the back of the room piped up, 'Yeah, right.' </p>
	
	<h2>Zebra crossing</h2>
	
	<p>A policeman spotted a jay walker and decided to challenge him, <br>
	'Why are you trying to cross here when there's a zebra crossing only 20 metres away?' <br> <br>

	'Well,' replied the jay walker, 'I hope it's having better luck than me.'</p>
	
	<a href="../index.jsp">Back to home page</a>
</body>

</html>