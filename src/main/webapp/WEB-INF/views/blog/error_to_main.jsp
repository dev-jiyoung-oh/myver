<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
오류가 발생했습니다.
<c:if test="${!empty BLOG.blog_id}">
	<a href="${pageContext.request.contextPath}/blog/${BLOG.blog_id}">${BLOG.blog_title} 방문하기</a>
</c:if>
<a href="${pageContext.request.contextPath}/blog/home">블로그 홈</a>
</body>
</html>