<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body>
  
  <header>
	<table border="1"> <tr> <td>
		<c:if test="${sessionScope['current.user.id']==null}">
		Not logged in! Please, do so on the main page!
		</c:if>
	    <c:if test="${sessionScope['current.user.id']!=null}">
	        <p>Nick: ${sessionScope["current.user.nick"]} <br>
	            Email: ${sessionScope["current.user.email"]} <br>
	            First name: ${sessionScope["current.user.fn"]} <br>
	            Last name: ${sessionScope["current.user.ln"]}</p>
	        <h4><a href="<% out.write(request.getContextPath());%>/servleti/logout">Logout</a></h4>
	    </c:if>
	    <h4><a href="<% out.write(request.getContextPath());%>/index.jsp">HOME PAGE </a></h4>
	</td> </tr> </table>
	</header> <br>

  	REGISTER:
      <form action="register" method="POST">
      	First name: <input type="text" name="firstName" value="${oldFirstName}"> <c:out value="${errors['firstName'] }"/> <br>
      	Last name: <input type="text" name="lastName" value="${oldLastName}"> <c:out value="${errors['lastName'] }"/> <br>
      	Email: <input type="text" name="email" value="${oldEmail}"> <c:out value="${errors['email'] }"/> <br>
      	Nick: <input type="text" name="nick" value="${oldNick}"> <c:out value="${errors['nick'] }"/> <br>
      	Password:  <input type="password" name="password"> <c:out value="${errors['password'] }"/> <br>
      	<input type="submit" value="Register"/>
      </form>

  </body>
</html>