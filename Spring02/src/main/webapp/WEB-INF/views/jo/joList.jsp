<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** JoList Spring_MVC2 **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>
	<h2>** JoList Spring_MVC2 **</h2>
	<c:if test="${not empty message}">
				${message}<br>
	</c:if>
	<hr>
	<br>
	<table width=100%
		style="text-align: center; line-height: 120%; font-weight: bold; font-size: 20px;"
		border="3">
		<tr style="color:white" bgcolor="black">
			<th>Jno</th>
			<th>Chief</th>
			<th>C_Name</th>
			<th>Jname</th>
			<th>Note</th>
		</tr>
		<c:if test="${not empty banana}">
			<c:forEach var="memjo" items="${banana}">
				<tr>
					<td><c:if test="${loginID=='admin'}">
							<a href="jodetail?jno=${memjo.jno}">${memjo.jno}</a>
						</c:if> <c:if test="${loginID!='admin'}">${memjo.jno}</c:if></td>
					<td><c:if test="${loginID=='admin'}">
							<a href="mdetail?id=${memjo.chief}">${memjo.chief}</a>
						</c:if> <c:if test="${loginID!='admin'}">${memjo.chief}</c:if></td>
					<td>${memjo.name}</td>
					<td>${memjo.jname}</td>
					<td>${memjo.note}</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
	<br>
	<hr>
	&nbsp;&nbsp;
	<a href="joinsertf">조 등록</a> &nbsp;&nbsp;
	<a href="javascript:history.go(-1)">이전으로</a> &nbsp;&nbsp;
	<a href="home">[HOME]</a>
</body>
</html>