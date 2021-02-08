<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login">
	<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
	<table>
		<tr>
			<td><input type="text" name="loginId" autofocus="autofocus" placeholder="아이디 입력" required="required"/></td>
		</tr>
		<tr>
			<td><input type="password" name="loginPwd" placeholder="비밀번호 입력" required="required"/></td>
		</tr>
		<tr>
			<td><input type="submit" value="로그인"/></td>
		</tr>
	</table>
</form>
</body>
</html>