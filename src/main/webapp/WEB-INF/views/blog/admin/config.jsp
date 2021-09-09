<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	// 블로그 정보 div 생성
	function newPageBlogInfo(){
		console.log('newPageBlogInfo()');
		
		$.ajax({
           url : '${pageContext.request.contextPath}/blog.admin/blogInfo.myver',
           data : {blog_id : '${BLOG.blog_id}'},
           dataType : 'json',
           error: function(xhr, status, error){
               alert(status+" "+error);
           },
           success : function(data){
	           	console.log(data);
	           	if(data == 'no_login'){
	           		alert("로그인 필요");
	           		// ★★★★ 처리해야함 ★★★★★★★★★★★★★★★★★★★★★★★★★
	           	}else if(data == 'error'){
	           		alert("에러 발생");
	           		// ★★★★ 처리해야함 ★★★★★★★★★★★★★★★★★★★★★★★★★
	           	}else{ // 로그인 성공
	           		/*
	           		console.log('블로그 정보');
	           		console.log(data);
	           		let html = '<div class="p-4" style="width: 800px;"><form id="blogFrm">';
	           		let topic_list = [];
	           		// DB에서 가져오기~
	           		topic_list.push('맛집');
	           		topic_list.push('라라라');
	           		
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
					let topic_list = [];
					topic_list.push({topic: "그림"});
					topic_list.push({topic: "만화"});
	           		console.log(topic_list);
					const html = '<div class="p-4" style="width: 800px;"><form id="blogFrm">'
								+	'<h3 class="py-3" style="border-bottom: 2px solid black;">블로그 정보</h3>'
								+	'<table class="table my-4">'
								+		'<tr><td>블로그 주소</td><td>${pageContext.request.contextPath}/blog/{{= blog_id}}</td></tr>'
								+		'<tr><td>블로그명</td><td><input type="text" name="blog_title" value="{{= blog_title}}"/><p>한글, 영문, 숫자 혼용가능 (한글 기준 25자 이내)</p></td></tr>'
								+		'<tr><td>별명</td><td><input type="text" name="blog_nick" value="{{= blog_nick}}"><p class="small">한글, 영문, 숫자 혼용가능 (한글 기준 10자 이내)</p></td></tr>'
					        	+		'<tr><td>소개글</td><td><textarea name="blog_info" class="float-start">{{= blog_info}}</textarea><p class="small float-start">블로그 프로필 영역의<br/>프로필 이미지 아래에 반영됩니다.<br/>(한글 기준 200자 이내)</p></td></tr>'
					        	+		'<tr>'
					        	+			'<td>내 블로그 주제</td>'
					        	+			'<td><select id="blog_topic" name="blog_topic"></select><p class="small">내 블로그에서 다루는 주제를 선택하세요.<br/>프로필 영역에 노출됩니다.</p></td>'
					        	+		'</tr>'
					        	+		'<tr><td>블로그 프로필 이미지</td><td></td></tr>'
					        	+	'</table>'
					        	+	'<div class="text-center">'
					        	+		'<input type="button" value="확인" onclick="blogUpdate()"/><input type="hidden" name="blog_id" value="{{= blog_id}}">'
					        	+	'</div>'
					        	+'</form><div>';
					
		        	$("#content").empty();
		        	$.tmpl(html, data).appendTo("#content");
		        	
		        	const tmpl = '<option>{{= topic}}</option>';
					$("#blog_topic").empty();
			   		$.tmpl(tmpl, topic_list).appendTo("#blog_topic");
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
           	if(data == 'no_login'){
           		alert("로그인 필요");
           		// ★★★★ 처리해야함 ★★★★★★★★★★★★★★★★★★★★★★★★★
           	}else{ // 로그인 성공
           		const html = '<div class="p-4" style="width: 800px;">'
							+	'<form id="blogFrm">'
							+		'<h3 class="py-3" style="border-bottom: 2px solid black;">내가 추가한 이웃</h3>'
							+		'<div class="following-action w-100">'
							+			'<div class="following-action1 float-start my-3 mx-1">'
							+				'<span class=""><button type="button" class="btn btn-sm btn-light border-secondary following-delete">삭제</button></span>'
							+			'</div>'
							+			'<div class="following-action2 float-end my-3">'
							+				'<span class="float-start me-1">정렬된 이웃<strong>n</strong>명</span>'
							+				'<div class="float-start">'
							+					'<select><option selected>이웃추가순</option><option>블로그명순</option><option>이웃별명순</option></select>'
							+		'</div></div></div>'
							+		'<table class="table text-center">'
							+			'<thead><tr class="table-light">'
							+				'<td class="col w-6"><input type="checkbox" class="following-all-check"/></td>'
							+				'<td class="col w-20"><select><option selected>이웃전체</option><option>이웃</option><option>서로이웃</option></select></td>'
							+				'<td class="col text-start w-auto">이웃</td>'
							+				'<td class="col w-20">추가일</td>'
							+			'</tr></thead>'
							+			'<tbody id="myNeighborManageTbody"></tbody>'
		   					+		'</table>'
		   					+	'</form>'
		   					+'<div>';
		   		$('#content').html(html);
		   		
		  		const tmpl = '<tr class="{{if isBothNeighbor}}following-both-neighbor{{else}}following-neighbor{{/if}}">'
							+	'<td class=""><input type="checkbox" name="following-check" value="{{= neighbor_member_no}}" class="following-check"/></td>'
							+	'<td class="col">{{if isBothNeighbor}}서로{{/if}}이웃</td>'
							+	'<td class="col text-start">{{= blog_nick}} | <a href="${pageContext.request.contextPath}/blog/{{= blog_id}}">{{= blog_title}}</a></td>'
							+	'<td class="">{{= date}}</td>'
							+'</tr>';
				$("#myNeighborManageTbody").empty();
		   		$.tmpl(tmpl, data).appendTo("#myNeighborManageTbody");
           	}
           }
       });
	}

	// 나를 추가한 이웃 div 생성
	function newPageNeighborMeManage(){
		console.log('newPageNeighborMeManage()!!!');
		
		$.ajax({
           type : 'GET',
           url : '${pageContext.request.contextPath}/blog.admin/neighborMeManage.myver',
           data : {id : '${BLOG.blog_id}'},
           error: function(xhr, status, error){
               alert(status+" "+error);
           },
           success : function(data){
	           	//alert('ajax 성공');
	           	console.log(data);
	           	if(data == 'no_login'){
	           		alert("로그인 필요");
	           		// ★★★★ 처리해야함 ★★★★★★★★★★★★★★★★★★★★★★★★★
	           	}else if(data == 'error'){
	           		alert("에러 발생");
	           		// ★★★★ 처리해야함 ★★★★★★★★★★★★★★★★★★★★★★★★★
	           	}else{ // 로그인 성공
	           		const html = 	'<div class="p-4" style="width: 800px;"><form id="blogFrm">'
	           					+	'<h3 class="py-3" style="border-bottom: 2px solid black;">나를 추가한 이웃</h3>'
	          					+	'<table class="table text-center my-4">'
	          					+		'<thead><tr class="table-light">'
	          					+			'<td class="col w-6"><input type="checkbox"/></td>'
	          					+			'<td class="col text-start w-auto">'
	          					+				'<select>'
	          					+					'<option selected>나를 이웃으로 추가한 사람 전체</option>'
	          					+					'<option>나도 이웃으로 추가한 사람</option>'
	          					+					'<option>상대만 나를 추가한 사람</option>'
	          					+				'</select>'
	          					+			'</td>'
	          					+			'<td class="col w-20">이웃 추가</td>'
	          					+			'<td class="col w-20">추가일</td>'
	          					+		'</tr></thead>'
	          					+		'<tbody id="neighborMeManageTbody"></tbody></table></form></div>';
	           		
	           		$('#content').html(html);
	           		
	          		const tmpl = '<tr>'
	        					+	'<td><input type="checkbox" value="{{= neighbor_member_no}}"/></td>'
	        					+	'<td class="col text-start">'
	        					+		'{{= blog_nick}}&nbsp;|&nbsp;'
	        					+		'<a href="${pageContext.request.contextPath}/blog/{{= blog_id}}">{{= blog_title}}</a>'
					        	+	'</td>'
					        	+	'<td><button type="button" class="btn btn-light border-secondary btn-sm" {{if isBothNeighbor}}disabled{{/if}}>이웃 추가</button></td>'
					        	+	'<td>{{= date}}</td>'
					        	+'</tr>';
					
					$("#neighborMeManageTbody").empty();
	           		$.tmpl(tmpl, data).appendTo("#neighborMeManageTbody");
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
				console.log('블로그 정보');
				newPageBlogInfo();
			}else if(page == 'myNeighborManage.myver'){
				console.log('내가 추가한 이웃');
				newPageMyNeighborManage();
			}else if(page == 'neighborMeManage.myver'){
				console.log('나를 추가한 이웃');
				newPageNeighborMeManage();
			}else if(page == 'blogReset.myver'){
				console.log('블로그 초기화');
				// 페이지 생성해야함
			}
		}
	}

	function blogUpdate(){
		var queryString = $("#blogFrm").serialize();
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
	});

	$('.following-delete').click(function(){
		alert('g');
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
		<div id="content" class="col-md-9"></div>
	</div>
</div>

</body>
</html>