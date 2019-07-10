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
  
  <h1>List of blog entries by <c:out value="${authorNick}"/></h1>
  <br>
  <ul>
    <c:forEach var="e" items="${entries}">
      <li><a href="${authorNick}/${e.id}"><c:out value="${e.title}"/></a></li>
    </c:forEach>
  </ul>
  
  <br>
  
  <c:if test="${sessionScope['current.user.nick']==authorNick}">
  	<a href="${authorNick}/new">Add a new blog entry</a>
  </c:if>

  </body>
</html>