<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Primjer bloka u kojem možemo deklarirati funkcije -->
<%! 
private void zapisiDatum(javax.servlet.jsp.JspWriter out, Date now) throws java.io.IOException {
	out.print(now);
}
%>
<html>
   <body>
     <p>Pozdrav! Sada je:
      <% 
         Date now = new Date();
         out.println(now);
      %>
	  a to možemo napisati i ovako: <%= now %> i ovako <% zapisiDatum(out, now); %>.</p>
	  <p>
	  <% if (Calendar.getInstance().get(Calendar.AM_PM) == Calendar.AM) {%>
        Good Morning
      <% } else { %>
        Good Afternoon
      <% } %>
	  </p>
	  <p>Ispis preko taga: <c:out value="abc" />, ispis preko EL-izraza: ${"abc"}.</p>
	  <p><c:forEach var="brojac" begin="1" end="5">${brojac},</c:forEach></p>
   </body>
</html>