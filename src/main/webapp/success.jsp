<%@ page language="java" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>success</title>
</head>

<body>
欢迎你${user.username }
<a href="${pageContext.request.contextPath }/user/logout">退出</a>
</body>
</html>
