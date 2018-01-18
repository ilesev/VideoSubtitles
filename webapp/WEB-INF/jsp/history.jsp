<%--
  Created by IntelliJ IDEA.
  User: ilesev
  Date: 11.01.18
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index-style.css">
    <title>History</title>
</head>
<body>

<%@include file="navigation.jsp" %>
    <h1>History</h1>

    <ul style="list-style-type: none">
        <c:forEach items="${requestScope.list}" var="item">
            <li>
                <c:choose>
                    <c:when test="${item.fileType == 'Video'}">
                        <img src="${pageContext.request.contextPath}/images/movie.png" alt=""
                             style="width: 32px; display: inline-block;">
                        <a href="/files/${username}/${item.fileName}.mp4" style="vertical-align: super" download="movie.mp4">${item.fileName}</a>
                    </c:when>
                    <c:when test="${item.fileType == 'Subtitle'}">
                        <img src="${pageContext.request.contextPath}/images/subtitle.png" alt=""
                             style="width: 32px; display: inline-block;">
                        <a href="/files/${username}/${item.fileName}.vtt" style="vertical-align: super" download="sub.vtt">${item.fileName}</a>
                    </c:when>
                </c:choose>
            </li>
        </c:forEach>
    </ul>
</body>
</html>
