<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script>
	$(function(){
		//id => $('#아이디밸류')
		//class => $('.클래스밸류')
		//name => $('[name="네임밸류"]')
		$('#frm').submit(function(event){
			var isok;
			
			
			
			return isok;
		})
		
	})
</script>
</head>
<body>
<form action="${pageContext.request.contextPath}/" id="frm">
	<table>
		<tr>
			<td>비밀번호 찾기</td>
		</tr>
		<tr>
			<td>비밀번호 재설정</td>
		</tr>
		<tr>
			<td>비밀번호 변경을 실패했습니다.</td>
		</tr>
		<tr>
			<td>
				<input type="button" value="비밀번호 찾기"/>
			</td>
		</tr>
		<tr>
			<td>
				<input type="button" value="로그인"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
