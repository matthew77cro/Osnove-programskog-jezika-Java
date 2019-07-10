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
	
  <c:choose>
    <c:when test="${sessionScope['current.user.id']==null}">
      LOGIN:
      <form action="main" method="POST">
      	Nick: <input type="text" name="nick" value="${oldNick}"> <c:out value="${errors['nick'] }"/> <br>
      	Password:  <input type="password" name="password"> <c:out value="${errors['password'] }"/> <br>
      	<input type="submit" value="Login"/>
      	<c:out value="${errors['login'] }"/>
      </form>
      <a href="register">Register</a>
    </c:when>
    <c:otherwise>
      <h1>Welcome, <c:out value=" ${sessionScope['current.user.nick']}"></c:out>!</h1>
    </c:otherwise>
  </c:choose>
  
  <br> <br>
  List of registered authors:
  <br>
  <ul>
    <c:forEach var="e" items="${authors}">
      <li><a href="author/${e.nick}"><c:out value="${e.nick}"/></a></li>
    </c:forEach>
  </ul>

  </body>
</html>