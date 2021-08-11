<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
	$(function(){
	    opener.parent.location.reload();

	    //id => $('#') //class => $('.') //name => $('[name=""]')
		
   		//window.close();
	});
</script>
</head>

<body>
	<div class="">
		<c:choose>
			<c:when test="${ADD}">
				<div class="popup_heading">
					<h1 class="title">이웃 추가</h1>
				</div>
				<div class="popup_text">
					<div class="buddy_state">
						<p>
							<strong class="text-success">${BLOG.blog_nick}</strong>님을 이웃으로 추가했습니다.
						</p>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="popup_heading">
					<h1 class="title">이웃 끊기</h1>
				</div>
				<div class="popup_text">
					<div class="buddy_state">
						<p>
							<strong class="text-success buddy_state_text">${BLOG.blog_nick}</strong>님과의 이웃을 끊었습니다.
						</p>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<div class="area_button">
			<a href="#" onclick="window.close()" class="btn btn-success">닫기</a>
		</div>
	</div>
</body>
</html>