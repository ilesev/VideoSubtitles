<%--
  Created by IntelliJ IDEA.
  User: ilesev
  Date: 5.01.18
  Time: 9:40
  To change this template use File | Settings | File Templates.
--%>

<%
    boolean hasError = request.getParameter("errorMessage") != null;
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Video Subtitle Maker</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href='https://fonts.googleapis.com/css?family=Roboto:400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login-style.css">
</head>

<body>
<div class="user">
    <header class="user__header">
        <img src="${pageContext.request.contextPath}/images/logo.png" alt="" style="width: 70px; height: 50px;"/>
        <h1 class="user__title">Sign in to start creating!</h1>
    </header>

    <form class="form" action="login" method="post">
        <div class="form__group">
            <input type="text" placeholder="Username" class="form__input" id="signInUsername" name="username"/>
        </div>

        <div class="form__group">
            <input type="password" placeholder="Password" class="form__input" id="signInPassword" name="password"/>
        </div>

        <input class="btn" type="submit" id="signIn" value="Sign in"/>
    </form>

    <header class="user__header">
        <h1 class="user__title" style=" margin-top: 20px; margin-bottom: -25px; <%= hasError ? "color: red" : ""%>">
            <%= hasError? request.getParameter("errorMessage") : "Register to get full access!"%>
        </h1>
    </header>

    <form class="form" action="register" method="post">
        <div class="form__group">
            <input type="text" placeholder="Username" class="form__input" name="username"/>
        </div>

        <div class="form__group">
            <input type="password" placeholder="Password" class="form__input" name="password"/>
        </div>

        <div class="form__group">
            <input type="password" placeholder="Confirm Password" class="form__input" name="confirm"/>
        </div>

        <input class="btn" type="submit" id="register" value="Register"/>
    </form>

</div>
<script type="application/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
</body>
</html>
