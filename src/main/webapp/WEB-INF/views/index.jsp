<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<link rel="stylesheet" 
		href="https://fonts.googleapis.com/css?family=Black+Han+Sans&display=swap&subset=korean">
	<style type="text/css">
		.hanSansGreen {font-family : "Black Han Sans";
				  	   color : rgb(46, 204, 113);}
		.hanSansYello {font-family : "Black Han Sans";
				  	   color : rgb(241, 196, 15);}

	</style>
</head>
<body>

<a class="hanSansYello">MY</a><a class="hanSansGreen">VER</a>
<br/><input type="search"><br/>
<a href="${pageContext.request.contextPath}/memo/list">
	<img src="${pageContext.request.contextPath}/resources/img/icons/mail-green.png"/>
</a>
<a class="hanSansGreen">B</a><a class="hanSansYello">log</a>
<a class="hanSansGreen">C</a><a class="hanSansYello">afe</a>
</body>
</html>