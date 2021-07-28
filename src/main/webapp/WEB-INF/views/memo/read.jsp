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
	<table>
		<tr>
			<td>${MEMO.is_important}</td>
			<td>${MEMO.title}</td>
			<td>${MEMO.date}</td>
		</tr>
		<tr>
			<td>보낸사람</td>
			<td colspan="2">
				<c:if test="${empty MEMO.writer_name}">
					${MEMO.writer_id}
				</c:if>
				<c:if test="${!empty MEMO.writer_name}">
					${MEMO.writer_name}
				</c:if>
			</td>
		</tr>
		<tr>
			<td>받는사람</td>
			<td colspan="2">
				<c:if test="${empty MEMO.receiver_name}">
					${MEMO.receiver_id}
				</c:if>
				<c:if test="${!empty MEMO.receiver_name}">
					${MEMO.receiver_name}
				</c:if>
			</td>
		</tr>
		<tr>
			<td colspan="3">
				${MEMO.content}
			</td>
		</tr>
	</table>
</body>
</html>