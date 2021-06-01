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
<input type="search">
<div class="row">
	<div class="col-md-12">
	인기글div
	</div>
	<div class="col-md-8">
	이웃새글div
	</div>
	<div class="col-md-4">
		<div>
			<span>
				블로그 이미지
			</span>
			<a>닉네임${BLOG.blog_nick}</a>
			<a>오늘 방문자수${TODAYVISIT}</a>
			<a>로그아웃</a>
			<nav>
				<a>내 블로그${BLOG.blog_no}</a>
				<a>글쓰기</a>
			</nav>
		</div>
		<div>
			<div>
				<a>내 소식</a>
				<a>내가 남긴 글</a>
				<a>이웃 목록</a>
			</div>
			<div>
				리스트~
			</div>
			<div>
				<a>모두 삭제</a>
				<a>&lt;</a>
				<a>&gt;</a>
			</div>
		</div>
		
	</div>
</div>
</body>
</html>
