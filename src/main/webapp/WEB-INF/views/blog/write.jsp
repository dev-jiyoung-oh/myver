<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
	$(function(){
		//id => $('#') //class => $('.') //name => $('[name=""]')
		
		$('#body_container').css('height', '93vh');
		$('#body_container').attr('class', 'container-fluid d-flex flex-column p-0');


		// "발행 시간" radio 변경 event
	    $("input:radio[name=radio_time]").click(function(){
	        if($('#radio_time_reservation').is(":checked")){
	            $("#reservation_time_div").attr("class", "d-block");
	        }else{
	        	$("#reservation_time_div").attr("class", "d-none");
	        }
	    });
		
		// "카테고리" select box 변경 event
		$('#category_select').change(function(){
			var is_public = $('#category_select > option:selected').attr("value2");
			
			if(is_public == 1){
				$('#open_public').prop("disabled", true);
				$('#open_neighbor').prop("disabled", true);
				$('#open_private').prop("checked", true);
			}else{
				$('#open_public').prop("disabled", false);
				$('#open_neighbor').prop("disabled", false);
			}
		})
		
		// 게시글 발행 버튼 클릭 event ====================================================================
		$('#submit_btn').click(function(){
			// 받는사람 없는 경우 처리
			if(!$('#title').val()){
				alert('제목을 입력해주세요.');
				$('#submit_cancel_btn').click();
				$('#title').focus();
				return false;
			}
			
			// 제목 없는 경우 처리
	  		if(!$('#content').val()){
	  			alert('본문 내용을 입력해주세요.');
	  			$('#submit_cancel_btn').click();
	  			$('#content').focus();
				return false;
	  		}
			
	  		if($('#radio_time_reservation').is(":checked")){
	            alert("발행시간 예약");
	        }
	  		
			alert('제목: '+$('#title').val()+', 내용: '+$('#content').val()+', 카테고리: '+$('#category').val()+', 공개 설정: '+$('[name="open_type"]:checked').val()+", 발행 시간: "+$('[name="radio_time"]:checked').val());
			// 예약한 경우, 발행시간 가져와서 담기)★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
			$('#open_type').val($('[name="open_type"]:checked').val());
			$('#blog_category_no').val($('#category_select > option:selected').val());
			
			$('#write_object_frm').submit();
		});
	});
</script>
</head>

<body>
	<div class="container-fluid border-bottom">
		<div class="container">
			<div class="navbar">
				<div class="nav ms-auto">
					<button type="button" class="btn btn-outline-warning mx-1">
						<span>내 블로그</span>
							<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-text-fill" viewBox="0 0 16 16">
								<path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM5 4h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1zm-.5 2.5A.5.5 0 0 1 5 6h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zM5 8h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1zm0 2h3a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1z" />
							</svg>
					</button>

					<button type="button" class="btn btn-success" data-toggle="modal" data-target="#object_set_modal">
						<span>발행</span>
						<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-text-fill" viewBox="0 0 16 16">
							<path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM5 4h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1zm-.5 2.5A.5.5 0 0 1 5 6h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zM5 8h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1zm0 2h3a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1z" />
						</svg>
					</button>
				</div>
			</div>
		</div>
	</div>

	<div class="container-fluid bg-light h-100">
		<div class="container h-100">
			<div class="row h-100">
				<div class="col"></div>
				<div class="col-md-10">
					<form id="write_object_frm" action="${pageContext.request.contextPath}/blog/${BLOG_ID}/write" method="POST" class="h-100 d-flex flex-column">
						<input type="hidden" id="open_type" name="open_type">
						<input type="hidden" id="blog_category_no" name="blog_category_no">
						
						<div class="input-group">
							<input type="text" id="title" name="title" class="form-control p-5" placeholder="제목" style="border-top: none; border-color: #dee2e6; border-radius: 0; font-size: 32px;">
						</div>

						<div class="input-group h-100">
							<textarea id="content" name="content" class="form-control p-5" placeholder="본문을 입력해주세요." style="border-top: none; border-color: #dee2e6; border-radius: 0; resize: none;"></textarea>
						</div>
						
						<%-- <tr>
			                <td>파일첨부</td>
			                <td>
			                	<div>
			                		<input multiple="multiple" type="file" class="" id="file">
			             		</div>
			             		<div style="display:none;" id="write_file_content">
			             			<table id="write_file_content_table">
			             				<tr>
			             					<td><input type="checkbox" id="all_file_check"></td>
			             					<td>파일명</td>
			             					<td>용량</td>
			             				</tr>
			             			</table>
			             		</div>
			             		<div>
			             			<input value="삭제" type="button" id="delete_file_btn">
			             		</div>
			             	</td>
			            </tr> --%>
					</form>
				</div>
				<div class="col"></div>
			</div>
		</div>
	</div>

	<!-- 모달창 -->
	<div class="modal" id="object_set_modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modar-body px-4 py-3">
					<table>
						<tr>
							<td>카테고리</td>
							<td>
								<select id="category_select">
									<c:forEach items="${CATEGORY_LIST}" var="category">
										<option value="${category.blog_category_no}" value2="${category.is_public}"
										 <c:if test="${category.is_basic eq 1}">selected</c:if>>
											<c:if test="${category.parent_category_no ne 0}">
												ㄴ
											</c:if>
											<c:if test="${category.is_public eq 1}">
												<i class="fas fa-lock"></i>
											</c:if>
											${category.category_name}
										</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<%--
						<tr>
							<td>주제</td>
							<td></td>
						</tr>
						 --%>
						<tr>
							<td>공개 설정</td>
							<td>
								<ul>
									<li>
										<input id="open_public" type="radio" name="open_type" value="0" checked> 
										<label for="open_public">전체공개</label>
									</li>
									<li>
										<input id="open_neighbor" type="radio" name="open_type" value="1"> 
										<label for="open_neighbor">이웃공개</label>
									</li>
									<li>
										<input id="open_private" type="radio" name="open_type" value="2"> 
										<label for="open_private">비공개</label>
									</li>
								</ul>
							</td>
						</tr>
						<%-- 
						<tr>
							<td>발행 설정</td>
							<td>
								<ul>
									<li>
										<input id="publish-option-comment" name="publish-option" type="checkbox"> 
										<label for="publish-option-comment">댓글허용</label>
									</li>
									<li>
										<input id="publish-option-like" name="publish-option" type="checkbox"> 
										<label for="publish-option-like">공감허용</label>
									</li>
									<li>
										<input id="publish-option-search" name="publish-option" type="checkbox"> 
										<label for="publish-option-search">검색허용</label>
									</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<input type="checkbox" i> 
								<label>이 설정을 기본값으로 유지</label>
							</td>
						</tr>
						
						<tr>
							<td>태그 편집</td>
							<td></td>
						</tr>
						 --%>
						<tr>
							<td>발행 시간</td>
							<td>
								<ul>
									<li>
										<input id="radio_time_now" type="radio" name="radio_time" value="now" checked> 
										<label for="radio_time_now">현재</label>
									</li>
									<li>
										<input id="radio_time_reservation" type="radio" name="radio_time" value="reservation"> 
										<label for="radio_time_reservation">예약</label>
									</li>
								</ul>
								<div id="reservation_time_div" class="d-none">
									<p>
										<i class="bi bi-alarm"></i>
										<a id="time_setting_text">
											설정한 시간으로 예약 발행됩니다.
										</a>
									</p>
									<div>
										<div>
											<input type="text" readonly value="오늘 날짜">
											<div>달력 그리기</div>
										</div>
										<div>
											<select title="예약 발행 시간 선택" id="reservation_hour">
												<option value="00">00</option>
												<option value="01">01</option>
												<option value="02">02</option>
												<option value="03">03</option>
												<option value="04">04</option>
												<option value="05">05</option>
												<option value="06">06</option>
												<option value="07">07</option>
												<option value="08">08</option>
												<option value="09">09</option>
												<option value="10">10</option>
												<option value="11">11</option>
												<option value="12">12</option>
												<option value="13">13</option>
												<option value="14">14</option>
												<option value="15">15</option>
												<option value="16">16</option>
												<option value="17">17</option>
												<option value="18">18</option>
												<option value="19">19</option>
												<option value="20">20</option>
												<option value="21">21</option>
												<option value="22">22</option>
												<option value="23">23</option>
											</select>
										</div>
										<div>
											<select title="예약 발행 분 선택" id="reservation_minute">
												<option value="00">00</option>
												<option value="10">10</option>
												<option value="20">20</option>
												<option value="30">30</option>
												<option value="40">40</option>
												<option value="50">50</option>
											</select>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="modal-footer px-4 py-3">
					<button id="submit_cancel_btn" type="button" class="btn btn-outline-danger" data-dismiss="modal">
						<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check2" viewBox="0 0 16 16">
							<path d="M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z" />
						</svg>
						<span>취소</span>
					</button>
					<button id="submit_btn" type="button" class="btn btn-outline-success">
						<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check2" viewBox="0 0 16 16">
							<path d="M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z" />
						</svg>
						<span>발행</span>
					</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>