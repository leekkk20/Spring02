<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Home</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>
<h1>Hello Spring !!!</h1>
<P>  The time on the server is ${serverTime}. </P>
<hr>
<c:if test="${not empty loginID}">
=> ${loginName} 님 안녕하세요 !!! <br>
</c:if>
<c:if test="${not empty message}">
=> ${message}<br>
</c:if>
<hr>
<img alt="main" src="resources/images/tulips.png" width="300" height="200">
<hr>
   <!-- Login 전 -->
<c:if test="${empty loginID}">
   &nbsp; &nbsp;<a href="loginf">[LoginF]</a>
   &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<a href="joinf">[JoinForm]</a>
</c:if>
      
   <!-- Login 후 -->
<c:if test="${not empty loginID}">
   &nbsp; &nbsp;<a href="logout">[Logout]</a><br><br>
   
   &nbsp; &nbsp;<a href="mdetail">[내 정보 보기]</a>
   &nbsp;&nbsp; &nbsp;<a href="mdetail?jCode=U">[내 정보 수정]</a>
   &nbsp;&nbsp; &nbsp;<a href="mdelete">[회원 탈퇴]</a>
</c:if>
   <br><br>
   &nbsp;&nbsp;&nbsp;<a href="mlist">[MemberList]</a>
   &nbsp;&nbsp; &nbsp;<a href="blist">[BoardList]</a>
   &nbsp;&nbsp; &nbsp;<a href="jlist">[JoList]</a><br>
   <br>
   &nbsp;&nbsp;&nbsp;<a href="mcrilist">[MCriList]</a>
   &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<a href="bcrilist">[BCriList]</a><br>
   <br>
   &nbsp;&nbsp;&nbsp;<a href="axTestForm">[AjaxTest]</a>
   &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<a href="bcrypt">[Bcrypt]</a>
   &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<a href="calendarMain">[FullCalendar]</a><br>
   <br>
   &nbsp;&nbsp;&nbsp;<a href="mchecklist">[MCheck]</a>
   &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<a href="bchecklist">[BCheck]</a><br>
   <br>
   &nbsp;&nbsp;&nbsp;<a href="greensn">[GreenSN]</a>
   &nbsp; &nbsp; &nbsp; &nbsp; <a href="greenall">[GreenAll]</a>
   &nbsp; &nbsp; &nbsp;&nbsp;<a href="jeju">[Jeju]</a>
   &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<a href="gps">[GPS]</a><br>
   <br>
   &nbsp;&nbsp;&nbsp;<a href="echo">[WS_Echo]</a>
   &nbsp; &nbsp; &nbsp; &nbsp; <a href="chat">[WS_Chat]</a><br>
   <br>
   &nbsp;&nbsp;&nbsp;<a href="etest">[Exception]</a><br>
   <br>
   &nbsp;&nbsp;&nbsp;<a href="log4jTest">[Log4j_Test]</a><br>
   <br><br><br><br>

</body>
</html>
