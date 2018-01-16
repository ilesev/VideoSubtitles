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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href='https://fonts.googleapis.com/css?family=Roboto:400,700' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index-style.css">
    <title class="title">Video Subtitle Maker</title>
  </head>
  <body>
    <%@include file="navigation.jsp"%>
    <div class="user">
      <header class="user__header">
       <h1>Hello, ${username}</h1>
        <h1>Upload a file</h1>
      </header>

      <form class="form" method="post" action="upload" enctype="multipart/form-data">
        <div class="form__group">
          <input type="file" accept="video/mp4" class="form__input" name="file">
        </div>
          <input class="btn" type="submit" value="Upload">
      </form>
    </div>
  </body>
</html>
