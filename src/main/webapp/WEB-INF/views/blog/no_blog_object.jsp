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
삭제되었거나 존재하지 않는 게시물입니다.
<c:if test="${!empty BLOG.member_id}">
	<a href="${pageContext.request.contextPath}/blog/${BLOG.member_id}">${BLOG.blog_title} 방문하기</a>
</c:if>
<a href="${pageContext.request.contextPath}/blog/home">블로그 홈</a>
</body>
</html>