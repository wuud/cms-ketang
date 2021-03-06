<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>课程名称</title>
    <link rel="icon" href="${pageContext.request.contextPath}/static/images/logo.png" type="image/x-icon"/>

    <link
            href="${pageContext.request.contextPath}/static/lib/bootstrap/css/bootstrap.min.css"
            rel="stylesheet">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/css/main.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/css/index.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/static/font/iconfont.css">

    <!--[if lt IE 9]>
    <script src="https://cdn.jsdelivr.net/npm/html5shiv@3.7.3/dist/html5shiv.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/respond.js@1.4.2/dest/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<!-- 章节列表 -->
<div class="container">
    <h3>课程章节列表</h3>
    <table class="table table-hover">
        <thead>
        <tr>
            <th width="20%" style="text-align: center">#</th>
            <th width="60%" style="text-align: center">视频名称</th>
            <th width="20%" style="text-align: center">视频大小</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${videoList }" var="video" varStatus="status">
            <tr height="60px" style="line-height:60px;font-size:18px">
                <th scope="row" style="text-align: center;padding-top:20px;">${status.count}</th>
                <td style="text-align: center;padding-top:20px;">
                <a href="${pageContext.request.contextPath}/course/video/${courseId}_${video.id}">
                        ${video.vname }</a></td>
                <td style="text-align: center;padding-top:20px;">${video.videoSize}M</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</body>
</html>