<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script>
/*
function getContent(event){
	alert($(event.currentTarget));
}*/
function getContent(n, e){
	alert(event.target.parent());
	/*
	for(var key in event.target){
		console.log("attributes:"+key+", value:"+event.target[key]);
	}
	*/
	// alert(event.target["innerHTML"]);
	/*
	$.ajax({ 
		type: "POST", 
		url : "${pageContext.request.contextPath}/memo/getContent", 
		data: { "mn" :  n}, 
		dataType : "json", // 서버에서 반환되는 데이터 형식을 지정
		async: false, // 동기 방식으로 처리(아작스 처리 후 return isok;)
		success : function(data){
			if(data != null){
				//alert("성공 : "+data.content);
				
				$.each(data.fileList, function(index, list){
					//alert(index);
					//alert(list.original_name+" "+list.saved_path+" "+list.saved_name);
					var original_name = list.original_name;
					var saved_name = list.saved_name;
					var saved_path = list.saved_path;
					$('#read_table').empty();
					$('#read_table').append("<tr><td colspan='1'><a href='/filepath/"+saved_path+saved_name+"' download='"+original_name+"'>"+original_name+"</></td></tr>")
				})
				
				//$('#read_table').append("<tr><td colspan='1'></td></tr>")
				//'<a href="'+data.저장경로+'" title="'+data.원래이름+'" data'
			}else {
				alert('실패');
			}
		}, 
		error: function(request,status,error) {
			alert("????"+"code = "+ request.status + " message = " + request.responseText + " error = " + error); 
		}
	})
	*/
}
	$(function(){
		//id => $('#아이디밸류')
		//class => $('.클래스밸류')
		//name => $('[name="네임밸류"]')
	
	})
</script>
</head>
<body>
<div class="container">
<div class="row">
	<div id="nav_snb" class="col-md-3">
		<div class="text-center">
			<div class="btn-group d-flex">
				<span class="btn btn-success">쪽지쓰기</span>
				&nbsp;
				<span class="btn btn-success">내게쓰기</span>
			</div>
		</div>
		
		<div class="section_typemail">
			<a href="#" class="" title="안읽은 쪽지">
				<span class="cnt" id="unreadMailCount">999+</span>
				<em>안읽음<span class="btdnd">쪽지 목록 보기</span></em>
			</a>
			<a href="#" class="" title="중요메일">
				<span class="icon"></span>
				<em>중요<span class="btdnd">메일 목록 보기</span></em>
			</a>
			<a href="#" class="" title="첨부파일이 있는 메일">
				<span class="icon"></span>
				<em>첨부<span class="btdnd">메일 목록 보기</span></em>
			</a>
			<a href="#" class="" title="내가 수신인에 포함된 메일">
				<span class="icon"></span>
				<em><span class="btdnd">내가 </span>받는사람<span class="btdnd">인 메일 목록 보기</span></em>
			</a>
		</div>

		<div id="lnb_scroll_area" class="svc_menu_area scrl">
			<div id="svc_menu_content">
				<ul class="nav flex-column">
					<li class="nav-item">
                        <i class="ico_mbox"></i>
                        <a href="#" title="전체 쪽지" class="nav-link list-group-item-action">
                        	받은쪽지함
                           	<span id="" class="badge badge-custom float-right" title="정리하기">
                            	정리하기
                          	</span>
                        </a>
					</li>
                 	<li class="nav-item">
                   		<i class="ico_mbox"></i>
                   		<a href="#" title="개인 쪽지" class="nav-link list-group-item-action pl-2rem">
                   			개인쪽지
                    		<strong class="text-success" title="안읽은 쪽지 수">125</strong>
                   		</a>
                  	</li>
                  	<li class="nav-item">
                   		<i class="ico_mbox"></i>
                   		<a href="#" title="카페 단체" class="nav-link list-group-item-action pl-2rem">
                   			카페단체
                    		<strong class="text-success" title="안읽은 쪽지 수">3</strong>
                   		</a>
                  	</li>
					<li class="nav-item">
                        <i class="ico_mbox"></i>
                        <a href="#" class="nav-link list-group-item-action" title="보낸 쪽지함">
                        	보낸쪽지함
                        	<span class="badge badge-custom float-right" title="내게쓰기">
                        		내게쓰기
                        	</span>
                        </a>
					</li>
					<li id="" class="nav-item">
                        <i class="ico_mbox"></i>
                        <a href="#" class="nav-link list-group-item-action" title="내게 쓴 쪽지함">
                       		내게 쓴 쪽지함
                       		<span class="badge badge-custom float-right" title="수신확인">
                       			수신확인
                     		</span>
                        </a>
					</li>
					<li id="" class="nav-item">
                        <i class="ico_mbox"></i>
                        <a class="nav-link list-group-item-action" title="쪽지 보관함">쪽지보관함</a>
					</li>
					<li id="" class="nav-item">
                        <i class="ico_mbox"></i>
                        <a href="#" class="nav-link list-group-item-action" title="스팸 쪽지함">
                        	스팸쪽지함
	                        <span class="badge badge-custom float-right" title="비우기">
	                          	비우기
	                        </span>
                        </a>
					</li>
					<li id="" class="nav-item">
                        <i class="ico_mbox"></i>
                        <a href="#" class="nav-link list-group-item-action" title="휴지통">
                        	휴지통
                            <strong class="text-success" title="안읽은 쪽지 수">10</strong>
                            <span class="badge badge-custom float-right" title="비우기">
                            	비우기
                           	</span>
                        </a>
					</li>
				</ul>
			</div>
		</div>
	</div>

	<div class="col-md-9">
		<ol id="">
			<c:forEach var="memo" items="${MEMOLIST}">
				<li class="list|${memo.memo_no}">
					<ul class="list-group list-group-horizontal">
						<li class="list-group-item">
							<c:if test="${memo.box==2}">발신</c:if>
							<c:if test="${memo.box==0}">개인</c:if>
						</li>
						<li class="list-group-item">
							<input type="checkbox">
						</li>
						<li class="list-group-item">
							<c:if test="${memo.is_important==0}">안중요</c:if>
							<c:if test="${memo.is_important==1}">중요</c:if>
						</li>
						<li class="list-group-item">
							<c:if test="${memo.is_read==0}">안읽음</c:if>
							<c:if test="${memo.is_read==1}">읽음</c:if>
						</li>
						<li class="list-group-item">
							${memo.writer_id} &lt;${memo.writer_name}&gt;
						</li>
						<!-- <td><a href="${pageContext.request.contextPath}/memo/read?mn=${memo.memo_no}">${memo.title}</a></td> -->
						<li class="list-group-item">
							<a name="list_title">
								${memo.title}
							</a>
						</li>
						<li class="list-group-item">${memo.date}</li>
						<li class="list-group-item">${memo.memo_size}</li>
					</ul>
				</li>
			</c:forEach>
		</ol>
	</div>
	
	<div class="col-md-9">
		<table border="1" id="read_table">
			<tr>
				<td id="is_important"></td>
				<td id="title"></td>
				<td id="date"></td>
			</tr>
			<tr>
				<td>보낸사람</td>
				<td colspan="2" id="read_writer">
					${MEMO.writer_id} &lt;${MEMO.writer_name}&gt;
				</td>
			</tr>
			<tr>
				<td>받는사람</td>
				<td colspan="2" id="read_receiver">
					${MEMO.receiver_id} &lt;${MEMO.receiver_name}&gt;
				</td>
			</tr>
			<tr>
				<td colspan="3" id="read_content">
					${MEMO.content}
				</td>
			</tr>
		</table>
	</div>
	
	<div class="col-md-9">
        <h2 class="text-center">게시글 쓰기</h2>
        <form id="write_frm">
          <input type="hidden" name="writer_id" id="writer_id" value="${sessionScope.MID}" />
          <input type="hidden" name="writer_name" value="" />
          <input type="hidden" name="receiver_name" value="" />
          <input type="hidden" name="date" value="" />
          <table class="table table-striped">
            <tr>
                <td>받는사람</td>
                <td><input type="text"  class="" name="receiver_id" id="receiver_id"></td>
            </tr>
            <tr>
                <td>제목</td>
                <td><input type="text"  class="" name="title" id="title"></td>
            </tr>
            <tr>
                <td>파일첨부</td>
                <td>
                	<div>
                		<input multiple="multiple" type="file" class="" id="file">
             		</div>
             		<div style="display:none;" id="write_file_content">
             			<table id="write_file_content_table">
             				<tr>
             					<td>
             						<input type="checkbox" id="all_file_check">
             					</td>
             					<td>파일명</td>
             					<td>용량</td>
             				</tr>
             			</table>
             		</div>
             		<div>
             			<input value="삭제" type="button" id="delete_file_btn">
             		</div>
             	</td>
            </tr>
            <tr>
                <td colspan="2"><textarea rows="10" cols="50" name="content" id="content" class=""></textarea></td>
            </tr>
            <tr>
                 
                <td colspan="2"  class="text-center">
                    <input id="submit_btn" type="button" value="보내기" class="btn btn-success">
                    <input type="reset" value="다시작성" class="btn btn-warning">
                </td>
            </tr>
             
          </table>
        </form>
    </div>			
</div>	
</div>		
</body>
</html>