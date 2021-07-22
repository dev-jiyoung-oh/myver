<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
	<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/css/myver.css" rel="stylesheet">
</head>

<body>

<a href="${pageContext.request.contextPath}/">MYVER</a>

<%-- 로그인중이 아닐 때에만 로그인,회원가입 버튼이 보임  -> taglib ( security/tags ) 때문에 가능 --%>
<sec:authorize access="isAnonymous()">
	<a href="${pageContext.request.contextPath}/login">로그인</a>
	<a href="${pageContext.request.contextPath}/join">회원가입</a>
</sec:authorize>

<%-- 로그인 중일 경우에만 로그아웃 버튼이 보임 --%>
<sec:authorize access="isAuthenticated()">
	<form action="${pageContext.request.contextPath}/logout" method="POST"> 
		<input type="submit" id="logoutBtn" value="Logout" />
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	</form>
	
	${sessionScope.MID} 님
	<sec:authentication property="principal" var="user"/>
	${user.id}
	${user.nick}
	${user.auth}
</sec:authorize>

<%-- 관리자 로그인 시 --%>
<sec:authorize access="hasRole('ADMIN')">
- 관리자 로그인함 -
</sec:authorize>

<%-- 일반 회원 로그인 시 --%>
<sec:authorize access="hasRole('MEMBER')">
- 회원 로그인함 -
</sec:authorize>

</body>
</html>