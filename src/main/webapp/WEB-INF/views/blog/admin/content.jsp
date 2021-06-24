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
	
		$("#blogUpdateBtn").click(function(){
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
	        });
		})
	})
	</script>
</head>
<body>
MYVER 블로그 | 관리
<div class="row">
	<div class="col-md-12">
		<ul>
			<li>기본 설정</li>
			<li>메뉴·글·동영상 관리</li>
			<li>내 블로그 통계</li>
		</ul>
	</div>
	<div class="col-md-3">
		<div>
			<div>메뉴 관리</div>
			<ul>
				<li>상단메뉴 설정</li>
				<li>카테고리 설정</li>
			</ul>   
		</div>
		<div>
			<div>글 관리</div>
			<ul>
				<li>게시글 관리</li>
				<li>댓글 관리</li>
			</ul>   
		</div>
	</div>
	<div class="col-md-9">
		<form id="upperCategoryFrm">
			<h1>상단 메뉴 설정</h1>
			<table>
				<tr>
					<td>
						<div>블로그 카테고리</div>
						<div>
							<ul>
								<c:forEach var="category" items="${CATEGORYS}">
									<li>
										<c:if test="${category.parent_category_no != 0}">--</c:if>
										${category.category_name}
										<c:if test="${is_public == 1}">
											<img src="${pageContext.request.contextPath}/resources/img/icons/lock.png">
										</c:if>
									</li>
								</c:forEach>
							</ul>
						</div>
					</td>
					<td>
						<input type="button" value="선택">
					</td>
					<td>
						<div>선택한 메뉴</div>
						<div>
							<ul>
								<c:forEach var="category" items="${CATEGORYS}">
									<c:if test="${category.is_upper == 1}">
										<li>${category.category_name}</li>
									</c:if>
								</c:forEach>
							</ul>
						</div>
					</td>
				</tr>
			</table>
			<div>
				<input type="button" id="upperCategoryUpdateBtn" value="확인"/>
			</div>
		</form>
	</div>
	
	<div class="col-md-9">
		<form id="categoryFrm">
			<h1>상단 메뉴 설정</h1>
			<table>
				<tr>
					<td>
						<div>블로그 카테고리</div>
						<div>
							<ul>
								<c:forEach var="category" items="${CATEGORYS}">
									<li>
										<c:if test="${category.parent_category_no != 0}">--</c:if>
										${category.category_name}
										<c:if test="${is_public == 1}">
											<img src="${pageContext.request.contextPath}/resources/img/icons/lock.png">
										</c:if>
									</li>
								</c:forEach>
							</ul>
						</div>
					</td>
					<td>
						<table>
							<tr>
								<td>카테고리명</td>
								<td>
									<input type="text">
								</td>
							</tr>
							<tr>
								<td>공개설정</td>
								<td>
									<input type="radio" name="is_public">
									공개 
									<input type="radio" name="is_public">
									비공개
								</td>
							</tr>
							<tr>
								<td>주제분류</td>
								<td>
								</td>
							</tr>
							<tr>
								<td>목록보기</td>
								<td>
									<input type="radio" name="show_list">
									목록닫기 
									<input type="radio" name="show_list">
									목록열기 
									<select>
										<option>5줄 보기</option>
										<option>10줄 보기</option>
										<option>15줄 보기</option>
										<option>20줄 보기</option>
										<option>30줄 보기</option>
									</select>
								</td>
							</tr>
						</table>
						<div>
							<input type="checkbox"> 블로그에서 이 카테고리를 기본으로 보여줍니다.
						</div>
					</td>
				</tr>
			</table>
			<div>
				<input type="button" id="categoryUpdateBtn" value="확인"/>
			</div>
		</form>
	</div>
</div>
</body>
</html>