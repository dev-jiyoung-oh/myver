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
		var savedFiles = [];

		$('[name="file"]').change(function () {      
		  	if($('#write_file_content').css('display') == 'none'){
		  		$('#write_file_content').show();
		  	}
			
			var fileList = this.files; 
			var arr = new Array();
			var html = '';
			
		  	for(var i = 0; i < fileList.length; i++) {
		    	var file = fileList[i];
		    	savedFiles.push(file);
		    	arr.push({name : file.name, size: file.size});
		  	}
		  	
		  	for(key in arr){
		  		html += '<tr><td><input type="checkbox"></td>';
		  		html += '<td>' + arr[key].name + '</td>';
		  		html += '<td>' + arr[key].size + '</td></tr>';
		  	}
		  				
	    	$('#write_file_content_table').append(html);
		});
		
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
        <form action="${pageContext.request.contextPath}/memo/write" method="POST"  enctype="multipart/form-data">
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
                <td><input type="text"  class="" name="title"></td>
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
             						<input type="checkbox">
             					</td>
             					<td>파일명</td>
             					<td>용량</td>
             				</tr>
             			</table>
             		</div>
             	</td>
            </tr>
            <tr>
                <td colspan="2"><textarea rows="10" cols="50" name="content" class=""></textarea></td>
            </tr>
            <tr>
                 
                <td colspan="2"  class="text-center">
                    <input type="submit" value="보내기" class="btn btn-success">
                    <input type="reset" value="다시작성" class="btn btn-warning">
                </td>
            </tr>
             
          </table>
        </form>
    </div>
</div>
</body>
</html>