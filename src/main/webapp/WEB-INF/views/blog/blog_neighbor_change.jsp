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
	<div class="p-4">
		<form method="POST" action="${pageContext.request.contextPath}/blog/neighborChange">
			<input type="hidden" name="blog_id" value="${BLOG.blog_id}">
			<div>
				<c:choose>
					<c:when test="${IS_NEIGHBOR}">
						<input type="hidden" name="add" value="false">
						<div class="popup_heading mb-4">
							<h1 class="title fs-4 fw-bold">이웃 끊기</h1>
						</div>
						<div class="popup_text p-4 bg-light border">
							<div class="buddy_state">
								<p class="m-0">
									<strong class="text-success buddy_state_text">${BLOG.blog_nick}</strong>님과의 이웃을 끊습니다.
								</p>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<input type="hidden" name="add" value="true">
						<div class="popup_heading mb-4">
							<h1 class="title fs-4 fw-bold">이웃 추가</h1>
						</div>
						<div class="popup_text p-4 bg-light border">
							<div class="buddy_state">
								<p class="m-0">
									<strong class="text-success buddy_state_text">${BLOG.blog_nick}</strong>님을 이웃으로 추가합니다.
								</p>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
				<div class="tip_box my-2">
					<ul class="nav flex-column lh-1">
						<li class="nav-item">
							<a target="_blank" href="#" class="nav-link text-black hover-text-decoration-undeline">이웃은 무엇인가요?</a>
						</li>
						<li class="nav-item">
							<a target="_blank" href="#" class="nav-link text-black hover-text-decoration-undeline">이웃공개 글은 누가 볼 수 있게 되나요?</a>
						</li>
						<li class="nav-item">
							<a target="_blank" href="#" class="nav-link text-black hover-text-decoration-undeline">블로그 이용 TIP 더보기</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="area_button text-end mt-4">
				<a href="#" onclick="window.close()" class="btn btn-outline-secondary">취소</a>
				<input type="submit" class="btn btn-success " value="다음">
			</div>
		</form>
	</div>
</body>
</html>