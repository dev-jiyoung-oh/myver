<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<script id="companyTemplate" type="text/html">     
		<li>${company}</li> 
	</script>

	<script type="text/javascript">
	//var datas = [ {"compnay": "회사1"} , {"compnay": "회사2"} , {"compnay": "회사3"} ] 
	// companyTemplate에 datas객체에 있는 데이터를 바인딩하여 companyList공간안 제일 뒷부분에 소스를 추가합니다
	//$("#companyTemplate").tmpl(datas).appendTo("#companyList");
	
	// 블로그 정보 div 생성
	function newPageBlogInfo(){
		console.log('newPageBlogInfo()');
		
		$.ajax({
            type : 'GET',
            url : '${pageContext.request.contextPath}/blog.admin/blogInfo.myver',
            data : {blog_id : '${BLOG.blog_id}'},
            dataType : 'json',
            error: function(xhr, status, error){
                alert(status+" "+error);
            },
            success : function(data){
            	//console.log(data);
            	//alert('ajax 성공');
            	if(data == 'no_login'){
            		alert("로그인 필요");
            		// ★★★★ 처리해야함 ★★★★★★★★★★★★★★★★★★★★★★★★★
            	}else if(data == 'error'){
            		alert("에러 발생");
            		// ★★★★ 처리해야함 ★★★★★★★★★★★★★★★★★★★★★★★★★
            	}else{ // 로그인 성공
            		console.log('블로그 정보');
            		console.log(data);
            		let html = '<div class="p-4" style="width: 800px;"><form id="blogFrm2">';
            		let topic_list = [];
            		// DB에서 가져오기~
            		topic_list.push('맛집');
            		topic_list.push('라라라');
            		
            		html += '<h3 class="py-3" style="border-bottom: 2px solid black;">블로그 정보</h3>';
            		html += '<table class="table my-4">';
            		html += '<tr><td>블로그 주소</td><td>'+'${pageContext.request.contextPath}/blog/'+data.blog_id+'</td></tr>';
            		html += '<tr><td>블로그명</td><td><input type="text" name="blog_title" value="'+data.blog_title+'"/><p>한글, 영문, 숫자 혼용가능 (한글 기준 25자 이내)</p></td></tr>';
            		html += '<tr><td>별명</td><td><input type="text" name="blog_nick" value="'+data.blog_nick+'"><p class="small">한글, 영문, 숫자 혼용가능 (한글 기준 10자 이내)</p></td></tr>';
            		html += '<tr><td>소개글</td><td><textarea name="blog_info" class="float-start">'+data.blog_info+'</textarea><p class="small float-start">블로그 프로필 영역의<br/>프로필 이미지 아래에 반영됩니다.<br/>(한글 기준 200자 이내)</p></td></tr>';
            		html += '<tr><td>내 블로그 주제</td><td><select id="blog_topic" name="blog_topic">';
					for(i=0; i<topic_list.length; i++){
						html += '<option>'+ topic_list[i] + '</option>';
					}
					html += '</select><p class="small">내 블로그에서 다루는 주제를 선택하세요.<br/>프로필 영역에 노출됩니다.</p></td></tr>';
					html += '<tr><td>블로그 프로필 이미지</td><td></td></tr>';
					html += '</table>';
					html += '<div class="text-center"><input type="button" value="확인" onclick="blogUpdate()"/><input type="hidden" name="blog_id" value="'+data.blog_id+'"></div>';
					html += '</form><div>';
					
					$('#content').html(html);
            	}
            }
        });
	}
	
	// 내가 추가한 이웃 div 생성
	function newPageMyNeighborManage(){
		console.log('newPageMyNeighborManage()');
		
		$.ajax({
            type : 'GET',
            url : '${pageContext.request.contextPath}/blog.admin/myNeighborManage.myver',
            data : {blog_id : '${BLOG.blog_id}'},
            dataType : 'json',
            error: function(xhr, status, error){
            	alert("에러 발생");
                alert(status+" "+error);
            },
            success : function(data){
            	console.log(data);
            	//alert('ajax 성공');
            	if(data == 'no_login'){
            		alert("로그인 필요");
            		// ★★★★ 처리해야함 ★★★★★★★★★★★★★★★★★★★★★★★★★
            	}else{ // 로그인 성공
            		let html = '<form id="blogFrm">';
					html += '<h1>내가 추가한 이웃</h1>';
					html += '<div class="following-action">' +
								'<div class="following-action1 float-start my-3 mx-1">' +
									'<span class=""><button type="button" class="btn btn-sm btn-light border-secondary following-delete">삭제</button></span>' +
								'</div>' +
								'<div class="following-action2 float-end my-3">' +
									'<span class="float-start me-1">정렬된 이웃<strong>n</strong>명</span>' +
									'<div class="float-start">' +
										'<select><option selected>이웃추가순</option><option>블로그명순</option><option>이웃별명순</option></select>' +
							'</div></div></div>';
					html += '<table class="table text-center">' +
								'<tr class="table-light">' +
									'<td class="col w-6"><input type="checkbox" class="following-all-check"/></td>' +
									'<td class="col w-20"><select><option selected>이웃전체</option><option>이웃</option><option>서로이웃</option></select></td>' +
									'<td class="col text-start w-auto">이웃</td>' +
									'<td class="col w-20">추가일</td>' +
								'</tr>';
					for(i=0; i<data.length; i++){
						html += '<tr class="' + data[i].neighbor_member_no;
						if(data[i].isBothNeighbor){
							html += ' following-both-neighbor">' +
										'<td class=""><input type="checkbox" name="following-check" value="' + data[i].neighbor_member_no + '" class="following-check"/></td>' + 
										'<td class="col">서로이웃</td>';
						}else{
							html += ' following-neighbor">' +
										'<td class=""><input type="checkbox" name="following-check" value="' + data[i].neighbor_member_no + '" class="following-check"/></td>' + 
										'<td class="col">이웃</td>';
						}
						html += '<td class="col text-start">' + data[i].blog_nick + ' | <a href="${pageContext.request.contextPath}/blog/' + data[i].blog_nick + '">' + data[i].blog_title + '</a></td>' +
								'<td class="">'+data[i].date+'</td></tr>'
					}
					html += '</table></form>';
					
					$('#content').html(html);
            	}
            }
        });
	}
	
	// 나를 추가한 이웃 div 생성
	function newPageNeighborMeManage(){
		console.log('newPageNeighborMeManage()');
		
		$.ajax({
            type : 'post',
            url : '${pageContext.request.contextPath}/blog.admin/neighborMeManage.myver',
            data : {blog_id : '${BLOG.blog_id}'},
            dataType : 'json',
            error: function(xhr, status, error){
                alert(status+" "+error);
            },
            success : function(data){
            	alert('ajax 성공');
            	if(data == 'no_login'){
            		alert("로그인 필요");
            		// ★★★★ 처리해야함 ★★★★★★★★★★★★★★★★★★★★★★★★★
            	}else if(data == 'error'){
            		alert("에러 발생");
            		// ★★★★ 처리해야함 ★★★★★★★★★★★★★★★★★★★★★★★★★
            	}else{ // 로그인 성공
            		/*
            		let html = '<div class="p-4" style="width: 800px;"><form id="blogFrm2">';
            		let topic_list = [];
            		// DB에서 가져오기~
            		topic_list.push('맛집');
            		topic_list.push('라라라');
            		
            		html += '<h3 class="py-3" style="border-bottom: 2px solid black;">블로그 정보</h3>';
            		html += '<table class="table my-4">';
            		html += '<tr><td>블로그 주소</td><td>'+'${pageContext.request.contextPath}/blog/'+data.blog_id+'</td></tr>';
            		html += '<tr><td>블로그명</td><td><input type="text" name="blog_title" value="'+data.blog_title+'"/><p>한글, 영문, 숫자 혼용가능 (한글 기준 25자 이내)</p></td></tr>';
            		html += '<tr><td>별명</td><td><input type="text" name="blog_nick" value="'+data.blog_nick+'"><p class="small">한글, 영문, 숫자 혼용가능 (한글 기준 10자 이내)</p></td></tr>';
            		html += '<tr><td>소개글</td><td><textarea name="blog_info" class="float-start">'+data.blog_info+'</textarea><p class="small float-start">블로그 프로필 영역의<br/>프로필 이미지 아래에 반영됩니다.<br/>(한글 기준 200자 이내)</p></td></tr>';
            		html += '<tr><td>내 블로그 주제</td><td><select id="blog_topic" name="blog_topic">';
					for(i=0; i<topic_list.length; i++){
						html += '<option>'+ topic_list[i] + '</option>';
					}
					html += '</select><p class="small">내 블로그에서 다루는 주제를 선택하세요.<br/>프로필 영역에 노출됩니다.</p></td></tr>';
					html += '<tr><td>블로그 프로필 이미지</td><td></td></tr>';
					html += '</table>';
					html += '<div class="text-center"><input type="button" value="확인" onclick="blogUpdate()"/><input type="hidden" name="blog_id" value="'+data.blog_id+'"></div>';
					html += '</form><div>';
					
					$('#content').html(html);
            		*/
            	}
            }
        });
	}
	
	// 블로그 초기화 div 생성
	function newPageBlogReset(){
		console.log('newPageBlogReset()');
		
		$.ajax({
            type : 'post',
            url : '${pageContext.request.contextPath}/blog.admin/blogReset.myver',
            data : {blog_id : '${BLOG.blog_id}'},
            dataType : 'json',
            error: function(xhr, status, error){
                alert(status+" "+error);
            },
            success : function(data){
            	alert('ajax 성공');
            	if(data == 'no_login'){
            		alert("로그인 필요");
            		// ★★★★ 처리해야함 ★★★★★★★★★★★★★★★★★★★★★★★★★
            	}else if(data == 'error'){
            		alert("에러 발생");
            		// ★★★★ 처리해야함 ★★★★★★★★★★★★★★★★★★★★★★★★★
            	}else{ // 로그인 성공
            		/*
            		let html = '<div class="p-4" style="width: 800px;"><form id="blogFrm2">';
            		let topic_list = [];
            		// DB에서 가져오기~
            		topic_list.push('맛집');
            		topic_list.push('라라라');
            		
            		html += '<h3 class="py-3" style="border-bottom: 2px solid black;">블로그 정보</h3>';
            		html += '<table class="table my-4">';
            		html += '<tr><td>블로그 주소</td><td>'+'${pageContext.request.contextPath}/blog/'+data.blog_id+'</td></tr>';
            		html += '<tr><td>블로그명</td><td><input type="text" name="blog_title" value="'+data.blog_title+'"/><p>한글, 영문, 숫자 혼용가능 (한글 기준 25자 이내)</p></td></tr>';
            		html += '<tr><td>별명</td><td><input type="text" name="blog_nick" value="'+data.blog_nick+'"><p class="small">한글, 영문, 숫자 혼용가능 (한글 기준 10자 이내)</p></td></tr>';
            		html += '<tr><td>소개글</td><td><textarea name="blog_info" class="float-start">'+data.blog_info+'</textarea><p class="small float-start">블로그 프로필 영역의<br/>프로필 이미지 아래에 반영됩니다.<br/>(한글 기준 200자 이내)</p></td></tr>';
            		html += '<tr><td>내 블로그 주제</td><td><select id="blog_topic" name="blog_topic">';
					for(i=0; i<topic_list.length; i++){
						html += '<option>'+ topic_list[i] + '</option>';
					}
					html += '</select><p class="small">내 블로그에서 다루는 주제를 선택하세요.<br/>프로필 영역에 노출됩니다.</p></td></tr>';
					html += '<tr><td>블로그 프로필 이미지</td><td></td></tr>';
					html += '</table>';
					html += '<div class="text-center"><input type="button" value="확인" onclick="blogUpdate()"/><input type="hidden" name="blog_id" value="'+data.blog_id+'"></div>';
					html += '</form><div>';
					*/
					
					$('#content').html(html);
            	}
            }
        });
	}
	
	const adminConfig = {
		goToPage: function(href){
			//alert(href);
			//alert(href.substr(href.lastIndexOf('/')+1));
			let page = href.substr(href.lastIndexOf('/')+1);
			
			function pageEmpty(){
				$('#content').empty();
			};
			
			if(page == 'blogInfo.myver'){
				alert('블로그 정보');
				newPageBlogInfo();
			}else if(page == 'myNeighborManage.myver'){
				alert('내가 추가한 이웃');
				newPageMyNeighborManage();
			}else if(page == 'neighborMeManage.myver'){
				alert('나를 추가한 이웃');
				// 페이지 생성해야함
			}else if(page == 'blogReset.myver'){
				alert('블로그 초기화');
				// 페이지 생성해야함
			}
		}
	}
	
	function blogUpdate(){
		var queryString = $("#blogFrm2").serialize();
		console.log(queryString);
		
        $.ajax({
            type : 'post',
            url : '${pageContext.request.contextPath}/blog.admin/blogInfoUpdate.myver',
            data : queryString,
            error: function(xhr, status, error){
                alert(error);
            },
            success : function(data){
                alert(data);
            }
        });
	}
	
	$(function(){
		//id => $('#아이디밸류')
		//class => $('.클래스밸류')
		//name => $('[name="네임밸류"]')
		$("#blog_topic").val("${BLOG.blog_topic}").prop("selected", true);
		
		newPageBlogInfo();
		
		
		$(".following-delete").click(function(){
			if($(".following-check:checked").length == 0){
				alert("먼저 이웃을 선택해주세요.");
				return false;
			}
			
			if(confirm("선택한 이웃을 삭제하시겠습니까?")){
				var followings = [];
				
				$(".following-check:checked").each(function(){
					followings.push($(this).val());
				})
				
				//console.log(followings);
				
				$.ajax({
		            type : 'post',
		            url : '${pageContext.request.contextPath}/blog.admin/neighborDelete',
		            data : {followings : followings},
		            dataType : 'json',
		            traditional : true,
		            error: function(xhr, status, error){
		                alert(status+" "+error);
		            },
		            success : function(data){
		            	if(data[0] == -1){
		            		alert("로그인 필요");
		            		// ★★★★ 처리해야함 ★★★★★★★★★★★★★★★★★★★★★★★★★
		            	}else{
			            	//alert(data.length+"/"+followings.length+" 삭제 완료");
			            	$.each(data, function(i, val){
				            	$("input[name='following-check'][value='"+val+"']").closest('tr').remove();
			            	});
		            	}
		            }
		        });
			}
		});
	});
	</script>
</head>

<body>
<div class="container-fluid bg-light border-bottom p-0">
	<div class="container col-md-12 px-0">
		<a class="float-start my-3 text-secondary text-center border-end" style="width: 200px;">블로그 관리</a>
		<ul class="nav">
			<li class="nav-item">
				<a href="${pageContext.request.contextPath}/blog.admin/${BLOG.blog_id}/config" class="nav-link p-3 text-success fw-bold ms-3">기본 설정</a>
			</li>
			<li class="nav-item">
				<a href="${pageContext.request.contextPath}/blog.admin/${BLOG.blog_id}/content/topmenu" class="nav-link p-3 text-black">메뉴·글·동영상 관리</a>
			</li>
			<li class="nav-item">
				<a href="${pageContext.request.contextPath}/blog.admin/${BLOG.blog_id}/stat/today" class="nav-link p-3 text-black">내 블로그 통계</a>
			</li>
		</ul>
	</div>
</div>
<div class="container">
	<div class="row">
		<div class="col-md-3 py-4 px-3 border-end small" style="width: 200px;">
			<div class="pb-3 border-bottom">
				<div class="fw-bold" style="font-size: 15px;">기본 정보 관리</div>
				<ul class="nav flex-column">
					<li class="nav-item">
						<a href="javascript:adminConfig.goToPage('${pageContext.request.contextPath}/blog.admin/blogInfo.myver');" class="text-black hover-text-decoration-undeline">
							블로그 정보
						</a>
					</li>
				</ul>   
			</div>
			<div class="pb-3 border-bottom">
				<div class="fw-bold" style="font-size: 15px;">이웃 관리</div>
				<ul class="nav flex-column">
					<li class="nav-item">
						<a href="javascript:adminConfig.goToPage('${pageContext.request.contextPath}/blog.admin/myNeighborManage.myver');" class="text-black hover-text-decoration-undeline">
							내가 추가한 이웃
						</a>
					</li>
					<li class="nav-item">
						<a href="javascript:adminConfig.goToPage('${pageContext.request.contextPath}/blog.admin/neighborMeManage.myver');" class="text-black hover-text-decoration-undeline">
							나를 추가한 이웃
						</a>
					</li>
				</ul>   
			</div>
			<div class="pb-3">
				<div class="fw-bold" style="font-size: 15px;">데이터 관리</div>
				<ul class="nav flex-column">
					<li class="nav-item">
						<a href="javascript:adminConfig.goToPage('${pageContext.request.contextPath}/blog.admin/blogReset.myver');"  class="text-black hover-text-decoration-undeline">
							블로그 초기화
						</a>
					</li>
				</ul>   
			</div>
		</div>
		
		<div id="content" class="col-md-9">
			<!-- 
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
			</form> -->
		</div>
		<!-- 
		<div class="col-md-9">
			<form id="blogFrm">
				<h1>내가 추가한 이웃</h1>
				<div class="following-action">
					<div class="following-action1 float-start my-3 mx-1">
						<span class="">
							<button type="button" class="btn btn-sm btn-light border-secondary following-delete">삭제</button>
						</span>
					</div>
					<div class="following-action2 float-end my-3">
						<span class="float-start me-1">
							정렬된 이웃
							<strong>n</strong>
							명
						</span>
						<div class="float-start">
							<select>
								<option selected>이웃추가순</option>
								<option>블로그명순</option>
								<option>이웃별명순</option>
							</select>
						</div>
					</div>
				</div>
				<table class="table text-center">
					<tr class="table-light">
						<td class="col w-6"><input type="checkbox" class="following-all-check"/></td>
						<td class="col w-20">
							<select>
								<%-- 
								<option selected>내가 이웃으로 추가한 사람 전체</option>
								<option>상대도 나를 추가한 사람</option>
								<option>나만 상대를 추가한 사람</option>
								--%>
								<option selected>이웃전체</option>
								<option>이웃</option>
								<option>서로이웃</option>
							</select>
						</td>
						<td class="col text-start w-auto">이웃</td>
						<td class="col w-20">추가일</td>
					</tr>
					<c:forEach items="${FOLLOWINGS}" var="following">
						<tr class="${following.neighbor_member_no} <c:choose>
										<c:when test="${following.isBothNeighbor}">
											following-both-neighbor
										</c:when>
										<c:otherwise>
											following-neighbor
										</c:otherwise>
									</c:choose>">
							<td class=""><input type="checkbox" name="following-check" value="${following.neighbor_member_no}" class="following-check"/></td>
							<td class="col">
								<c:choose>
									<c:when test="${following.isBothNeighbor}">
										서로이웃
									</c:when>
									<c:otherwise>
										이웃
									</c:otherwise>
								</c:choose>
							</td>
							<td class="col text-start">${following.blog_nick} | 
								<a href="${pageContext.request.contextPath}/blog/${following.blog_nick}">${following.blog_title}</a>
							</td>
							<td class="">${following.date}</td>
						</tr>
					</c:forEach>
				</table>
			</form>
		</div>
		
		<div class="col-md-9">
			<form id="blogFrm">
				<h1>나를 추가한 이웃</h1>
				<table class="table text-center">
					<tr class="table-light">
						<td class="col w-6"><input type="checkbox"/></td>
						<td class="col text-start w-auto">
							<select>
								<option selected>나를 이웃으로 추가한 사람 전체</option>
								<option>나도 이웃으로 추가한 사람</option>
								<option>상대만 나를 추가한 사람</option>
							</select>
						</td>
						<td class="col w-20">이웃 추가</td>
						<td class="col w-20">추가일</td>
					</tr>
					<c:forEach items="${FOLLOWERS}" var="follower">
						<tr>
							<td><input type="checkbox" value="${follower.member_no}"/></td>
							<td class="col text-start">
								${follower.blog_nick}&nbsp;|&nbsp;
								<a href="${pageContext.request.contextPath}/blog/${follower.blog_nick}">${follower.blog_title}</a>
							</td>
							<td>
								<button type="button" class="btn btn-light border-secondary btn-sm" <c:if test="${follower.isBothNeighbor}">disabled</c:if>>이웃 추가</button>
							</td>
							<td>${follower.date}</td>
						</tr>
					</c:forEach>
				</table>
			</form>
		</div> -->
	</div>
</div>
</body>
</html>