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
		<form id="blogFrm">
			<h1>블로그 정보</h1>
			<table>
				<tr>
					<td>블로그 주소</td>
					<td></td>
				</tr>
			</table>
			<div>
				<input type="button" id="blogUpdateBtn" value="확인"/>
			</div>
		</form>
	</div>
</div>
</body>
</html>