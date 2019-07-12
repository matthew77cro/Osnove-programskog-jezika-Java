<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>

<!DOCTYPE html>
<html>
   <head>
      <title>Error</title>
   </head>
   <body>
     
	Ime slike : ${name } <br/>
	Broj linija : ${line } <br/>
	Broj kruznica : ${circle } <br/>
	Broj krugova : ${fcircle } <br/>
	Broj trokuta : ${ftriangle } <br/>
	
	<br/>
	<img alt="Slika" src="<%=request.getContextPath()%>/pic?file=${name }">
	<br/>
     <a href="main">Back to home page</a>
   </body>
</html>