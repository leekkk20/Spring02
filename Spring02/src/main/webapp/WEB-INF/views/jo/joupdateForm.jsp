<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** JoUpdate Spring_MVC2 **</title>
<link rel="stylesheet" type="text/css"
	href="resources/myLib/myStyle.css">
</head>
<body>
	<form action="joupdate" method="post">
		<table>
			<tr height="40">
				<th bgcolor="GreenYellow">Jno</th>
				<td><input type="text" name="jno" id="jno" size="20"
					value="${apple.jno}" readonly></td>
			</tr>
			<!-- ** input Tag 입력(수정) 막기
			=> disabled : 서버로 전송 안됨
			=> readonly : 서버로 전송됨 -->
			<tr height="40">
				<th bgcolor="GreenYellow">Chief</th>
				<td><input type="text" name="chief" value="${apple.chief}"
					size="20"></td>
			</tr>
			<tr height="40">
				<th bgcolor="GreenYellow">Jname</th>
				<td><input type="text" name="jname" value="${apple.jname}"></td>
			</tr>
			<tr height="40">
				<th bgcolor="GreenYellow">Note</th>
				<td><input type="text" name="note" value="${apple.note}"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="수정">&nbsp;&nbsp;&nbsp;
					<input type="reset" value="취소"></td>
			</tr>

		</table>
	</form>
	<c:if test="${not empty message}">
		<hr>
	<${message}<br>
	</c:if>
	<hr>
	<br> &nbsp;&nbsp;&nbsp;
	<a href="jlist">[JoList]</a>
	<br> &nbsp;&nbsp;
	<a href="javascript:history.go(-1)">이전으로</a> &nbsp;&nbsp;
	<a href="home">[HOME]</a>
</body>
</html>