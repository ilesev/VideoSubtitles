<div style="position: relative; text-align: center;">
    <img src="${pageContext.request.contextPath}/images/logo.jpg" class="center">
    <span class="image-footer">Create subtitles easily!</span>
</div>
<div id='cssmenu'>
    <ul>
        <li class=' ${pageContext.request.requestURI eq '/WEB-INF/jsp/index.jsp' ? 'active' : ''} '><a href='/'>Home</a></li>
        <li class=' ${pageContext.request.requestURI eq '/WEB-INF/jsp/history.jsp' ? 'active' : ''} '><a href='/history'>History</a></li>
        <li style="float: right; margin-right: 5px;"><a href="#" id="logout">Logout</a></li>
    </ul>
</div>
<script
        src="https://code.jquery.com/jquery-3.2.1.min.js"
        integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
        crossorigin="anonymous"></script>
<script type="application/javascript" src="${pageContext.request.contextPath}/js/logout.js"></script>
