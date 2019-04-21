<!DOCTYPE html>
<html>
<head>
<title>个人主页</title>
<link rel="icon" href="${pageContext.request.contextPath}/static/images/logo.png" type="image/x-icon" />

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
</head>1
<body>
	<!-- 导航条 -->
    <nav class="navbar navbar-default navbar-static-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#qh_navbar">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a href="#" class="navbar-brand"><span class="iconfont icon-chuanxiang "></span>启航课堂</a>
        </div>
        <div id="qh_navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li><a href="#">首页</a></li>
            <li><a href="#">全部课程</a></li>
            <li><a href="#">问题讨论</a></li>
            <li><a href="#">学习路线</a></li>
            <li> <a href="#">资料下载</a></li>
            <li><a href="#">在线提问</a></li>
          </ul>
          <div class="navbar-form navbar-left">
            <form action="" accept="#" method="get">
              <div class="input-group">
                <input type="text" name="search_str" class="form-control" placeholder="课程名称">
                <span class="input-group-btn">
                  <button type="button" class="btn btn-primary">
                    <span class="glyphicon glyphicon-search"></span>
                  </button>
                </span>
              </div>
            </form>
          </div>
          <ul class="nav navbar-nav navbar-right" id="reglog">
            <li><a href="#"><span class="glyphicon glyphicon-user"></span> 登录</a></li>
            <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> 注册</a></li>
          </ul>
        </div>
      </div>
    </nav>

	<!-- 章节列表 -->
	<div class="container">
		<h3>我参加的课程</h3>
		<table class="table table-hover">
			<thead>
				<tr>
					<th width="20%" style="text-align: center">#</th>
					<th width="60%" style="text-align: center">课程名称</th>
					<th width="20%" style="text-align: center">课程讲师</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${joinCourseList }" var="map" varStatus="status">
					<tr height="60px" style="line-height:60px;font-size:18px">
						<th scope="row" style="text-align: center;padding-top:20px;">${status.count}</th>
						<td style="text-align: center;padding-top:20px;"><a
							href="${pageContext.request.contextPath}/course/${map['course'].id}">
								${map['course'].cname }</a></td>
						<td style="text-align: center;padding-top:20px;">${map['teacher']}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="container" style="margin-top:30px">
		<h3>我发布的课程</h3>
		<table class="table table-hover">
			<thead>
				<tr>
					<th width="20%" style="text-align: center">#</th>
					<th width="60%" style="text-align: center">课程名称</th>
					<th width="20%" style="text-align: center">参加人数</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${myCourseList }" var="map" varStatus="status">
					<tr height="60px" style="line-height:60px;font-size:18px">
						<th scope="row" style="text-align: center;padding-top:20px;">${status.count}</th>
						<td style="text-align: center;padding-top:20px;"><a
							href="${pageContext.request.contextPath}/course/${map['course'].id}">
								${map['course'].cname }</a></td>
						<td style="text-align: center;padding-top:20px;">${map['count']}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</body>
</html>