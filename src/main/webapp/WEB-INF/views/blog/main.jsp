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
		<c:if test="${empty BLOG.path or empty BLOG.saved_name}">
			<img src="${pageContext.request.contextPath}/resources/img/icons/no_thumbnail.png">
		</c:if>
		<c:if test="${!empty BLOG.path and !empty BLOG.saved_name}">
			<img src="/filepath/${BLOG.path}/${BLOG.saved_name}">
		</c:if>
		<br/>
		<a>${BLOG.blog_nick}</a><br/>
		<a>(${BLOG.blog_id})</a><br/>
		<a>${BLOG.blog_info}</a>
		<c:if test="${BLOG.blog_id eq sessionScope.MID}">
			<div>
				<div>
					<a href="${pageContext.request.contextPath}/blog.admin/${BLOG.blog_id}/config">관리</a>
					·
					<a href="${pageContext.request.contextPath}/blog.admin/${BLOG.blog_id}/stat/today">통계</a>
				</div>
				<div>
					<a href="${pageContext.request.contextPath}/blog/${BLOG.blog_id}/write">글쓰기</a>
				</div>
			</div>
		</c:if>
	</div>
	<div class="col-md-3">
		<ul>
			<c:forEach var="category" items="${CATEGORY_LIST}">
				<li>
					<a href="${pageContext.request.contextPath}/blog/${BLOG.blog_nick}?blog_category_no=${category.blog_category_no}">
						${category.category_name}
					</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div class="col-md-3">
		내가 추가한
		<ul>
			<c:forEach var="following" items="${FOLLOWING}">
				<li>
					<a href="${pageContext.request.contextPath}/blog/${following.blog_id}">
						<c:if test="${empty following.path or empty following.saved_name}">
							<img src="${pageContext.request.contextPath}/resources/img/icons/no_thumbnail.png">
						</c:if>
						<c:if test="${!empty following.path and !empty following.saved_name}">
							<img src="/filepath/${following.path}/${following.saved_name}">
						</c:if>
					</a>
					<a href="${pageContext.request.contextPath}/blog/${following.blog_id}">${following.blog_nick}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div class="col-md-3">
		나를 추가한
		<ul>
			<c:forEach var="follower" items="${FOLLOWER}">
				<li>
					<a href="${pageContext.request.contextPath}/blog/${follower.blog_id}">
						<c:if test="${empty follower.path or empty follower.saved_name}">
							<img src="${pageContext.request.contextPath}/resources/img/icons/no_thumbnail.png">
						</c:if>
						<c:if test="${!empty follower.path and !empty follower.saved_name}">
							<img src="/filepath/${follower.path}/${follower.saved_name}">
						</c:if>
					</a>
					<a href="${pageContext.request.contextPath}/blog/${follower.blog_id}">${follower.blog_nick}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div class="col-md-12">
		<a href="${pageContext.request.contextPath}/blog/${BLOG.blog_nick}?blog_category_no=${CATEGORY.blog_category_no}">${CATEGORY.category_name}</a> <a>${CATEGORY_TOTAL}개의 글</a>
		<table border="1">
			<tr>
				<td>글제목</td>
				<td>조회수</td>
				<td>작성일</td>
			</tr>
			<c:forEach var="list" items="${LIST}">
				<tr>
					<td>
						<a href="${pageContext.request.contextPath}/blog/${BLOG.blog_nick}/${list.blog_object_no}?blog_category_no=${CATEGORY.blog_category_no}">
							${list.title}
						</a>
					</td>
					<td>${list.hits}</td>
					<td>${list.date}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="col-md-12">
		<c:forEach var="object" items="${OBJECTS}">
			<table border="1">
				<tr>
					<td colspan="2">
						<a href="${pageContext.request.contextPath}/blog/${BLOG.blog_nick}?blog_category_no=${object.blog_category_no}">
							${object.category_name}
						</a>
					</td>
				</tr>
				<tr>
					<td>${object.title}</td>
					<%-- <td>${object.hits}</td> --%>
					<td>${object.date}</td>
				</tr>
				<tr>
					<td colspan="2">${object.content}</td>
				</tr>
			</table>
		</c:forEach>
	</div>
</div>
</body>
</html>
