<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>项目代码统计</title>
<link rel="icon"
	href="${pageContext.request.contextPath}/static/images/logo.png"
	type="image/x-icon" />

<link
	href="${pageContext.request.contextPath}/static/lib/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/css/main.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/css/login.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/font/iconfont.css">

<!--[if lt IE 9]>
    <script src="https://cdn.jsdelivr.net/npm/html5shiv@3.7.3/dist/html5shiv.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/respond.js@1.4.2/dest/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header.jsp"%>
	<div class="container">
	<table class="table table-hover">
        <thead>
        <tr>
            <th width="20%" style="text-align: center;font-size:22px">语言</th>
            <th width="20%" style="text-align: center;font-size:22px">文件数</th>  
            <th width="20%" style="text-align: center;font-size:22px">空白行</th>
            <th width="20%" style="text-align: center;font-size:22px">注释行</th>
            <th width="20%" style="text-align: center;font-size:22px">代码行</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${VOList }" var="vo" varStatus="status">
            <tr height="60px" style="line-height:60px;font-size:18px">
                <th scope="row" style="text-align: center;padding-top:20px;">${vo.language}</th>
                <td style="text-align: center;padding-top:20px;">${vo.files}</td>
                <td style="text-align: center;padding-top:20px;">${vo.blank}</td>
                <td style="text-align: center;padding-top:20px;">${vo.comment}</td>
                <td style="text-align: center;padding-top:20px;">${vo.code}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	<script src="${pageContext.request.contextPath}/static/js/checkCode.js"></script>
	<script
		src="${pageContext.request.contextPath}/static/lib/jquery/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/static/lib/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/static/js/index.js"></script>
</body>
</html>