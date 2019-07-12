<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title>Grafika</title>
</head>

<body>
	<ul>
		<c:forEach var="slika" items="${slike}">
			<li><a href="<%=request.getContextPath()%>/prikaz?file=${slika }">${slika }</a></li>
		</c:forEach>
	</ul>
	
	<br/>
	NEW IMAGE:
      <form action="<%=request.getContextPath()%>/main" method="POST" id="form">
      	 File name: <input type="text" name="fileName" value="">  	
      	<input type="submit" value="Submit"/>
      </form>
      
      <textarea rows="10" cols="80" name="fileContent" form="form"></textarea>
</body>

</html>