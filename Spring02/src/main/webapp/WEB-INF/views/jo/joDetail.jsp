<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** MemberJoin Spring_MVC2 **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>
	<h2>** JoDetail Spring_MVC2 **</h2>
	<br>
	<hr>
	<c:if test="${not empty apple}">
		<table>
			<tr height="40">
				<td bgcolor="lavender" style="text-align: center;">Jno</td>
				<td>${apple.jno}</td>
			</tr>
			<tr height="40">
				<td bgcolor="lavender" style="text-align: center;">Chief</td>
				<td>${apple.chief}</td>
			</tr>
			<tr height="40">
				<td bgcolor="lavender" style="text-align: center;">Jname</td>
				<td>${apple.jname}</td>
			</tr>
			<tr height="40">
				<td bgcolor="lavender" style="text-align: center;">Note</td>
				<td>${apple.note}</td>
			</tr>
		</table>
	</c:if>
		<hr>
	<h3>** ${apple.jno} Jo MemberLIst **</h3>
		<table width=100%
		style="text-align: center; line-height: 120%; font-weight: bold; font-size: 20px;"
		border="3">
		<tr style="color:white" bgcolor="black">
			<th>ID</th>
			<th>Password</th>
			<th>Name</th>
			<th>info</th>
			<th>Birthday</th>
			<th>Jno</th>
			<th>Age</th>
			<th>Point</th>
		</tr>
		<c:if test="${not empty banana}">
			<c:forEach var="member" items="${banana}">
				<tr>
					<td><c:if test="${loginID=='admin'}">
							<a href="mdetail?id=${member.id}">${member.id}</a>
						</c:if> <c:if test="${loginID!='admin' }">${member.id}</c:if></td>
					<td>${member.password}</td>
					<td>${member.name}</td>
					<td>${member.info}</td>
					<td>${member.birthday}</td>
					<td>${member.jno}</td>
					<td>${member.age}</td>
					<td>${member.point}</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
	
	<c:if test="${not empty message}">
	<${message}<br>
	</c:if>
	<hr>

	<c:if test="${not empty loginID}">
	<br>&nbsp;&nbsp;
	<a href="jodetail?jCode=U&jno=${apple.jno}">[조 수정]</a>
	<!-- memberList에서 요청한 경우는 session에 보관된 id로는 수정할 수 없기 때문에 parameter로 처리함 -->
	&nbsp;&nbsp;<a href="jodelete?jno=${apple.jno}">[조 삭제]</a>
	</c:if>
	<br>
	<br> &nbsp;&nbsp;
	<a href="jolist">JoList</a> &nbsp;&nbsp;
	<br><br> &nbsp;&nbsp;
	<a href="jinsertf">[새 그룹 등록하기]</a>
	&nbsp;&nbsp;&nbsp;
	<a href="javascript:history.go(-1)">이전으로</a> &nbsp;&nbsp;
	<a href="home">[HOME]</a>
</body>
</html>