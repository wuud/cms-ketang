<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>登录 | 注册</title>
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
<style>
        /* 滑动控件容器,亮灰色背景 */
        #dragContainer {
            position: relative;
            display: inline-block;
            background: #e8e8e8;
            width: 390px;
            height: 50px;
            border: 2px solid #e8e8e8;
        }
        /* 滑块左边部分,绿色背景 */
        #dragBg {
            position: absolute;
            background-color: blue;
            width: 0px;
            height: 100%;
        }
        /* 滑动验证容器文本 */
        #dragText {
            position: absolute;
            width: 100%;
            height: 100%;
            /* 文字水平居中 */
            text-align: center;
            /* 文字垂直居中,这里不能用百分比,因为百分比是相对原始line-height的,而非div高度 */
            line-height: 50px;
            /* 文本不允许选中 */
            user-select: none;
            -webkit-user-select: none;
        }
        /* 滑块 */
        #dragHandler {
            position: absolute;
            width: 50px;
            height: 100%;
            cursor: move;
        }
        /* 滑块初始背景 */
        .dragHandlerBg {
            background: #fff no-repeat center url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA3hpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNS1jMDIxIDc5LjE1NTc3MiwgMjAxNC8wMS8xMy0xOTo0NDowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo0ZDhlNWY5My05NmI0LTRlNWQtOGFjYi03ZTY4OGYyMTU2ZTYiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6NTEyNTVEMURGMkVFMTFFNEI5NDBCMjQ2M0ExMDQ1OUYiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NTEyNTVEMUNGMkVFMTFFNEI5NDBCMjQ2M0ExMDQ1OUYiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTQgKE1hY2ludG9zaCkiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo2MTc5NzNmZS02OTQxLTQyOTYtYTIwNi02NDI2YTNkOWU5YmUiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6NGQ4ZTVmOTMtOTZiNC00ZTVkLThhY2ItN2U2ODhmMjE1NmU2Ii8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+YiRG4AAAALFJREFUeNpi/P//PwMlgImBQkA9A+bOnfsIiBOxKcInh+yCaCDuByoswaIOpxwjciACFegBqZ1AvBSIS5OTk/8TkmNEjwWgQiUgtQuIjwAxUF3yX3xyGIEIFLwHpKyAWB+I1xGSwxULIGf9A7mQkBwTlhBXAFLHgPgqEAcTkmNCU6AL9d8WII4HOvk3ITkWJAXWUMlOoGQHmsE45ViQ2KuBuASoYC4Wf+OUYxz6mQkgwAAN9mIrUReCXgAAAABJRU5ErkJggg==");
        }
        /* 验证成功时的滑块背景 */
        .dragHandlerOkBg {
            background: #fff no-repeat center url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA3hpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNS1jMDIxIDc5LjE1NTc3MiwgMjAxNC8wMS8xMy0xOTo0NDowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo0ZDhlNWY5My05NmI0LTRlNWQtOGFjYi03ZTY4OGYyMTU2ZTYiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6NDlBRDI3NjVGMkQ2MTFFNEI5NDBCMjQ2M0ExMDQ1OUYiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NDlBRDI3NjRGMkQ2MTFFNEI5NDBCMjQ2M0ExMDQ1OUYiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTQgKE1hY2ludG9zaCkiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDphNWEzMWNhMC1hYmViLTQxNWEtYTEwZS04Y2U5NzRlN2Q4YTEiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6NGQ4ZTVmOTMtOTZiNC00ZTVkLThhY2ItN2U2ODhmMjE1NmU2Ii8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+k+sHwwAAASZJREFUeNpi/P//PwMyKD8uZw+kUoDYEYgloMIvgHg/EM/ptHx0EFk9I8wAoEZ+IDUPiIMY8IN1QJwENOgj3ACo5gNAbMBAHLgAxA4gQ5igAnNJ0MwAVTsX7IKyY7L2UNuJAf+AmAmJ78AEDTBiwGYg5gbifCSxFCZoaBMCy4A4GOjnH0D6DpK4IxNSVIHAfSDOAeLraJrjgJp/AwPbHMhejiQnwYRmUzNQ4VQgDQqXK0ia/0I17wJiPmQNTNBEAgMlQIWiQA2vgWw7QppBekGxsAjIiEUSBNnsBDWEAY9mEFgMMgBk00E0iZtA7AHEctDQ58MRuA6wlLgGFMoMpIG1QFeGwAIxGZo8GUhIysmwQGSAZgwHaEZhICIzOaBkJkqyM0CAAQDGx279Jf50AAAAAABJRU5ErkJggg==");
        }
    </style>
<script type="text/javascript" src="..qhkt/"></script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header.jsp"%>
	<!-- 登录表单 -->
	<div id="loginhtml" class="container">
		<div class="row">
			<div class="col-md-7 col-sm-6 col-xs-5"></div>
			<div class="col-md-10  col-sm-12 col-xs-14 login-col">
				<ul class="nav nav-tabs">
					<li class="col-xs-12 active"><a href="#loginform"
						data-toggle="tab">登录账号</a></li>
					<li class="col-xs-12"><a data-toggle="tab" href="#regform">注册账号</a></li>
				</ul>
				<div class="tab-content">
					<div id="loginform" class="tab-pane fade in active">
						<form id="forcss"
							action="${pageContext.request.contextPath}/doLogin" method="post">
							<div class="form-group">
								<label>账号:</label>
								<c:if test="${error!=null }">
									<span style="color: red; float: right; font-size: 20px">${error}</span>
								</c:if>
								<input type="text" name="number" class="form-control"
									placeholder="邮箱/手机">
							</div>
							<div class="form-group">
								<label>密码:</label> <input type="password" name="password"
									class="form-control" placeholder="密码">
							</div>
							<div id="dragContainer">
        						<div id="dragBg"></div>
        						<div id="dragText"></div>
       							<div id="dragHandler" class="dragHandlerBg"></div>
    						</div>
							<div class="checkbox">
								<label><input type="checkbox" name="rememberme">10天内自动登录</label>
							</div>
							<div class="form-group">
								<input type="submit" class="form-control btn btn-primary"
									value="登录">
							</div>
							<div class="form-group">
								<p>
									<a href="#">找回密码</a> | 还没有账号? <a>先注册一个</a>
								</p>
							</div>
						</form>
					</div>
					<div id="regform" class="tab-pane fade">
						<form action="${pageContext.request.contextPath}/reg/"
							method="post">
							<div class="form-group">
								<label>手机:</label> <input type="text" name="phone"
									class="form-control" placeholder="常用的手机号码">
							</div>
							<div class="form-group">
								<label>用户名:</label> <input type="text" name="username"
									class="form-control" placeholder="用户名">
							</div>
							<div class="form-group">
								<label>密码:</label> <input type="password" name="password"
									class="form-control" placeholder="密码">
							</div>
							<div class="form-group">
								<label>邮箱:</label> <input type="password" name="email"
									class="form-control" placeholder="邮箱">
							</div>
							
							<div class="form-group"
								style="margin-top: 30px; margin-bottom: 20px;">
								<input type="submit" class="form-control btn btn-primary"
									value="登录">
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="col-md-7  col-sm-6 col-xs-5"></div>
		</div>
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
<script>
        window.onload = function() {
            var dragContainer = document.getElementById("dragContainer");
            var dragBg = document.getElementById("dragBg");
            var dragText = document.getElementById("dragText");
            var dragHandler = document.getElementById("dragHandler");
 
            //滑块最大偏移量
            
            var maxHandlerOffset = 340;
            console.log(maxHandlerOffset);
            //是否验证成功的标记
            var isVertifySucc = false;
            initDrag();
 
            function initDrag() {
                dragText.textContent = "拖动滑块验证";
                dragHandler.addEventListener("mousedown", onDragHandlerMouseDown);
 
                dragHandler.addEventListener("touchstart", onDragHandlerMouseDown);
            }
 
            function onDragHandlerMouseDown(event) {
                document.addEventListener("mousemove", onDragHandlerMouseMove);
                document.addEventListener("mouseup", onDragHandlerMouseUp);
 
                document.addEventListener("touchmove", onDragHandlerMouseMove);
                document.addEventListener("touchend", onDragHandlerMouseUp);
            }
 
            function onDragHandlerMouseMove(event) {
                /*
                html元素不存在width属性,只有clientWidth
                offsetX是相对当前元素的,clientX和pageX是相对其父元素的
                touches：表示当前跟踪的触摸操作的touch对象的数组。
                targetTouches：特定于事件目标的Touch对象的数组。
            　　changedTouches：表示自上次触摸以来发生了什么改变的Touch对象的数组。
                */
                
                
                var left = (event.clientX || event.changedTouches[0].clientX) - dragHandler.clientWidth / 2-500;
                console.log(left);
                if(left < 0) {
                    left = 0;
                } else if(left > maxHandlerOffset) {
                    left = maxHandlerOffset;
                    verifySucc();
                }
                dragHandler.style.left = left + "px";
                dragBg.style.width = dragHandler.style.left;
            }
            function onDragHandlerMouseUp(event) {
                document.removeEventListener("mousemove", onDragHandlerMouseMove);
                document.removeEventListener("mouseup", onDragHandlerMouseUp);
 
                document.removeEventListener("touchmove", onDragHandlerMouseMove);
                document.removeEventListener("touchend", onDragHandlerMouseUp);
 
                dragHandler.style.left = 0;
                dragBg.style.width = 0;
            }
 
            //验证成功
            function verifySucc() {
                isVertifySucc = true;
                dragText.textContent = "验证通过";
                dragText.style.color = "white";
                dragHandler.setAttribute("class", "dragHandlerOkBg");
 
                dragHandler.removeEventListener("mousedown", onDragHandlerMouseDown);
                document.removeEventListener("mousemove", onDragHandlerMouseMove);
                document.removeEventListener("mouseup", onDragHandlerMouseUp);
 
                dragHandler.removeEventListener("touchstart", onDragHandlerMouseDown);
                document.removeEventListener("touchmove", onDragHandlerMouseMove);
                document.removeEventListener("touchend", onDragHandlerMouseUp);
            };
        }
    </script>
</html>