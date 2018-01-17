<%@ page import="Utils.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Editor</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@include file="navigation.jsp"%>

    <h1>Editor</h1>
    <label for="videoSelector">Select a video</label>
        <select name="videoSelector" id="videoSelector" form="videoLoader">
            <option value="">None</option>
            <c:forEach items="${requestScope.videos}" var="item">
                <option value="${item}">${item}</option>
            </c:forEach>
        </select>
    <form action="editorLoader" method="post" id="videoLoader">
        <input type="submit" value="Load Video">
    </form>
    <% if(request.getSession().getAttribute(Constants.PROPERTY_VIDEO_ADDR) != null) {%>
    <video width="640" height="480" crossorigin="anonymous" controls>
        <source src="/files/${video}" type="video/mp4">
        <track src="/files/${subtitle}" kind="captions" srclang="en" label="English" default>
    </video>
    <c:remove var="video" scope="session" />
    <c:remove var="subtitle" scope="session" />
    <%}%>
</body>
</html>
