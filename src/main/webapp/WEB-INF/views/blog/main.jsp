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

<a class="hanSansYello">MY</a>
<a class="hanSansGreen">VER</a>&nbsp;
<a class="hanSansGreen">블로그</a>

<div class="row">
	<div class="col-md-12">
		<a>이웃 블로그</a>
		<a>블로그 홈</a>
		<a>내 메뉴</a>
		<sec:authorize access="isAuthenticated()">
			<a>${sessionScope.MID}</a>
		</sec:authorize>
	</div>
	<div class="col-md-12">
		<a>${BLOG.blog_title}</a>
	</div>
	<div class="col-md-3">
		<a>${BLOG.blog_nick}</a>
		<a>${BLOG.blog_info}</a>
	</div>
	<div class="col-md-3">
		<a>카테고리</a>
	</div>
</div>
</body>
</html>
