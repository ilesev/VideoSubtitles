
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Editor</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%@include file="navigation.jsp"%>

<h1>Editor</h1>

<video width="640" height="480" crossorigin="anonymous" preload="none" controls>
    <source src="/files/${video}" type="video/mp4">
    <track src="/files/${subtitle}" kind="captions" srclang="en" label="English" default>
</video>
</body>
</html>
