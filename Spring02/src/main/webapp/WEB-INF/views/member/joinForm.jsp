<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** MemberDetail Spring_MVC2 **</title>
<link rel="stylesheet" type="text/css"
	href="resources/myLib/myStyle.css">
<script src="resources/myLib/jquery-3.2.1.min.js"></script>
<script src="resources/myLib/inCheck.js"></script>
<script src="resources/myLib/axTest01.js"></script>
<script>
	// ** id 중복 확인 **
	// => UI 개선사항
	// => 중복확인 버튼 추가
	//		처음 : 중복확인_enable / submit_disable
	// => 중복확인 완료 후 submit가 가능하도록
	//		중복확인_disable / submit_enable
	// => 중복확인 기능 : function idDupCheck()
	//		id 확인 요청 => 서버로 전송 => id, selectOne결과 => response: 사용 가능/불가능
	// => 서버측 : 컨트롤러에 idDupCheck 요청을 처리하는 매핑 메서드, view 작성
	function idDupCheck() {
		// 1) id 무결성 확인
		if (iCheck == false) {
			iCheck = idCheck();
		} else {
			// 2) 서버로 확인 요청 => 결과는 새 창으로 처리
			let url = 'idDupCheck?id=' + $('#id').val();
			window.open(url, '_blank', 'width=400, height=300, resizable=yes, scrollbars=yes, toolbar=no, menubar=yes');
		}

	} // idDupCheck
	// ----------------------------------------------------------------------------------------------------------

	// ** 입력값 무결성 점검 **
	// 1) 전역변수 선언
	// => 개별적 오류 확인을 위한 switch 변수
	let iCheck = false;
	let pCheck = false;
	let ppCheck = false;
	let nCheck = false;
	let fCheck = false;
	let bCheck = false;
	let poCheck = false;

	// 2) 개별적 focusout 이벤트 핸들러 작성 : JQuery
	// => 이벤트: focusout() 과 enterKey -> keydown()
	// => val()전달 -> 무결성 Check
	// => message -> Tag 아랫쪽에 출력
	// => JS 외부문서(inCheck.js) 에 각 항목별 Check 구문 function 으로 작성
	$(function() {
		$('#id').focus();

		// ** enterKey 누르면 다음으로 이동
		// => 이벤트리스너 : keydown 사용
		// => 이벤트 핸들러인 익명함수의 첫번째 매개변수는 이벤트 객체를 전달,
		//    이 이벤트 객체는 발생된 이벤트에 대한 정보를 제공,
		//    이들중에 누른 key 의 값을 알려주는 속성이 e.which 이고,
		//    enterKey 의 코드 값은 13 (Ascii Code 표 참고)
		// => 자동적으로 focusout 이벤트 발생하게됨

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

		$('#password2').keydown(function(e) {
			if (e.which == 13) {
				e.preventDefault();
				$('#name').focus();
			}
		}).focusout(function() {
			ppCheck = pw2Check();
		}); //password2

		$('#name').keydown(function(e) {
			if (e.which == 13) {
				e.preventDefault();
				$('#info').focus();
			}
		}).focusout(function() {
			nCheck = nmCheck();
		}); //name

		// ** Info(길이(length)로 입력 여부만 확인)
		$('#info').keydown(function(e) {
			if (e.which == 13) {
				e.preventDefault();
				$('#birthday').focus();
			}
		}).focusout(function() {
			fCheck = foCheck();
		}); // info

		$('#birthday').keydown(function(e) {
			if (e.which == 13) {
				e.preventDefault();
				$('#jno').focus();
			}
		}).focusout(function() {
			bCheck = bdCheck();
		}); //birthday

		// ** Age : 정수 체크
		$('#age').keydown(function(e) {
			if (e.which == 13) {
				e.preventDefault();
				$('#point').focus();
			}
		}).focusout(function() {
			aCheck = agCheck();
		}); // age

		// ** Point : 실수 체크
		$('#point').keydown(function(e) {
			if (e.which == 13) {
				e.preventDefault();
				$('#submit').focus();
			}
		}).focusout(function() {
			poCheck = ptCheck();
		}); //point

	}); //ready

	// 3) submit 판단 & 실행 :  JS submit
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
		if (ppCheck == false) {
			$('#ppMessage').html(' Password2를 확인하세요 !!');
		}
		if (nCheck == false) {
			$('#nMessage').html(' Name을 확인하세요 !!');
		}
		if (fCheck == false) {
			$('#fMessage').html('info를 확인하세요 !!');
		}
		if (bCheck == false) {
			$('#bMessage').html(' Birthday 를 확인하세요 !!');
		}
		if (aCheck == false) {
			$('#aMessage').html('Age를 확인하세요 !!');
		}
		if (poCheck == false) {
			$('#poMessage').html(' Point 를 확인하세요 !!');
		}

		if (iCheck && pCheck && ppCheck && nCheck && fCheck && bCheck && aCheck & poCheck) {
			// => submit 확인
			if (confirm("~~ 정말 가입 하십니까 ? (Yes:확인 / No:취소)") == false) {
				alert('~~ 가입이 취소 되었습니다 ~~');
				return false;
			} else
				return true; // submit 진행
		} else
			return false;

	} //inCheck
</script>
</head>
<body>
	<!-- 
   ** FileUpLoad Form **
   => Form과 Table Tag의 사용 시 주의사항 : form 내부에 table 사용해야 함.
      -> form 단위 작업 시 인식 안 됨.
      -> JQ의 serialize, FormData의 append all 등
      
   => method="post" : 255byte 이상 대용량 전송 가능하므로
   => enctype="multipart/form-data" : 파일 upload를 가능하게 해줌.
      ** multipart/form-data는 파일업로드가 있는 입력 양식요소에 사용되는 enetype 속성의 값 중 하나이고,
         multipart는 form-data가 여러 부분으로 나뉘어 서버로 전송되는 것을 의미.
         이 폼이 제출될 때 이 형식을 서버에 알려주며,
         multipart/form-data로 지정이 되어있어야 서버에서 정상적으로 데이터를 처리할 수 있다.
 -->
	<h2>** MemberJoin Spring_MVC2</h2>
	<form action="join" method="post" id="myForm"
		enctype="multipart/form-data">
		<table>
			<tr height="40">
				<td bgcolor="LightBlue">I D</td>
				<td><input type="text" name="id" id="id" size="20"
					placeholder="id는 영문, 숫자 10자 이내">
					<button type="button" id="idDup" onclick="idDupCheck()">ID
						중복확인</button> <br> <span id="iMessage" class="eMessage"></span></td>
			</tr>
			<tr height="40">
				<td bgcolor="LightBlue">Password</td>
				<td><input type="password" name="password" id="password"
					size="20" placeholder="특수문자 반드시 포함" value="12345!"><br>
					<span id="pMessage" class="eMessage"></span></td>
			</tr>
			<tr height=40>
				<td bgcolor="LightBlue"><label for=password>PW 확인</label></td>
				<td><input type="password" name=password2 id=password2
					size="20"><br> <span id="ppMessage" class="eMessage"></span></td>
			</tr>
			<tr height="40">
				<td bgcolor="LightBlue">Name</td>
				<td><input type="text" name="name" id="name"><br>
					<span id="nMessage" class="eMessage"></span></td>
			</tr>
			<tr height="40">
				<td bgcolor="LightBlue">Info</td>
				<td><input type="text" name="info" id="info"><br>
					<span id="fMessage" class="eMessage"></span></td>
			</tr>
			<tr height="40">
				<td bgcolor="LightBlue">Birthday</td>
				<td><input type="date" name="birthday" id="birthday"><br>
					<span id="bMessage" class="eMessage"></span></td>
			</tr>
			<tr height="40">
				<td bgcolor="LightBlue">Jno</td>
				<td><select name="jno" id="jno">
						<option value="1">1:unique</option>
						<option value="2">2:천지창조</option>
						<option value="3">3:3조</option>
						<option value="4">4:4조</option>
						<option value="5">5:do가자</option>
						<option value="6">6:김고정</option>
						<option value="9" selected>9:관리자</option>
				</select></td>
			</tr>
			<tr height="40">
				<td bgcolor="LightBlue">Age</td>
				<td><input type="text" name="age" id="age"
					placeholder="정수를 입력하세요"><br> <span id="aMessage"
					class="eMessage"></span></td>
			</tr>
			<tr height="40">
				<td bgcolor="LightBlue">Point</td>
				<td><input type="text" name="point" id="point"><br>
					<span id="poMessage" class="eMessage"></span></td>
			</tr>

			<tr height="40">
				<td bgcolor="LightBlue">Image</td>
				<td><img src="" class="select_img"><br> <input
					type="file" name="uploadfilef" id="uploadfilef"> <script>
						// 해당 파일의 서버상의 경로를 src로 지정하는것으로는 클라이언트 영역에서 이미지는 표시될수 없기 때문에
						// 이를 해결하기 위해 FileReader이라는 Web API를 사용
						// => 이 를 통해 url data를 얻을 수 있음.
						//    ( https://developer.mozilla.org/ko/docs/Web/API/FileReader)
						// ** FileReader
						// => 웹 애플리케이션이 비동기적으로 데이터를 읽기 위하여 읽을 파일을 가리키는File
						//    혹은 Blob 객체를 이용해 파일의 내용을(혹은 raw data버퍼로) 읽고 
						//    사용자의 컴퓨터에 저장하는 것을 가능하게 해줌.   
						// => FileReader.onload 이벤트의 핸들러.
						//    읽기 동작이 성공적으로 완료 되었을 때마다 발생.
						// => e.target : 이벤트를 유발시킨 DOM 객체
						// => type="file" 은 복수개의 파일을 업로드 할 수 있도록 설계됨
						//    그러므로 files[] 배열 형태의 속성을 가짐
						$('#uploadfilef').change(function() {
							if (this.files && this.files[0]) {
								var reader = new FileReader;
								reader.readAsDataURL(this.files[0]);
								reader.onload = function(e) {
									$(".select_img").attr("src", e.target.result).width(100).height(100);
								} // onload_function
							} // if   
						}); //change
					</script></td>
			</tr>
			<tr height="40">
				<td></td>
				<td><input type="submit" value="가입" id="submit"
					onclick="return inCheck()" disabled>&nbsp;&nbsp; <input
					type="reset" value="취소"> <span id="axjoin"
					class="textLink">AxJoin</span>&nbsp; &nbsp;</td>
		</table>
	</form>
	<c:if test="${not empty message}">
		<hr>
	<${message}<br>
	</c:if>
	<hr>
	<br> &nbsp;&nbsp;
	<a href="javascript:history.go(-1)">이전으로</a> &nbsp;&nbsp;
	<a href="home">[HOME]</a>
</body>
</html>