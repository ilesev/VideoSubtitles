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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Video Subtitle Maker</title>
  </head>
  <body>
  <%@include file="navigation.jsp"%>

  <h1>Hello, ${username}</h1>

  <form method="post" action="logout">
    <input type="submit" value="Logout">
  </form>
  </body>
</html>
