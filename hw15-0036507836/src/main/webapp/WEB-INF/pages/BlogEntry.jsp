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
  
  <h1><c:out value="${entry.title}"/></h1>
      <p><c:out value="${entry.text}"/></p>
       <c:out value="by ${entry.creator.nick}"/>
       
       <c:if test="${sessionScope['current.user.nick']==entry.creator.nick}">
  			<br><a href="edit?eid=${entry.id}">Edit this blog entry</a>
 	   </c:if>
       
      <c:if test="${!entry.comments.isEmpty()}">
      <h2>Comments</h2>
      <ul>
      <c:forEach var="e" items="${entry.comments}">
        <li><div style="font-weight: bold">[Korisnik=<c:out value="${e.usersEMail}"/>] <c:out value="${e.postedOn}"/></div><div style="padding-left: 10px;"><c:out value="${e.message}"/></div></li>
      </c:forEach>
      </ul>
    </c:if>
    
    <br>
    ENTER A NEW COMMENT:
      <form action="<%=request.getContextPath()%>/servleti/newBlogEntryComment" method="POST" id="usrform">
      	  <input type="hidden" name="entryId" value="${entry.id}">
      	 Email: <input type="text" name="email" value="${oldEmail}"> <c:out value="${errors['email'] }"/>    	
      	<input type="submit" value="Submit"/>
      </form>
      
      <c:out value="${errors['message'] }"/> <br>
      <textarea rows="10" cols="80" name="message" form="usrform"><c:out value="${oldMessage }"></c:out></textarea>

  </body>
</html>