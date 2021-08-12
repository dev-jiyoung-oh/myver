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
<form action="${pageContext.request.contextPath}/login" method="post">
	<div class="row">
		<div class="col"></div>
		<div class="col">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="hidden" name="loginRedirect" value="<%= request.getParameter("loginRedirect") %>" />
		
			<c:if test="${not empty exceptionMsgName}">
				<a style="color:red;">아이디 또는 비밀번호가 올바르지 않습니다.</a>
			</c:if>
			
			<div class="form-group my-1">
				<div class="input-group">
					<input type="text" name="id" autofocus="autofocus" placeholder="아이디 입력" required="required" value="${id}" class="form-control"/>
				</div>
			</div>
			<div class="form-group my-1">
				<div class="input-group">
					<input type="password" name="pw" placeholder="비밀번호 입력" required="required" value="${pw}" class="form-control"/>
				</div>
			</div>
			<div class="form-group my-1">
				<button type="submit" class="btn btn-success w-100">로그인</button>
			</div>
			
			<ul class="nav justify-content-center">
				<li class="nav-item">
					<a href="${pageContext.request.contextPath}/findId" class="nav-link">아이디 찾기</a>
				</li>
				<li class="nav-item">
					<a href="${pageContext.request.contextPath}/findPw" class="nav-link">비밀번호 찾기</a>
				</li>
				<li class="nav-item">
					<a href="${pageContext.request.contextPath}/join" class="nav-link">회원가입</a>
				</li>
			</ul>
		</div>
		<div class="col"></div>
	</div>
</form>
</body>
</html>