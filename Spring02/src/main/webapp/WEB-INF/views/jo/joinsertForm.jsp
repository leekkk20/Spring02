<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Jo Insert Spring_MVC2 **</title>
<link rel="stylesheet" type="text/css"
	href="resources/myLib/myStyle.css">
</head>
<body>
	<h2>** Jo Insert Spring_MVC2 **</h2>
	<form action="joinsert" method="post">
		<table>
			<tr height="40">
				<th bgcolor="LemonChiffon">Jno</th>
				<td><input type="text" name="jno" id="jno" size="20"
					placeholder="숫자 5글자 이내"></td>
			</tr>
			<tr height="40">
				<th bgcolor="LemonChiffon">Chief</th>
				<td><input type="text" name="chief" size="20"
					placeholder="영문 5글자 이상 입력"></td>
			</tr>
			<tr height="40">
				<th bgcolor="LemonChiffon">Jname</th>
				<td><input type="text" name="jname" id="jname" size="20"></td>
			</tr>
			<tr height="40">
				<th bgcolor="LemonChiffon">Note</th>
				<td><input type="text" name="note" id="note" size="20"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="등록">&nbsp; &nbsp; <input
					type="reset" value="취소"></td>
			</tr>
		</table>
	</form>
	<c:if test="${not empty message}">
		<hr>
	<${message}<br>
	</c:if>
	<hr>
	<br> &nbsp;&nbsp;
	<a href="jolist">JoList</a> &nbsp;&nbsp;
	<a href="javascript:history.go(-1)">이전으로</a> &nbsp;&nbsp;
	<a href="home">[HOME]</a>
</body>
</html>