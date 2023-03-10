<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** BoardList Spring_MVC2 **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>
	<h2>** BoardList Spring_MVC2 **</h2>
	<br>
	<c:if test="${not empty message}">
				${message}<br>
	</c:if>
	<hr>
	<table width=100%
		style="line-height: 120%; font-weight: bold; font-size: 20px;"
		border="3">
		<tr bgcolor="black" style="text-align: center; color: white;">
			<th>Seq</th>
			<th>Title</th>
			<th>I D</th>
			<th>RegDate</th>
			<th>조회수</th>
		</tr>
		<c:if test="${not empty banana}">
			<c:forEach var="board" items="${banana}">
				<tr>
					<td>${board.seq}</td>
					<td>
						<!-- 답글 등록후 indent 에 따른 들여쓰기 => 답글인 경우에만 적용  --> 
						<c:if test="${board.indent > 0}">
							<c:forEach begin="1" end="${board.indent}">
								<span>&nbsp;&nbsp;</span>
							</c:forEach>
							<span style="color: blue">re...</span>
						</c:if> 
						<!-- 로그인 하는 경우에만 title 클릭시 content 출력
						=> bDetail을 실행함 --> 
						<c:if test="${not empty loginID}">
							<a href="bdetail?seq=${board.seq}">${board.title}</a>
						</c:if> <c:if test="${empty loginID}">${board.title}</c:if>
					</td>
					<td><c:if test="${loginID=='admin'}">
							<a href="mdetail?id=${board.id}">${board.id}</a>
						</c:if> <c:if test="${loginID!='admin' }">${board.id}</c:if></td>

					<td>${board.regdate}</td>
					<td>${board.cnt}</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
	<hr>
	<c:if test="${not empty loginID}">
		&nbsp;&nbsp;<a href="binsertf">새글등록</a>
	</c:if>
	&nbsp;&nbsp;
	<a href="javascript:history.go(-1)">이전으로</a> &nbsp;&nbsp;
	<a href="home">[HOME]</a>
</body>
</html>