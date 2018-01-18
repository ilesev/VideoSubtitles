<%@ page import="Utils.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Editor</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/video.css">
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

    <div class="container">
        <% if(request.getSession().getAttribute(Constants.PROPERTY_VIDEO_ADDR) != null) {%>
        <video width="640" height="480" style="padding-bottom: 20px;" crossorigin="anonymous" controls>
            <source src="/files/${video}" type="video/mp4">
            <track src="/files/${subtitle}" kind="subtitles" srclang="en" label="English" default>
        </video>

        <br>

        <label for="subtitle">Subtitles</label>
        <br>
        <textarea rows="12" cols="50" style="width: 640px;" id="subtitle">
            ${subContent}
        </textarea>
    </div>
    <c:remove var="video" scope="session" />
    <c:remove var="subtitle" scope="session" />
    <%}%>

</body>
</html>
