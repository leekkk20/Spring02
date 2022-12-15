<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** MemberList Spring_MVC2 **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>
	<h2>** MemberList Spring_MVC2 **</h2>
	<c:if test="${not empty message}">
				${message}<br>
	</c:if>
	<hr>
	<br>
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
			<th>Image</th>
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
					<td><img src="${member.uploadfile}" width="50" height="60"></td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
	<br>
	<hr>
	&nbsp;&nbsp;
	<a href="javascript:history.go(-1)">이전으로</a> &nbsp;&nbsp;
	<a href="home">[HOME]</a>
</body>
</html>