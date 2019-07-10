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

  EDIT A BLOG ENTRY:
      <form action="<%=request.getContextPath()%>/servleti/editBlogEntry" method="POST" id="usrform">
      	 <input type="hidden" name="id" value="${entry.id }">
      	 Title: <input type="text" name="title" value="${oldTitle }"> <c:out value="${errors['title'] }"/>
      	<input type="submit" value="Submit"/>
      </form>
      <c:out value="${errors['text'] }"/> <br>
      <textarea rows="10" cols="80" name="text" form="usrform"><c:out value="${oldText }"/></textarea>


  </body>
</html>