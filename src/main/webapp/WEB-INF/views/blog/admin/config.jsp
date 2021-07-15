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
			alert("버튼 누름!");
			var queryString = $("#blogFrm").serialize();
			 
	        $.ajax({
	            type : 'post',
	            url : '${pageContext.request.contextPath}/blog.admin.update/blog',
	            data : queryString,
	            error: function(request,status,error){
	                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	            },
	            success : function(json){
	                alert("성공적으로 반영되었습니다.");
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
			<div>기본 정보 관리</div>
			<ul>
				<li>블로그 정보</li>
			</ul>   
		</div>
		<div>
			<div>이웃 관리</div>
			<ul>
				<li>내가 추가한 이웃</li>
				<li>나를 추가한 이웃</li>
			</ul>   
		</div>
		<div>
			<div>사생활 보호</div>
			<ul>
				<li>블로그 초기화</li>
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
				<tr>
					<td>블로그명</td>
					<td>
						<input type="text" name="blog_title" value="${BLOG.blog_title}"/>
						<p>한글, 영문, 숫자 혼용가능 (한글 기준 25자 이내)</p>
					</td>
				</tr>
				<tr>
					<td>별명</td>
					<td>
						<input type="text" name="blog_nick" value="${BLOG.blog_nick}"/>
						<p>한글, 영문, 숫자 혼용가능 (한글 기준 10자 이내)</p>
					</td>
				</tr>
				<tr>
					<td>소개글</td>
					<td>
						<textarea name="blog_info" rows="" cols="">${BLOG.blog_info}</textarea>
						<p>블로그 프로필 영역의<br/>프로필 이미지 아래에 반영됩니다.<br/>(한글 기준 200자 이내)</p>
					</td>
				</tr>
				<tr>
					<td>내 블로그 주제</td>
					<td>
						<select id="blog_topic" name="blog_topic">
							<option>문학·책</option>
							<option>영화</option>
							<option>미술·디자인</option>
							<option>공연·전시</option>
							<option>음악</option>
							<option>드라마</option>
							<option>스타·연예인</option>
							<option>만화·애니</option>
							<option>방송</option>
							<option>일상·생각</option>
							<option>육아·결혼</option>
							<option>애완·반려동물</option>
							<option>좋은글·이미지</option>
							<option>패션·미용</option>
							<option>인테리어·DIY</option>
							<option>요리·레시피</option>
							<option>상품리뷰</option>
							<option>원예·재배</option>
							<option>게임</option>
							<option>스포츠</option>
							<option>사진</option>
							<option>자동차</option>
							<option>취미</option>
							<option>국내여행</option>
							<option>세계여행</option>
							<option>맛집</option>
							<option>IT·컴퓨터</option>
							<option>사회·정치</option>
							<option>건강·의학</option>
							<option>비즈니스·경제</option>
							<option>어학·외국어</option>
							<option>교육·학문</option>
							<option>주제 선택 보류</option>
						</select>
						<p>내 블로그에서 다루는 주제를 선택하세요.<br/>프로필 영역에 노출됩니다.</p>
					</td>
				</tr>
				<tr>
					<td>블로그 프로필 이미지</td>
					<td>
						<img src=""/>
						<a>등록</a>
						<a>삭제</a>
						<p>프로필 이미지는 가로 161px 섬네일로 생성됩니다.<br/> - 블로그스킨에 따라 섬네일이 축소/확대되어 적용됩니다.<br><a href="#">프로필 이미지가 보이지 않는다면?</a></p>
					</td>
				</tr>
			</table>
			<div>
				<input type="button" id="blogUpdateBtn" value="확인"/>
			</div>
			
			<input type="hidden" name="blog_id" value="${BLOG.blog_id}">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
	</div>
	
	<div class="col-md-9">
		<form id="blogFrm">
			<h1>내가 추가한 이웃</h1>
			<table>
				<tr>
					<td><input type="checkbox"/></td>
					<td>
						<select>
							<option selected>내가 이웃으로 추가한 사람 전체</option>
							<option>상대도 나를 추가한 사람</option>
							<option>나만 상대를 추가한 사람</option>
						</select>
					</td>
					<td>추가일</td>
				</tr>
				<c:forEach items="${FOLLOWINGS}" var="following">
					<tr>
						<td><input type="checkbox"/></td>
						<td>${following.blog_nick} | 
							<a href="${pageContext.request.contextPath}/blog/${following.blog_nick}">${following.blog_title}</a>
						</td>
						<td>${following.date}</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
	
	<div class="col-md-9">
		<form id="blogFrm">
			<h1>나를 추가한 이웃</h1>
			<table>
				<tr>
					<td><input type="checkbox"/></td>
					<td>
						<select>
							<option selected>나를 이웃으로 추가한 사람 전체</option>
							<option>나도 이웃으로 추가한 사람</option>
							<option>상대만 나를 추가한 사람</option>
						</select>
					</td>
					<td>추가일</td>
				</tr>
				<c:forEach items="${FOLLOWERS}" var="follower">
					<tr>
						<td><input type="checkbox"/></td>
						<td>${follower.blog_nick} | 
							<a href="${pageContext.request.contextPath}/blog/${follower.blog_nick}">${follower.blog_title}</a>
						</td>
						<td>${follower.date}</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
	
</div>
</body>
</html>