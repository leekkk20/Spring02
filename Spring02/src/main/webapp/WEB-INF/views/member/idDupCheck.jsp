<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** ID 중복 확인 **</title>
<link rel="stylesheet" type="text/css"
	href="resources/myLib/myStyle.css">
<script src="resources/myLib/jquery-3.2.1.min.js"></script>
<script src="resources/myLib/inCheck.js"></script>
<script>
	// ** 사용자가 입력한 id 를 사용가능하도록 해주고, 현재(this)창은 close
	// 1) this 창의 id 를 부모창의 id 로
	// 2) 부모창의 ID중복확인 버튼은 disable & submit 은 enable
	// 3) 부모창의 id 는 수정불가 (readonly) , password 에 focus
	// 4) 현재(this)창은 close

	function idOK() {
		// 1.
		// opener.document.getElementById('id').value='${newId}';
		// => <script>에서 EL은 문자열 Type 내부에서 사용 가능함
		opener.$('#id').val('${newId}');

		// 2.
		// ** JS :
		// opener.document.getElementById('submit').disabled = '';
		// opener.document.hetElementById('idDup').disabled = 'disabled';

		// ** JQ :
		// => attr, prop 비교
		// => attr()는 HTML의 속성(Attribute), 기능, 입력된 값을 취급
		// => prop()는 JavaScript DOM 객체의 프로퍼티(Property), 실제값, property가 가지는 본연의 값

		opener.$('#submit').attr('disabled', ''); // 적용X
		opener.$('#submit').attr('disabled', false); // 적용O
		opener.$('#idDup').attr('disabled', 'disabled');

		// opener.$('#submit').prop('disabled', false);
		// opener.$('#idDup').prop('disabled', true); // 위와 같음

		// 3.
		// => id가 확정되었으므로 수정 불가 => readonly
		// => attr, prop 비교
		// opener.$('#id').attr('readonly', 'readonly'); // 아래와 같음
		opener.$('#id').prop('readonly', ture);
		opener.$('#password').focus();

		// 4.
		window.close();
		// close(), self.close(); this.close(); => this는 되는지 확인 필요

	} // idOK
</script>
<style>
body {
	background-color: LightYellow;
	font-family: 맑은고딕;
}

#wrap {
	margin-left: 0;
	text-align: center;
}

h3 {
	color: navy;
}
</style>
</head>
<body>
	<div id="Wrap">
		<h2>** ID 중복 확인 **</h2>
		<form action="idDupCheck" method="get">
			User_ID : <input type="text" name="id" id="id">&nbsp; <input
				type="submit" value="ID 중복확인" onclick="return idCheck()"><br>
			<!-- inCheck.js의 idCheck() 결과에 따라 submit 결정 -->
			<span id="iMessage" class="eMessage"></span>
		</form>
		<br> <br>
		<hr>
		<br> <br>
		<!-- ** 서버의 확인 결과에 따른 처리 영역
		=> isUse : 'T' 가능 / 'F' 불가능 -->
		<div id="msgBlock">
			<c:if test="${isUse=='T'}">
				${newId}는 사용 가능합니다.&nbsp;&nbsp;
				<input type="button" value="Id OK" onclick="idOK()">
				<!-- 사용자가 입력한 id를 사용 가능하도록 해주고, 현재(this)창은 close -->
			</c:if>

			<c:if test="${isUse=='F'}">
				${newId}는 사용중인 아이디입니다.<br>
				다시 입력하세요!!!
				<!-- 부모창(joinForm, opener)에 남아있는 사용자가 입력한 id는 지워주고,
					현재(this)창에서는 id에 focus를 주고 재입력 유도 => script 필요 -->
				<script>
					$('#id').focus();
					opener.document.getElementById('id').value = ''; // id에 있는 값 삭제됨
				</script>
			</c:if>
		</div>

	</div>
</body>
</html>