<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Ajax Test Main Form **</title>
<link rel="stylesheet" type="text/css"
	href="resources/myLib/myStyle.css">
<script src="resources/myLib/jquery-3.2.1.min.js"></script>
<script src="resources/myLib/axTest01.js"></script>
<script src="resources/myLib/axTest02.js"></script>

</head>
<body>
	<h2>** Ajax Test Main Form **</h2>
	<c:if test="${not empty loginID}">
	=> ${loginName}님 안녕하세요 !!! <br>
	</c:if>
	<hr>
	&nbsp; &nbsp;
	<span id="axloginf" class="textLink">AxLoginF</span>&nbsp; &nbsp;
	<span id="axjoinf" class="textLink">AxJoinF</span>&nbsp; &nbsp;
	<span id="axmlist" class="textLink">AxMList</span>&nbsp; &nbsp;
	<span id="axblist" class="textLink">AxBList</span>&nbsp; &nbsp;
	<a href="home">[Home]</a>
	<br>
	<hr>
	<br>
	<div id="resultArea1"></div>
	<div id="resultArea2"></div>










</body>
</html>