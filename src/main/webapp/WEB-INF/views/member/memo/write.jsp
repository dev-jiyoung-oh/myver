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
		var saveFiles = [];

		// 파일 첨부 ====================================================================
		$('[name="file"]').change(function () {      
		  	if($('#write_file_content').css('display') == 'none'){
		  		$('#write_file_content').show();
		  	}
			
			var files = this.files; 
			var arr = new Array();
			var html = '';
			
		  	for(var i=0; i<files.length; i++) {
		    	var file = files[i];
		    	saveFiles.push(file);
		    	
		  		// 타이틀 비어있는 경우 파일명으로 채운다.
		  		if(!$('#title').val()){
		  			$('#title').val(file.name);
		  		}
		  		
		    	arr.push({name : file.name, size: file.size});
		  	}
		  	
		  	for(key in arr){
		  		html += '<tr><td><input type="checkbox" name="file_check"></td>';
		  		html += '<td>'+ arr[key].name + '</td>';
		  		html += '<td>' + arr[key].size + '</td></tr>';
		  		i++;
		  	}
		  				
	    	$('#write_file_content_table').append(html);
	    	
	    	alert(saveFiles.length);
	    	for(var i=0; i<saveFiles.length; i++){
	    		var file = saveFiles[i];
	    		alert(i+' '+file.name+' '+file.size);
	    	}
		});
		
		// 전체 체크/체크 해제 ====================================================================
		$('#all_file_check').click(function(){
			$("input[name=file_check]:checkbox").prop("checked", this.checked);
		})
		
		// 선택한 파일 삭제 ====================================================================
		$('#delete_file_btn').click(function(){
			// 선택된 것이 없다면!
			if($("input:checkbox[name=file_check]:checked").length == 0){
				alert('삭제할 파일을 선택하세요.');
				return false;
			}
			
			var index_array = new Array();
			var removed = 0;
			
			// 체크된 파일 순회
			$("input:checkbox[name=file_check]:checked").each(function() {
				var index = $(this).closest('tr').prevAll().length;
				$(this).closest('tr').remove();
				index_array.push(index+removed-1);
				removed++;
			});
			
			// 큰 인덱스부터 saved_file에서 제거
			for(var i=index_array.length-1; i>=0; i--){
				saveFiles.splice(index_array[i], 1);
			}
		})
		
		$('#submit_btn').click(function(){
			var formData = $('#write_frm').serializeArray();
			formData.push({ name: "file_array", value: saveFiles });
		})
		
		
		/*var formData = new FormData();

		for(var i=0;i<storedFiles.length;i++) {
			formData.append("files", storedFiles[i]);
		}*/
		  
	})
	</script>
</head>
<body>
<div class="row">
    <div class="col-md-2"></div>
    <div class="col-md-8">
        <h2 class="text-center">게시글 쓰기</h2>
        <form action="${pageContext.request.contextPath}/memo/write" method="POST" id="write_frm" enctype="multipart/form-data">
          <input type="hidden" name="writer_id" value="${sessionScope.MID}" />
          <input type="hidden" name="writer_name" value="" />
          <input type="hidden" name="receiver_name" value="" />
          <input type="hidden" name="date" value="" />
          <table class="table table-striped">
            <tr>
                <td>받는사람</td>
                <td><input type="text"  class="" name="receiver_id"></td>
            </tr>
            <tr>
                <td>제목</td>
                <td><input type="text"  class="" name="title" id="title"></td>
            </tr>
            <tr>
                <td>파일첨부</td>
                <td>
                	<div>
                		<input multiple="multiple" type="file"  class="" name="file">
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
                <td colspan="2"><textarea rows="10" cols="50" name="content" class=""></textarea></td>
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
</body>
</html>