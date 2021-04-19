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
	<table>
		<c:if test="${!empty ID}">
			<tr>
				<td>고객님의 정보와 일치하는 아이디입니다.</td>
			</tr>
			<tr>
				<td>${ID}</td>
			</tr>
			<tr>
			<td>
				<input type="button" value="로그인"/>
			</td>
		</tr>
		<tr>
			<td>
				<input type="button" value="비밀번호 찾기"/>
			</td>
		</tr>
		</c:if>
		
		<c:if test="${empty ID}">
			<tr>
				<td>고객님의 정보와 일치하는 아이디가 존재하지 않습니다.</td>
			</tr>
			<tr>
				<td>
					<input type="button" value="회원가입"/>
				</td>
			</tr>
		</c:if>
	</table>
</body>
</html>