<%--
  Created by IntelliJ IDEA.
  User: Iliyan
  Date: 12/24/2017
  Time: 12:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>

  <%
    if (request.getSession().getAttribute("username") == null) {
        response.sendRedirect("/login");
    }
  %>

  <h1>Hello, ${username}</h1>

  $END$
  </body>
</html>
