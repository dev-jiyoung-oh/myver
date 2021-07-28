<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
	<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/css/myver.css" rel="stylesheet">
	
	<script type="text/javascript">
		$(function(){
			$('#logout_btn').click(function(){
				$('#logout_frm').submit();
			})
		})
	</script>
</head>


<header class="navbar navbar-expand-md navbar-light bg-light border-bottom">
	<div class="container">
	    <a class="navbar-brand text-success" href="${pageContext.request.contextPath}/">
	    	<img src="${pageContext.request.contextPath}/resources/img/myver_logo.png" height="34"/>
	    </a>
	    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	      	<span class="navbar-toggler-icon"></span>
	    </button>
	    <div class="collapse navbar-collapse" id="navbarSupportedContent">
	    	<ul class="navbar-nav me-auto mb-lg-0">
	    		<li class="nav-item">
	          		<a class="nav-link text-success nav-custom-a" href="#">소개</a>
	        	</li>
	        	<li class="nav-item">
	          		<a class="nav-link text-success nav-custom-a" href="${pageContext.request.contextPath}/memo/list">쪽지</a>
	        	</li>
	        	<li class="nav-item">
	          		<a class="nav-link text-success nav-custom-a" href="${pageContext.request.contextPath}/blog/home">블로그</a>
	        	</li>
		        <li class="nav-item">
		          	<a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">카페</a>
		        </li>
		        <li class="nav-item">
		          	<a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">고객센터</a>
		        </li>
	        </ul>
	    
	    	<ul class="navbar-nav">
				<%-- 로그인 중이 아닐 때에만 로그인,회원가입 버튼이 보임  -> taglib ( security/tags ) 때문에 가능 --%>
				<sec:authorize access="isAnonymous()">
					<li class="nav-item">
						<a class="btn btn-outline-success mx-1" href="${pageContext.request.contextPath}/login">로그인</a>
					</li>
					<li class="nav-item">
						<a class="btn btn-outline-warning" href="${pageContext.request.contextPath}/join">회원가입</a>
					</li>
				</sec:authorize>
				
				<%-- 로그인 시 --%>
				
				<sec:authorize access="isAuthenticated()">
					<li class="nav-item">
						<sec:authentication property="principal" var="user"/>
						<div class="dropdown">
							<button class="btn dropdown-toggle mx-1" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
								${user.nick} 님
							</button>
							<ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
								<li><a class="dropdown-item" href="#">내 정보</a></li>
								<li><a class="dropdown-item" href="${pageContext.request.contextPath}/memo/list">쪽지</a></li>
								<li><a class="dropdown-item pb-3" href="${pageContext.request.contextPath}/blog/${user.id}">내 블로그</a></li>
								<li><a class="dropdown-item text-danger text-center" id="logout_btn">로그아웃</a></li>
							</ul>
						</div>
					</li>
				</sec:authorize>
			</ul>
		</div>
	</div>
	<form action="${pageContext.request.contextPath}/logout" method="POST" id="logout_frm"> 
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	</form>
<!-- 
<a href="${pageContext.request.contextPath}/">MYVER</a>

<%-- 로그인중이 아닐 때에만 로그인,회원가입 버튼이 보임  -> taglib ( security/tags ) 때문에 가능 --%>
<sec:authorize access="isAnonymous()">
	<a href="${pageContext.request.contextPath}/login">로그인</a>
	<a href="${pageContext.request.contextPath}/join">회원가입</a>
</sec:authorize>

<%-- 로그인 중일 경우에만 로그아웃 버튼이 보임 --%>
<sec:authorize access="isAuthenticated()">
	<form action="${pageContext.request.contextPath}/logout" method="POST"> 
		<input type="submit" id="logoutBtn" value="Logout" />
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	</form>
	
	${sessionScope.MID} 님
	<sec:authentication property="principal" var="user"/>
	${user.id}
	${user.nick}
	${user.auth}
</sec:authorize>

<%-- 관리자 로그인 시 --%>
<sec:authorize access="hasRole('ADMIN')">
- 관리자 로그인함 -
</sec:authorize>

<%-- 일반 회원 로그인 시 --%>
<sec:authorize access="hasRole('MEMBER')">
- 회원 로그인함 -
</sec:authorize>
 -->
</header>
</html>