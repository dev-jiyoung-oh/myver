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
   <script type="text/javascript">
   	$(function(){
   		
   	})
   </script>
</head>
<body>
<!-- blog main -->
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="user"/>	
</sec:authorize>
<div class="row m-auto" style="width: 1000px">
	<div class="col-md-12 d-none">
		<a>이웃 블로그</a>
		<a>블로그 홈</a>
		<a>내 메뉴</a>
	</div>
	<div class="col-md-12 text-end p-6rem">
		<h2 class="fw-bold">${BLOG.blog_title}</h2>
	</div>
	<div class="col-md-2 border p-2 text-center" style="width:180px">
		<c:if test="${empty BLOG.path or empty BLOG.saved_name}">
			<img src="${pageContext.request.contextPath}/resources/img/icons/no_thumbnail.png" height="80">
		</c:if>
		<c:if test="${!empty BLOG.path and !empty BLOG.saved_name}">
			<img src="/filepath/${BLOG.path}/${BLOG.saved_name}">
		</c:if>
		<br/>
		<div>
			<strong>${BLOG.blog_nick}</strong>
			<span>(${BLOG.blog_id})</span>
		</div>
		<p class="my-2">${BLOG.blog_info}</p>
		<c:if test="${BLOG.blog_id eq user.id}">
			<div>
				<div>
					<button type="button" class="btn btn-success w-100">
						<i class="bi bi-pencil"></i>글쓰기
					</button>
					<!-- 
					<i class="bi bi-pencil"></i>
					<a href="${pageContext.request.contextPath}/blog/${BLOG.blog_id}/write" class="hover-text-decoration-undeline">글쓰기</a>
					 -->
				</div>
				<div class="text-end fs-08rem p-2">
					<i class="bi bi-gear-fill text-secondary"></i>
					<a href="${pageContext.request.contextPath}/blog.admin/${BLOG.blog_id}/config" class="hover-text-decoration-undeline">관리</a>
					·
					<a href="${pageContext.request.contextPath}/blog.admin/${BLOG.blog_id}/stat/today" class="hover-text-decoration-undeline">통계</a>
				</div>
			</div>
		</c:if>
		<c:if test="${!empty user.id and BLOG.blog_id ne user.id}">
			<a class="btn btn-outline-success w-100" onclick="window.open('${pageContext.request.contextPath}/blog/neighborChange?blog_id=${BLOG.blog_id}', '이웃 설정', 'menubar=no');">
				<c:choose>
					<c:when test="${IS_NEIGHBOR}">
						이웃
					</c:when>
					<c:otherwise>
						이웃 추가
					</c:otherwise>
				</c:choose>
			</a>
		</c:if>
	</div>
	<div class="col-md-2 mx-3">
		<div id="" class="border-bottom alert-link accordion-button p-1 bg-white text-black" data-bs-toggle="collapse" data-bs-target="#category_list_div" aria-controls="category_list_div" aria-expanded="true" role="button">
			카테고리
		</div>
		<div id="category_list_div" class="collapse show">
			<ul class="nav flex-column">
				<c:forEach var="category" items="${CATEGORY_LIST}">
					<li class="nav-item <c:if test='${category.all_category eq 1}'>py-2</c:if>">
						<div class="d-inline-flex">
							<div class="d-inline-flex">
								<c:if test="${category.parent_category_no != 0}">
									<img src="${pageContext.request.contextPath}/resources/img/icons/depth.png" height="14" class="align-bottom m-1 me-0">
								</c:if>
								<img src="${pageContext.request.contextPath}/resources/img/icons/list.png" height="14" class="align-bottom m-1"> 
							</div>
							<div class="">
								<a href="${pageContext.request.contextPath}/blog/${BLOG.blog_nick}?blog_category_no=${category.blog_category_no}" class="hover-text-decoration-undeline <c:if test='${category.all_category eq 1}'>alert-link</c:if>">
									${category.category_name}</a>
								<c:if test="${category.is_public eq 1}">
									<i class="fas fa-lock text-secondary h-50 align-top mt-04rem"></i>
								</c:if>
							</div>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
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
		<c:choose>
			<c:when test="${empty LIST && empty OBJECTS}">
				<div class="w-100 text-center p-5">
					<strong>아직 작성된 글이 없습니다.</strong>
					<c:if test="${BLOG.blog_id eq user.id}">
						<p>문득 스치는 생각이나 기분, 일기 등 다양한 이야기로<br/>나만의 공간을 채워보세요!</p>
						<button type="button" class="btn btn-outline-success">
							<i class="bi bi-pencil"></i>
							글쓰기
						</button>
					</c:if>
				</div>
			</c:when>
			<c:otherwise>
				<div class="w-100">
					<div>
						<a href="${pageContext.request.contextPath}/blog/${BLOG.blog_nick}?blog_category_no=${CATEGORY.blog_category_no}">${CATEGORY.category_name}</a>
						<a>${CATEGORY_TOTAL}개의 글</a>
					</div>
					<a>
						<span>목록닫기</span>
						<span>목록열기</span>
					</a>
					<table class="table">
						<thead>
							<tr class="text-secondary">
								<td>글제목</td>
								<td>조회수</td>
								<td>작성일</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="list" items="${LIST}">
								<tr>
									<td>
										<a href="${pageContext.request.contextPath}/blog/${BLOG.blog_nick}/${list.blog_object_no}?blog_category_no=${CATEGORY.blog_category_no}" class="hover-text-decoration-undeline">
											${list.title}
										</a>
									</td>
									<td>${list.hits}</td>
									<td>${list.date}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="w-100">
					<c:forEach var="object" items="${OBJECTS}">
						<table class="table table-borderless">
							<tr>
								<td colspan="2">
									<a href="${pageContext.request.contextPath}/blog/${BLOG.blog_nick}?blog_category_no=${object.blog_category_no}" class="hover-text-decoration-undeline">
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
			</c:otherwise>
		</c:choose>
	</div>
</div>
</body>
</html>
