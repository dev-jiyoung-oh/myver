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
<form action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<input type="hidden" name="loginRedirect" value="${loginRedirect}" />
	<table>
		<c:if test="${not empty exceptionMsgName}">
			<tr>
				<td><a style="color:red;">아이디 또는 비밀번호가 올바르지 않습니다.</a></td>
			</tr>
		</c:if>
		<tr>
			<td><input type="text" name="id" autofocus="autofocus" placeholder="아이디 입력" required="required" value="${id}" /></td>
		</tr>
		<tr>
			<td><input type="password" name="pw" placeholder="비밀번호 입력" required="required" value="${pw}"/></td>
		</tr>
		<tr>
			<td><input type="submit" value="로그인"/></td>
		</tr>
	</table>
</form>
</body>
</html>