<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** LoginForm Spring_MVC2 **</title>
<link rel="stylesheet" type="text/css"
	href="resources/myLib/myStyle.css">
<script src="resources/myLib/jquery-3.2.1.min.js"></script>
<script src="resources/myLib/inCheck.js"></script>
<script src="resources/myLib/axTest01.js"></script>
<script>
	let iCheck = false;
	let pCheck = false;

	$(function() {
		$('#id').focus();

		$('#id').keydown(function(e) {
			// ** ID
			if (e.which == 13) {
				e.preventDefault();
				// => form 에 submit 이 있는경우
				// => enter 누르면 자동 submit 발생되므로 이를 제거함
				$('#password').focus();
			}
		}).focusout(function() {
			iCheck = idCheck();
		}); //id

		$('#password').keydown(function(e) {
			if (e.which == 13) {
				e.preventDefault();
				$('#password2').focus();
			}
		}).focusout(function() {
			pCheck = pwCheck();
		}); //password
	});

	function inCheck() {
		// => 무결성 확인결과 submit 판단 & 실행
		// => 모든 항목에 오류가 없으면 : submit -> return true 또는 default
		//    하나의 항목이라도 오류가 있으면 : submit 취소 -> return false	 
		if (iCheck == false) {
			$('#iMessage').html(' id를 확인하세요 !!');
		}
		if (pCheck == false) {
			$('#pMessage').html(' Password를 확인하세요 !!');
		}

		if (iCheck && pCheck) {
			return true; // submit 진행
		} else
			return false;
	} //inCheck
</script>
</head>
<body>
	<h2>** LoginForm Spring_MVC2 **</h2>

	<form action="login" method="post">
		<table>
			<tr>
				<td bgcolor="PaleTurquoise" style="text-align: center;">I D</td>
				<td><input type="text" name="id" id="id" value="admin"><br>
					<span id="iMessage" class="eMessage"></span></td>
			</tr>
			<tr>
				<td bgcolor="PaleTurquoise " style="text-align: center;">Password</td>
				<td><input type="password" name="password" id="password"
					value="12345!"> <span id="pMessage" class="eMessage"></span></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Login"
					onclick="return inCheck()">&nbsp;&nbsp; 
					<input type="reset"	value="Reset">&nbsp;&nbsp;
					<span id="axlogin" class="textLink">AxLogin</span>
					<span id="axlogin" class="textLink">JsLogin</span>
				</td>
			</tr>
		</table>
	</form>
	<br>
	<span id="message"></span>
	<c:if test="${not empty message}">
		<hr>
${message}<br>
	</c:if>
	<hr>
	&nbsp;&nbsp;
	<a href="javascript:history.go(-1)">이전으로</a> &nbsp;&nbsp;
	<a href="home">[Home]</a>
</body>
</html>