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

			$.ajax({ 
				type: "POST", 
				url : "${pageContext.request.contextPath}/findPw", 
				data: { id : $('[name="id"]').val() }, 
				async: false, // 동기 방식으로 처리(아작스 처리 후 return isok;)
				success : function(result){
					if(result == "success"){
						isok = true;
					}else {
						alert(result+'실패');
						isok = false;
					}
				}, 
				error: function(request,status,error) { 
					alert("????"+"code = "+ request.status + " message = " + request.responseText + " error = " + error); 
					isok = false;
				} 
			})
			
			return isok;
		})
		
	})
</script>
</head>
<body>
<form action="${pageContext.request.contextPath}/findPwAuthFrm" id="frm" name="frm" method="POST">
	<table>
		<tr>
			<td>비밀번호 찾기</td>
		</tr>
		<tr>
			<td>아이디 입력</td>
		</tr>
		<tr>
			<td>비밀번호를 찾고자 하는 아이디를 입력해 주세요.</td>
		</tr>
		<tr>
			<td>
				<input type="text" name="id"/>
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