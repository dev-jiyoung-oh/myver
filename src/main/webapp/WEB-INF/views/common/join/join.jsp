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
<form action="${pageContext.request.contextPath}/join" method="post">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<table>
		<tr>
			<td colspan="2">아이디</td>
		</tr>
		<tr>
			<td>
				<input type="text" name="id">
			</td>
			<td>
				<input type="button" value="확인"/>
			</td>
		</tr>
		<tr>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td colspan="2">비밀번호</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="text" name="pw">
			</td>
		</tr>
		<tr>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td colspan="2">비밀번호 재확인</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="text" name="pwChek">
			</td>
		</tr>
		<tr>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td colspan="2">이름</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="text" name="name">
			</td>
		</tr>
		<tr>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td colspan="2">닉네임</td>
		</tr>
		<tr>
			<td>
				<input type="text" name="nick">
			</td>
			<td>
				<input type="button" value="확인"/>
			</td>
		</tr>
		<tr>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td colspan="2">휴대전화</td>
		</tr>
		<tr>
			<td>
				<input type="text" name="phone">
			</td>
			<td>
				<input type="button" value="확인"/>
			</td>
		</tr>
		<tr>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="가입하기"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>