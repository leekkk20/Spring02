<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** BoardVODetail Spring_MVC2 **</title>
<link rel="stylesheet" type="text/css"
	href="resources/myLib/myStyle.css">
</head>
<body>
	<h2>** BoardVODetail Spring_MVC2 **</h2>
	=> Request Board_id : ${param.id}
	<br>
	<hr>
	<c:if test="${not empty apple}">
		<table>
			<tr height="40">
				<td bgcolor="Khaki" style="text-align: center;">Seq</td>
				<td>${apple.seq}</td>
			</tr>
			<tr height="40">
				<td bgcolor="Khaki" style="text-align: center;">I D</td>
				<td>${apple.id}</td>
			</tr>
			<tr height="40">
				<td bgcolor="Khaki" style="text-align: center;">Title</td>
				<td>${apple.title}</td>
			</tr>
			<tr height="40">
				<td bgcolor="Khaki" style="text-align: center;">Content</td>
				<td><textarea rows="5" cols="50" readonly>${apple.content}</textarea></td>
			</tr>
			<tr height="40">
				<td bgcolor="Khaki" style="text-align: center;">RegDate</td>
				<td>${apple.regdate}</td>
			</tr>
			<tr height="40">
				<td bgcolor="Khaki" style="text-align: center;">조회수</td>
				<td>${apple.cnt}</td>
			</tr>
		</table>
	</c:if>
	<c:if test="${not empty message}">
		<hr>
	<${message}<br>
	</c:if>
	<hr>
	<c:if test="${loginID==apple.id || loginID=='admin'}">
		&nbsp;&nbsp;
		<a href="bdetail?jCode=U&seq=${apple.seq}">[게시글 수정]</a>
		&nbsp;&nbsp;
		<a href="bdelete?seq=${apple.seq}&root=${apple.root}">[게시글 삭제]</a>
		<!-- root 추가 : 삭제시 원글 삭제인지 답글 삭제인지 확인 가능 -->
	</c:if>
	<c:if test="${not empty loginID}">
	&nbsp;&nbsp;
		<a
			href="rinsertf?root=${apple.root}&step=${apple.step}&indent=${apple.indent}">[답글
			달기]</a>
	</c:if>
	<br>
	<br> &nbsp;&nbsp;
	<a href="blist">boardList</a> &nbsp;&nbsp;
	<a href="javascript:history.go(-1)">이전으로</a> &nbsp;&nbsp;
	<a href="home">[HOME]</a>
</body>
</html>