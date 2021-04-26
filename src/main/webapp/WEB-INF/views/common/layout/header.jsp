<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
	<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
</head>

<body>

<%-- 로그인중이 아닐 때에만 로그인,회원가입 버튼이 보임  -> taglib ( security/tags ) 때문에 가능 --%>
<sec:authorize access="isAnonymous()">
	<a href="${pageContext.request.contextPath}/login">로그인</a>
	<a href="${pageContext.request.contextPath}/join">회원가입</a>
</sec:authorize>

<%-- 로그인 중일 경우에만 로그아웃 버튼이 보임 --%>
<sec:authorize access="isAuthenticated()">
	<form action="${pageContext.request.contextPath}/logout" method="POST"> 
			<input id="logoutBtn" type="submit" value="Logout" />
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	</form>
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