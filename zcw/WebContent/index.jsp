<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>首页</title>
</head>
<body>
	<form action="/ShoppingCart/MyServlet" method=get>
            <input type="text"  name="book" value="javase"  size="40" maxlength="200" readonly>
            <input type="submit" name="add" value="加入购物车" height="2">
        </form>

        <form action="<%=request.getContextPath() %>/MyServlet" method="get">
            <input type="text"  name="book" value="javaweb"  size="40" maxlength="200" readonly>
            <input type="submit" name="add" value="加入购物车" height="2">
        </form>

</body>
</html>