<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>

<!DOCTYPE html>
<html>
   <head>
      <title>Error</title>
   </head>
   <body>
     <p> <% out.print(request.getAttribute("error")); %> </p>
     <a href="index.html">Back to home page</a>
   </body>
</html>