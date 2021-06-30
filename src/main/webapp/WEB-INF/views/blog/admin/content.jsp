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
			<div>메뉴 관리</div>
			<ul>
				<li>
					<a href="${pageContext.request.contextPath}/blog.admin/${BLOG.blog_id}/content/topmenu">상단메뉴 설정</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/blog.admin/${BLOG.blog_id}/content/category">카테고리 설정</a>
				</li>
			</ul>   
		</div>
		<div>
			<div>글 관리</div>
			<ul>
				<li>
					<a href="${pageContext.request.contextPath}/blog.admin/${BLOG.blog_id}/content/object">게시글</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/blog.admin/${BLOG.blog_id}/content/comment">댓글</a>
				</li>
			</ul>   
		</div>
	</div>
	
	<c:if test="${!empty CATEGORYS_FOR_UPPER}">
	<div class="col-md-9">
		<form id="upperCategoryFrm">
			<h1>상단메뉴 설정</h1>
			<table>
				<tr>
					<td>
						<div>블로그 카테고리</div>
						<div>
							<ul>
								<c:forEach var="category" items="${CATEGORYS_FOR_UPPER}">
									<li>
										<c:if test="${category.parent_category_no != 0}">--</c:if>
										${category.category_name}
										<c:if test="${category.is_public == 1}">
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
								<c:forEach var="category" items="${CATEGORYS_FOR_UPPER}">
									<c:if test="${category.is_upper == 1}">
										<li>
										${category.category_name}
										<c:if test="${category.is_public == 1}">
											<img src="${pageContext.request.contextPath}/resources/img/icons/lock.png">
										</c:if>
										</li>
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
	</c:if>
	
	<c:if test="${!empty CATEGORYS}">
	<div class="col-md-9">
		<form id="categoryFrm">
			<h1>카테고리 설정</h1>
			<table>
				<tr>
					<td>페이지당 글</td>
					<td>
						<input type="radio" name="objects_per_page" value="1">1개 
						<input type="radio" name="objects_per_page" value="3">3개 
						<input type="radio" name="objects_per_page" value="5">5개 
						<input type="radio" name="objects_per_page" value="10">10개 
					</td>
				</tr>
			</table>
			<table>
				<tr>
					<td>
						<div>카테고리 관리·설정</div>
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
	</c:if>
	
	<c:if test="${!empty CATEGORYS_FOR_OBJECT}">
	<div class="col-md-9">
		<form id="categoryFrm">
			<h1>게시글</h1>
			<div>
				<select>
					<option>전체</option>
					<option>제목</option>
					<option>내용</option>
				</select>
				<input type="text">
				<input type="button" value="검색">
				<select>
					<c:forEach var="category" items="${CATEGORYS_FOR_OBJECT}">
						<option value="${category.blog_category_no}">${category.category_name}</option>
					</c:forEach>
				</select>
				<input type="button" value="삭제">
			</div>
			<table>
				<tr>
					<td><input type="checkbox"></td>
					<td>카테고리</td>
					<td>제목</td>
					<td>작성일</td>
				</tr>
				<c:if test="${!empty OBJECTS}">
				<c:forEach var="object" items="${OBJECTS}">
					<tr>
						<td><input type="checkbox"></td>
						<td>${object.category_name}</td>
						<td>
							<a href="${pageContext.request.contextPath}/blog/${BLOG.blog_nick}/${object.blog_object_no}">${object.title}</a>
							<br/>${object.content}
						</td>
						<td>
							${object.date}
						</td>
					</tr>
				</c:forEach>
				</c:if>
			</table>
		</form>
	</div>
	</c:if>
	
	<c:if test="${!empty COMMENTS}">
	<div class="col-md-9">
		<form id="categoryFrm">
			<h1>댓글</h1>
			<div>
				<input type="text">
				<input type="button" value="ID 검색">
				<input type="button" value="전체보기">
				<input type="button" value="삭제">
			</div>
			<table>
				<tr>
					<td><input type="checkbox"></td>
					<td>작성자</td>
					<td>내용</td>
					<td>작성일</td>
				</tr>
				<c:forEach var="comment" items="${COMMENTS}">
					<tr>
						<td><input type="checkbox"></td>
						<td>${comment.nick}<br/>(${comment.id})</td>
						<td>
							<a href="${pageContext.request.contextPath}/blog/${comment.nick}/${comment.blog_object_no}">${comment.title}</a>
							<br/>${comment.comment}
						</td>
						<td>${comment.date}</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
	</c:if>
</div>
</body>
</html>