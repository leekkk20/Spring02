/**
 *  ** AjaxTest 02
   => 반복문에 이벤트 적용하기
   => axmlist : id별로 board조회, 관리자기능 (delete 버튼), Image(File)Download
   => axblist : 상세글 보기
 */

$(function() {
	// ** Ajax_MemberList
	$('#axmlist').click(function() {
		$.ajax({
			type: 'Get',
			url: 'axmlist',
			success: function(resultPage) {
				$('#resultArea1').html(resultPage);
			},
			error: function() {
				$('#resultArea1').html('*** 서버 오류 !!! 잠시 후 다시 하세요 !!! ***');
			}
		}); // ajax
	}); // axmlist_click

	// ** Ajax_BoardList
	$('#axblist').click(function() {
		$.ajax({
			type: 'Get',
			url: 'axblist',
			success: function(resultPage) {
				$('#resultArea1').html(resultPage);
			},
			error: function() {
				$('#resultArea1').html('*** 서버 오류 !!! 잠시 후 다시 하세요 !!! ***');
			}
		}); // ajax
	}); // axblist_click

	// ** 반복문에 이벤트 적용하기 2) => JQuery : reaydy 안에
	// => id별로 board 조회(요청 id 인식)
	$('.ccc').click(function() {
		// 1) 요청 id 인식
		let id = $(this).html();
		// $(this).text()
		// JS : innerHTML, innerTEXT
		console.log('*** id => ' + id)

		// 2) ajax 처리
		$.ajax({
			type: 'Get',
			url: 'aidblist',
			data: {
				keyword: id // ajax 요청시 id값을 keyword로 지정하면 자동으로 ser cri되므로 생략 가능
			},
			success: function(resultPage) {
				$('#resultArea2').html(resultPage);
			},
			error: function() {
				$('#resultArea2').html('*** 서버 오류 !!! 잠시 후 다시 하세요 !!! ***');
			}
		}); // ajax
	}); // ccc_click

	$('.ddd').click(function() {
		// 1) 요청 id 인식
		// => 이벤트가 일어난 Tag의 id 속성의 값으로 보관
		let id = $(this).attr('id');
		// id='11111'; // ErrorTest Data
		console.log('*** id => ' + id);
		$('#resultArea2').html('');

		// 2) ajax 처리
		$.ajax({
			type: 'Post',
			url: 'axmdelete',
			data: {
				id: id
			},
			success: function(resultData) {
				// ** jaonView 처리 결과
				// => 성공: span의 컨텐츠를 Deleted, click_event를 off
				if (resultData.code == 200) {
					alert('*** 삭제 성공 ***');
					// $(this).html('Deleted');
					// => ajax 처리 단계이므로 이미 this는 변경되어 click시의 this가 아님
					// 그러므로 this로는 불가능, id 속성은 가능
					$('#' + id).html('Deleted')
						.css({
							color: 'Gray',
							fontWeight: 'bold'
						}).off();
					// 이벤트 제거(적용됨), removeClass는 적용 안됨
				} else {
					alert('** 삭제 실패 **');
				};
			},
			error: function() {
				$('#resultArea2').html('*** 서버 오류 !!! 잠시 후 다시 하세요 !!! ***');
			}
		}); // ajax
	}); // ddd_click


}); // ready

// ** 반복문에 이벤트 적용하기
// => id별로 board 조회
// test 1) JS function
function aidBList(id) {
	$.ajax({
		type: 'Get',
		url: 'aidblist',
		data: {
			keyword: id // ajax 요청시 id값을 keyword로 지정하면 자동으로 ser cri되므로 생략 가능
		},
		success: function(resultPage) {
			$('#resultArea2').html(resultPage);
		},
		error: function() {
			$('#resultArea2').html('*** 서버 오류 !!! 잠시 후 다시 하세요 !!! ***');
		}
	}); // ajax
} // aidBList

// => Delete
function amDelete(id) {

	$.ajax({
		type: 'Get',
		url: 'axmdelete',
		data: { id: id },
		success: function(resultData) {
			// Delete 결과 처리

			if (resultData.code == '200') {
				alert('삭제가 성공적으로 처리되었습니다. ~~ ');
				// span 의 컨텐츠를 Deleted, click_event 를 off
				/* 
				$(this).html('Deleted'); 
				ajax 처리 단계이므로 이미 this는 변경되어 click 시의 this 가 아님 
				=> 그러므로 id 활용 */
				$('#' + id).html('Deleted')   //  $('#admin')
					.css({
						color: 'Gray',
						fontgWeight: 'bold'
					}).off().attr('onclick', null);
				// JQuery 의 이벤트제거 이므로 여기서 off()는 적용안됨
				// => 그러므로 이 경우에는 onclick 속성의 값을 삭제 해야함. 
			} else if (resultData.code == '201') {
				$('#resultArea2').html('');
				alert('~~ Delete Sql 오류 !! 잠시후 다시 하세요 ~~');
			} else {
				$('#resultArea2').html('');
				alert('~~ 관리자 로그인 정보 없음!!  다시 하세요 ~~');
			} //else

		}, // seccess
		error: function() {
			alert('~~ 서버오류 !! 잠시후 다시 하세요 ~~');
		}
	}); //ajax
} //amDelete 


