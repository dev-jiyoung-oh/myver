<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
blog object
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
	<div class="col-md-12">
		<table border="1">
			<tr>
				<td>${OBJECT.title}</td>
				<td>${OBJECT.hits}</td>
				<td>${OBJECT.date}</td>
			</tr>
			<tr>
				<td colspan="3">${OBJECT.content}</td>
			</tr>
		</table>
	</div>
	<%-- 
	<div class="col-md-3">
		<ul>
			<c:forEach var="category" items="${CATEGORY}">
				<li>${category.category_name}</li>
			</c:forEach>
		</ul>
	</div>
	<div class="col-md-12">
		<table border="1">
			<tr>
				<td>글제목</td>
				<td>조회수</td>
				<td>작성일</td>
			</tr>
			<c:forEach var="list" items="${LIST}">
				<tr>
					<td>${list.title}</td>
					<td>${list.hits}</td>
					<td>${list.date}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="col-md-12">
		<c:forEach var="object" items="${OBJECT}">
			<table border="1">
				<tr>
					<td>${object.title}</td>
					<td>${object.hits}</td>
					<td>${object.date}</td>
				</tr>
				<tr>
					<td colspan="3">${object.content}</td>
				</tr>
			</table>
		</c:forEach>
	</div>
	 --%>
</div>
</body>
</html>
