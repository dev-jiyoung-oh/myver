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
<form action="${pageContext.request.contextPath}/findPwAuth" method="POST">
	<input type="hidden" name="id" value="${ID}"/>
	<table>
		<tr>
			<td>비밀번호 찾기</td>
		</tr>
		<tr>
			<td>본인 확인</td>
		</tr>
		<tr>
			<td>회원정보에 등록한 휴대전화 번호를 정확히 입력해주세요.</td>
		</tr>
		<tr>
			<td>
				<input type="number" name="phone"/>
			</td>
		</tr>
		<tr>
			<td>
				<input type="submit" value="다음" />
			</td>
		</tr>
	</table>
</form>
</body>
</html>