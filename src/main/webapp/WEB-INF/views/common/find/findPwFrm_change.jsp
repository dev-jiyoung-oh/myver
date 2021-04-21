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
<form action="${pageContext.request.contextPath}/findPwChange" method="POST">
	<table>
		<tr>
			<td>비밀번호 찾기</td>
		</tr>
		<tr>
			<td>비밀번호 재설정</td>
		</tr>
		<tr>
			<td>비밀번호를 변경해 주세요.</td>
		</tr>
		<tr>
			<td>
				<input type="password" name="pw" placeholder="새 비밀번호"/>
			</td>
		</tr>
		<tr>
			<td>
				<input type="password" name="pwChk" placeholder="새 비밀번호 확인"/>
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