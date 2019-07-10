<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>

<!DOCTYPE html>
<html>
   <head>
      <title>Error</title>
   </head>
   <body bgcolor="<% out.print(session.getAttribute("pickedBgCol")); %>">
     <p> <% out.print(request.getAttribute("error")); %> </p>
     <a href="index.jsp">Back to home page</a>
   </body>
</html>