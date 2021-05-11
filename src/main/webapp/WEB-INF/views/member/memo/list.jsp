<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="nav_snb" class="" style="width: 251px;">
		<div class="btn_workset">
			<a href="${pageContext.request.contextPath}/memo/write" class="">
				<strong class=""><span>쪽지쓰기</span></strong>
			</a>
			<a href="#" class="">
				<strong class=""><span>내게쓰기</span></strong>
			</a>
		</div>
		
		<div class="section_typemail">
			<h3 class="btdnd">쪽지 모아보기</h3>
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
				<h2 class="btdnd">메일함</h2>
				<div class="tdst_menu">
					<ul>
						<td class="">
                            <span class="item_wrap bu1">
                                <i class="ico_mbox"></i><a href="#" title="전체 쪽지">받은쪽지함</a>
                                <a id="btnArrange" href="#" class=""><span>정리하기</span></a>
                            </span>
                            <span>
                            	<i class="ico_mbox"></i><a href="#" class="" title="개인 쪽지">개인쪽지</a>
                                <a href="#" class="article">
                                	<span class="btdnd">안읽은 쪽지 수</span>
                                	<em class="">125</em>
                               	</a>
                               	<i class="ico_mbox"></i><a href="#" class="" title="카페 단체">카페단체</a>
                                <a href="#" class="article">
                                	<span class="btdnd">안읽은 쪽지 수</span>
                                	<em class="">3</em>
                               	</a>
                            </span>
						</td>
						<td id="" class="">
                            <span class="">
                                <i class="ico_mbox"></i><a href="#" class="" title="보낸 쪽지함">보낸쪽지함</a>
                                <a id="btnArrange" href="#" class=""><span>내게쓰기</span></a>
                            </span>
						</td>
						<td id="" class="">
                            <span class="">
                                <i class="ico_mbox"></i><a href="#" class="" title="내게 쓴 쪽지함">내게쓴쪽지함</a>
                                <a id="btnArrange" href="#" class=""><span>수신확인</span></a>
                            </span>
						</td>
						<td id="" class="">
                            <span class="">
                                <i class="ico_mbox"></i><a href="#" class="" title="쪽지 보관함">쪽지보관함</a>
                            </span>
						</td>
						<td id="" class="">
                            <span class="">
                                <i class="ico_mbox"></i><a href="#" class="" title="스팸 쪽지함">스팸쪽지함</a>
                                <a id="btnArrange" href="#" class=""><span>비우기</span></a>
                            </span>
						</td>
						<td id="" class="">
                            <span class="">
                                <i class="ico_mbox"></i><a href="#" class="" title="휴지통">휴지통</a>
                                <a href="#" class="article">
                                	<span class="btdnd">안읽은 쪽지 수</span>
                                	<em class="">10</em>
                               	</a>
                                <a id="btnArrange" href="#" class=""><span>비우기</span></a>
                            </span>
						</td>
               
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div>
		<table>
			<c:forEach var="memo" items="${MEMOLIST}">
			<tr>
				<c:if test="${memo.box==2}"><td>발신</td></c:if>
				<c:if test="${memo.box==0}"><td>개인</td></c:if>
				<td><input type="checkbox"></td>
				<td>${memo.is_important}</td>
				<td>${memo.is_read}</td>
				<td>${memo.writer_id}</td>
				<td><a href="${pageContext.request.contextPath}/memo/read?mn=${memo.memo_no}">${memo.title}</a></td>
				<td>${memo.date}</td>
				<td>${memo.memo_size}</td>
			</tr>
			</c:forEach>
		</table>
	</div>			
				
			
</body>
</html>