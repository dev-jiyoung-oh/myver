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
<form action="${pageContext.request.contextPath}/findId" method="POST">
	<table>
		<tr>
			<td>비밀번호 찾기</td>
		</tr>
		<tr>
			<td>회원정보에 등록한 휴대전화로 인증</td>
		</tr>
		<tr>
			<td>회원정보에 등록한 휴대전화 번호를 정확히 입력하시면 아이디를 찾으실 수 있습니다.</td>
		</tr>
		<tr>
			<td>
				<input type="number" name="phone"/>
			</td>
		</tr>
		<tr>
			<td>
				<input type="submit" value="확인" />
			</td>
		</tr>
	</table>
</form>
</body>
</html>