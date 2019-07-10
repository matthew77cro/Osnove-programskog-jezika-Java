<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="hr.fer.zemris.java.p12.model.Unos"%>
<%@page import="java.util.List"%>
<%
  List<Unos> unosi = (List<Unos>)request.getAttribute("unosi");
%>
<html>
  <body>

  <b>Pronađeni su sljedeći unosi:</b><br>

  <% if(unosi.isEmpty()) { %>
    Nema unosa.
  <% } else { %>
    <ul>
    <% for(Unos u : unosi) { %>
    <li>[ID=<%= u.getId() %>] <%= u.getTitle() %> </li>  
    <% } %>  
    </ul>
  <% } %>

  </body>
</html>