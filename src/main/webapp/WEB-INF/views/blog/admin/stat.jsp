<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<script type="text/javascript">
	$(function(){
		//id => $('#아이디밸류')
		//class => $('.클래스밸류')
		//name => $('[name="네임밸류"]')
		$("#blog_topic").val("${BLOG.blog_topic}").prop("selected", true);
	
		$("#categoryUpdateBtn").click(function(){
			var objects_per_page = document.getElementsByName("objects_per_page");
			  
	        for (var i=0; i<objects_per_page.length; i++) {
	            if (objects_per_page[i].checked == true) {
	                alert(objects_per_page[i].value);
	            }
	        }
			/*
			var queryString = $("#blogFrm").serialize();
			 
	        $.ajax({
	            type : 'post',
	            url : '${pageContext.request.contextPath}/blog/blogUpdate',
	            data : queryString,
	            dataType : 'json',
	            error: function(xhr, status, error){
	                //alert(error);
	            },
	            success : function(json){
	                //alert(json)
	            }
	        });*/
		})
		
		$(":radio[name='objects_per_page'][value='${BLOG.objects_per_page}']").prop('checked', true);
	})
	</script>
</head>
<body>
MYVER 블로그 | 관리
<div class="row">
	<div class="col-md-12">
		<ul>
			<li>
				<a href="${pageContext.request.contextPath}/blog.admin/${BLOG.blog_id}/config">기본 설정</a>
				</li>
			<li>
				<a href="${pageContext.request.contextPath}/blog.admin/${BLOG.blog_id}/content/topmenu">메뉴·글·동영상 관리</a>
				</li>
			<li>
				<a href="${pageContext.request.contextPath}/blog.admin/${BLOG.blog_id}/stat/today">내 블로그 통계</a>
			</li>
		</ul>
	</div>
	<div class="col-md-3">
		<div>
			<div>오늘</div>
			<ul>
				<li>
					<a href="${pageContext.request.contextPath}/blog.admin/${BLOG.blog_id}/stat/today">일간 현황</a>
				</li>
			</ul>   
		</div>
		<div>
			<div>방문 분석</div>
			<ul>
				<li>조회수</li>
				<li>방문 횟수</li>
			</ul>   
		</div>
		<div>
			<div>사용자 분석</div>
			<ul>
				<li>검색 유입 분석</li>
				<li>시간대 분석</li>
				<li>성별·연령별 분포</li>
			</ul>   
		</div>
		<div>
			<div>순위</div>
			<ul>
				<li>조회수 순위</li>
				<li>좋아요수 순위</li>
				<li>댓글수 순위</li>
			</ul>   
		</div>
	</div>
	<div class="col-md-9">
		<form id="upperCategoryFrm">
			<div>
				<h1>일간 현황</h1>
				<div>&lt;날짜&gt;</div>
			</div>
			<div>
				<ul>
					<li>
						<span>조회수</span>
						<strong>10</strong>
					</li>
					<li>
						<span>댓글수</span>
						<strong>10</strong>
					</li>
					<li>
						<span>좋아요수</span>
						<strong>10</strong>
					</li>
					<li>
						<span>이웃증감수</span>
						<strong>10</strong>
					</li>
				</ul>
			</div>
			<div>
				<div>
					<a>조회수</a>
				</div>
				<div>
					<a>방문횟수</a>
				</div>
				<div>
					<a>성별,연령별 분포</a>
				</div>
			</div>
			<div>
				<span>~시간 기준</span>
			</div>
			<div>그래프~~</div>
			<div>
				<div>
					<div>게시물 조회수 순위</div>
					<div>
						<table>
							<tr>
								<th>순위</th>
								<th>제목</th>
								<th>조회수</th>
							</tr>
							<tr>
								<td>1</td>
								<td>제목~~</td>
								<td>6</td>
							</tr>
						</table>
						<div>
							<a>더보기 &gt;</a>
						</div>
					</div>
				</div>
				<div>
					<div>게시물 댓글 순위</div>
					<div>
						<table>
							<tr>
								<th>순위</th>
								<th>제목</th>
								<th>댓글수</th>
							</tr>
							<tr>
								<td>1</td>
								<td>제목~~</td>
								<td>6</td>
							</tr>
						</table>
						<div>
							<a>더보기 &gt;</a>
						</div>
					</div>
				</div>
			</div>
			<div>
				<div>
					<div>검색어 유입 분석</div>
					<div>
						<ul>
							<li>kakao 신입 공채</li>
							<li>스택 선형</li>
							<li>~~</li>
						</ul>
						<div>
							<a>더보기 &gt;</a>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
</body>
</html>