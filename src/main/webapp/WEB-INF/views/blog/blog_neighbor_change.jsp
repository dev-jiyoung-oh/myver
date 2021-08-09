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
		//id => $('#') //class => $('.') //name => $('[name=""]')
		
	    /*opener.parent.location.reload();
   		window.close();*/
	});
</script>
</head>

<body>
	<div class="">
		<c:choose>
			<c:when test="${IS_NEIGHBOR}">
				<form method="POST" action="">
					<div class="popup_heading">
						<h1 class="title">이웃 끊기</h1>
					</div>
					<div class="popup_text">
						<div class="buddy_state">
							<p>
								<strong class="text-success buddy_state_text">${BLOG.blog_nick}</strong>님과의 이웃을 끊습니다.
							</p>
						</div>
						<div class="tip_box">
							<ul class="list">
								<li class="item">
									<a target="_blank" href="#" class="">이웃은 무엇인가요?</a>
								</li>
								<li class="item">
									<a target="_blank" href="#" class="">이웃공개 글은 누가 볼 수 있게 되나요?</a>
								</li>
							</ul>
							<a target="_blank" href="#" class="">블로그 이용 TIP 더보기</a>
						</div>
					</div>
					<div class="area_button">
						<a href="#" onclick="window.close()" class="btn btn-outline-secondary">취소</a>
						<a href="#" class="btn btn-success">다음</a>
					</div>
				</form>
			</c:when>
			<c:otherwise>
				<form id="">
					<div class="popup_heading">
						<h1 class="title">이웃 추가</h1>
					</div>
					<div class="popup_text">
						<div class="buddy_state">
							<p>
								<strong class="text-success">${BLOG.blog_nick}</strong>님을 이웃으로 추가합니다.
							</p>
						</div>
						<div class="tip_box">
							<ul class="list">
								<li class="item">
									<a target="_blank" href="#" class="">이웃은 무엇인가요?</a>
								</li>
								<li class="item">
									<a target="_blank" href="#" class="">이웃공개 글은 누가 볼 수 있게 되나요?</a>
								</li>
							</ul>
							<a target="_blank" href="#" class="">블로그 이용 TIP 더보기</a>
						</div>
					</div>
					<div class="area_button">
						<a href="#" onclick="window.close()" class="btn btn-outline-secondary">취소</a>
						<a href="#" class="btn btn-success">다음</a>
					</div>
				</form>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>