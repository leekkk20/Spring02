package com.ncs.green;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import criTest.PageMaker;
import criTest.SearchCriteria;
import service.MemberService;
import vo.MemberVO;

// ** Bean 생성하는 @
// Java : @Component
// Spring 세분화 됨
// => @Controller,  @Service,  @Repository

// ** Spring 의 redirect ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// => mv.addObject 에 보관한 값들을 쿼리스트링의 parameter로 붙여 전달해줌 
// 그러므로 전달하려는 값들을 mv.addObject 로 처리하면 편리.
// 단, 브라우져의 주소창에 보여짐.

// ** RedirectAttributes
// => Redirect 할 때 파라메터를 쉽게 전달할 수 있도록 지원하며,
// addAttribute, addFlashAttribute, getFlashAttribute 등의 메서드가 제공됨.
// => addAttribute
//   - url에 쿼리스트링으로 파라메터가 붙어 전달됨. 
//    - 그렇기 때문에 전달된 페이지에서 파라메터가 노출됨.
// => addFlashAttribute
//   - Redirect 동작이 수행되기 전에 Session에 값이 저장되고 전달 후 소멸된다.
//   - Session을 선언해서 집어넣고 사용 후 지워주는 수고를 덜어주고,
//   - url에 퀴리스트링으로 붙지 않기때문에 깨끗하고 f5(새로고침)에 영향을 주지않음.  
//   - 주의사항 
//     받는쪽 매핑메서드의 매개변수로 parameter 를 전달받는 VO가 정의되어 있으면
//     이 VO 생성과 관련된 500 발생 하므로 주의한다.
//     ( Test : JoController 의 jupdate 성공시 redirect:jdetail )
//     단, VO로 받지 않는 경우에는 url에 붙여 전달하면서 addFlashAttribute 사용가능함        

// => getFlashAttribute
//   - insert 성공 후 redirect:jlist 에서 Test (JoController, 결과는 null)
//   - 컨트롤러에서 addFlashAttribute 가 session에 보관한 값을 꺼내는것은 좀더 확인이 필요함 

// ** redirect 로 한글 parameter 전달시 한글깨짐
// => 한글깨짐이 발생하는경우 사용함.
// => url 파라메터 로 전달되는 한글값 을 위한 encoding
//   - String message = URLEncoder.encode("~~ member 가 없네용 ~~", "UTF-8");
//     mv.setViewName("redirect:mlist?message="+message);  
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

// *** JSON 제이슨, (JavaScript Object Notation) **********
// => 자바스크립트의 객체 표기법으로, 데이터를 전달 할 때 사용하는 표준형식.
// 속성(key) 과 값(value) 이 하나의 쌍을 이룸

// ** JAVA의 Data 객체 -> JSON 변환하기
// 1) GSON
// : 자바 객체의 직렬화/역직렬화를 도와주는 라이브러리 (구글에서 만듦)
// 즉, JAVA객체 -> JSON 또는 JSON -> JAVA객체

// 2) @ResponseBody (매핑 메서드에 적용)
// : 메서드의 리턴값이 View 를 통해 출력되지 않고 HTTP Response Body 에 직접 쓰여지게 됨.
// 이때 쓰여지기전, 리턴되는 데이터 타입에 따라 종류별 MessageConverter에서 변환이 이뤄진다.
// MappingJacksonHttpMessageConverter 를 사용하면 request, response 를 JSON 으로 변환
// view (~.jsp) 가 아닌 Data 자체를 전달하기위한 용도
// @JsonIgnore : VO 에 적용하면 변환에서 제외

// 3) jsonView
// => Spring 에서 MappingJackson2JsonView를 사용해서
// ModelAndView를 json 형식으로 반환해 준다.
// => 방법
// -> pom dependency추가
// -> 설정화일 xml 에 bean 등록 
// ( 안하면 /WEB-INF/views/jsonView.jsp 를 찾게되고  없으니 404 발생 )
// -> return할 ModelAndView 생성시 View_Name을 "jsonView"로 설정
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   

@Controller
public class MemberController {
	@Autowired
	MemberService service;

	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// ** Member Check List **
	// => UI checkbox를 이용하여 mapper에 반복문 적용하기
	@RequestMapping(value = "/mchecklist")
	public ModelAndView mchecklist(ModelAndView mv, MemberVO vo) {
		// 1) Check_Box 처리
		// String[] check = request.getParameterValues("check");
		// => vo 에 배열 Type의 check 컬럼을 추가하면 편리

		// 2) Service 실행
		// => 선택하지 않은경우 : selectList()
		// => 선택을 한 경우 : 조건별 검색 checkList(vo) -> 추가
		// => 조건 선택 확인 : 배열 check의 길이, null 여부 확인
		// => 배열 Type인 경우 선택하지 않으면 check=null이므로 길이 필요 없음
		
		// ** view 화면에 요청사항을 동일하게 표시되도록 하기 위해
		// 요청조건 재전달하기
		mv.addObject("check", vo.getCheck());
		
		if (vo.getCheck() != null && vo.getCheck().length > 0) {
			// 조건별 검색
			mv.addObject("banana", service.checkList(vo));
		} else {
			mv.addObject("banana", service.selectList());
		}
		mv.setViewName("member/mCheckList");
		return mv;
	} // mchecklist

	// ** Ajax Member Delete : 관리자가 회원 삭제
	@RequestMapping(value = "/axmdelete", method = RequestMethod.POST)
	public ModelAndView axmdelete(HttpServletRequest request, ModelAndView mv, MemberVO vo) {
		// 1. 요청 분석
		String id = null;
		HttpSession session = request.getSession(false);
		if (session != null && ((String) session.getAttribute("loginID")).equals("admin")) {
			if (service.delete(vo) > 0) {

				mv.addObject("code", "200"); // 성공
			} else {
				mv.addObject("code", "201"); // 실패
			}
		} else {
			// ** session 정보가 없어 삭제가 불가능한 경우
			mv.addObject("code", "202");
		} // session 확인_if_else
			// 3. 결과 처리 (Java 객체 => Javascript가
			// => 성공 & 실패 : redirect:home가 바람직하다
			// redirect:home => 웹브라우져의 주소창의 주소가 home이 표시되도록 setViewName에
		mv.setViewName("jsonView");
		return mv;
	}

	// ** Image (File) Download
	// => 전달받은 파일패스와 이름으로 File 객체를 만들어 뷰로 전달
	@RequestMapping(value = "/dnload")
	public ModelAndView dnload(HttpServletRequest request, ModelAndView mv, @RequestParam("dnfile") String dnfile) {
		// => 동일 기능 : String dnfile = request.getParameter("dnfile");
		// 1) 파일 path 확인
		// => 요청 Parameter를 확인
//		String dnfile = request.getParameter("dnfile"); // @RequestParam("dnfile") String dnfile 하면서 주석
		String realPath = request.getRealPath("/"); // deprecated Method
		String fileName = dnfile.substring(dnfile.lastIndexOf("/") + 1);
		// dnfile => resources\\uploadImage\\aaa.gif

		// => 개발중인지, 배포했는지에 따라 결정
		// => 해당 파일 File 찾기
		if (realPath.contains(".eclipse.")) // eclipse 개발환경 (배포 전)
			realPath = "C:\\MTest\\myWork\\Spring02\\src\\main\\webapp\\resources\\uploadImage\\" + fileName;
		else // 톰캣 서버에 배포 후 : 서버내에서의 위치
			realPath += "resources\\uploadImage\\" + fileName;

		System.out.println("** => " + realPath);

		// 2) 해당 파일 객체화
		File file = new File(realPath);
		mv.addObject("downloadFile", file);

		// 3) response 처리 (response의 body에 담아줌)
		// => Java File 객체 => File 정보를 response에 전달
		mv.setViewName("downloadView");

		return mv;
	} // dnload

	@RequestMapping(value = "/axmlist")
	public ModelAndView axmlist(ModelAndView mv) {
		mv.addObject("banana", service.selectList());
		mv.setViewName("/axTest/axMemberList");
		return mv;
	} // axmlist

	// ** JSON Login
	// => jsonView 적용
	@RequestMapping(value = "/jslogin", method = RequestMethod.POST)
	public ModelAndView jslogin(HttpServletRequest request, HttpServletResponse response, ModelAndView mv,
			MemberVO vo) {
		// 1) request 처리
		// => 입력한 password 보관
		// => response의 한글 처리 (Ajax 요청 결과로 Data 전송시에는 필수)
		String password = vo.getPassword();
		response.setContentType("text/html; charset=UTF-8");

		// 2) service 처리
		vo = service.selectOne(vo);
		if (vo != null) {
			// ID 는 일치 -> Password 확인
			if (vo.getPassword().equals(password)) {
				// Login 성공 -> login 정보 session에 보관, home
				request.getSession().setAttribute("loginID", vo.getId());
				request.getSession().setAttribute("loginName", vo.getName());
				mv.addObject("code", "200");
			} else {
				// Password 오류
				mv.addObject("message", "~~ Password 오류,  다시 하세요 ~~");
				mv.addObject("code", "201");
			}
		} else { // ID 오류
			mv.addObject("message", "~~ ID 오류,  다시 하세요 ~~");
			mv.addObject("code", "202");
		} // else
		mv.setViewName("jsonView");
		// servlet-context.xml => jsonView
		return mv;
	} // jslogin

	// ** id 중복 확인
	@RequestMapping(value = "/idDupCheck")
	public ModelAndView idDupCheck(HttpServletRequest request, HttpServletResponse response, ModelAndView mv,
			MemberVO vo) {
		// 1) 입력한 new ID 보관
		mv.addObject("newId", vo.getId());

		// 2) service 처리
		vo = service.selectOne(vo);

		// 3) 결과 처리
		if (vo != null) {
			// => newId 사용 불가능
			mv.addObject("isUse", "F");
		} else {
			// => newId 사용 가능
			mv.addObject("isUse", "T");
		}
		mv.setViewName("/member/idDupCheck");
		return mv;
	} // idDupCheck

	// ** Criteria PageList
	// => ver01 : Criteria cri 사용
	// => ver02(현재 사용 중) : SearchCriteria cri
	@RequestMapping(value = "/mcrilist")
	public ModelAndView mcrilist(HttpServletRequest request, HttpServletResponse response, ModelAndView mv,
			SearchCriteria cri, PageMaker pageMaker) {
		// 1) Criteria 처리
		cri.setSnoEno();

		// ** ver02
		// => SearchCriteria 사용 : searchType, keyword 자동으로 Parameter로 전달됨 => 자동 set

		// 2) 서비스처리
		// => List 처리
//		mv.addObject("banana", service.criList(cri)); // ver01
		mv.addObject("banana", service.searchList(cri)); // ver02

		// 3) View 처리 => PageMaker
		pageMaker.setCri(cri);
		pageMaker.setTotalRowsCount(service.searchCount(cri)); // ver02 : 조건과 일치하는 Rows 개수
		mv.addObject("pageMaker", pageMaker);
		mv.setViewName("/member/mCriList");
		return mv;
	} // mcrilist
		// =====================================================================================================

	@RequestMapping(value = "/mlist")
	public ModelAndView mlist(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		// MemberList
		List<MemberVO> list = new ArrayList<MemberVO>();
		list = service.selectList();
		if (list != null) {
//		request.setAttribute("banana", list); // ModelAndView => 아래로 변경
			mv.addObject("banana", list);
		} else {
			request.setAttribute("message", "~~ 출력 자료가 없습니다 ~~"); // ModelAndView => 아래로 변경
			mv.addObject("message", "~~ 출력 자료가 없습니다 ~~");
		}
		mv.setViewName("/member/memberList");
//	return "/member/memberList"; // ModelAndView => 아래로 변경
		return mv;
	} // mlist
		// =====================================================================================================================================================

	@RequestMapping(value = "/mdetail")
	public ModelAndView mdetail(HttpServletRequest request, HttpServletResponse response, MemberVO vo,
			ModelAndView mv) {
		// => Mapping 메서드 : 매개변수로 지정된 객체에 request_ParameterName과 일치하는 컬럼(setter) 존재하면
		// 자동으로 set
		// => 위에 MemberVO vo를 매개변수로 넣으면,
		// => 아래 vo.setID(request.getParameter("id")) 필요 없어짐

		// 1. 요청 분석
		// => 요청 구분
		// - 로그인 후 내 정보 보기 : session.getAttribute....
		// - 관리자가 memberList에서 선택 : getParameter...

		HttpSession session = request.getSession(false);
		if (vo.getId() == null || vo.getId().length() < 1) {
			// => parameter id의 값이 없으면 session에서 가져옴
			if (session != null && session.getAttribute("loginID") != null)
				vo.setId((String) session.getAttribute("loginID"));
			else {
				request.setAttribute("message", "=> 출력할 id가 없음, Login 후 이용하세요");

				mv.setViewName("home");
				return mv;
			}
		} // getParameter_else

		String uri = "/member/memberDetail";

		// 2. Service 처리
		// => Service에서 selectOne
		vo = service.selectOne(vo);
		if (vo != null) {

			// ** Update 요청이면 updateForm.jsp로
			// => 주의 : parameter "jCode" 없는 경우에는 null return
			// (NullPointException 발생하지 않도록 주의)

			// ** Update 요청이면 updateForm.jsp로
			// => PasswordEncoder 사용 후에는
			// session에 보관해놓은 raw_password를 수정할 수 있도록 vo에 set 해줌
			if ("U".equals(request.getParameter("jCode"))) {
				uri = "/member/updateForm";
				vo.setPassword((String) session.getAttribute("loginPW"));
			}
			mv.addObject("apple", vo);
		} else { // 없는 ID
			mv.addObject("message", "***" + request.getParameter("id") + "님의 자료는 ");
		}
		mv.setViewName(uri);
		return mv;
	} // mdetail
		// ============================================================================================================================================

	// ** Login & Logout
	@RequestMapping(value = "/loginf")
	public ModelAndView loginf(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
//	return "/member/loginForm";
		mv.setViewName("/member/loginForm");
		return mv;
	} // loginf
		// ==============================================================================================================================================

	@RequestMapping(value = "/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		// 1) request 처리
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		MemberVO vo = new MemberVO();
		String uri = "/member/loginForm";

		// 2) service 처리
		vo.setId(id);
		vo = service.selectOne(vo);
		if (vo != null) {
			// ID 는 일치 -> Password 확인
			// => password 암호화 이전
//			if (vo.getPassword().equals(password)) { // 암호화 이전 code
			if (passwordEncoder.matches(password, vo.getPassword())) { // 암호화 이후 code
				// true => Login 성공 -> login 정보 session에 보관, home
				request.getSession().setAttribute("loginID", id);
				request.getSession().setAttribute("loginName", vo.getName());

				// ** BCryptPasswordEncoder 로 암호화되면 복호화가 불가능함.
				// => password 수정 을 별도로 처리해야 함.
				// => 그러나 기존의 update Code 를 활용하여 updateForm.jsp 에서 수정을 위해
				// User가 입력한 raw_password 를 보관함.
				// => 이 session에 보관한 값은 detail 에서 "U" 요청시 사용함.
				request.getSession().setAttribute("loginPW", password);

				uri = "home";
			} else {
				// Password 오류
//			request.setAttribute("message", "~~ Password 오류,  다시 하세요 ~~");
				mv.addObject("message", "~~ Password 오류,  다시 하세요 ~~");
			}
		} else { // ID 오류
//		request.setAttribute("message", "~~ ID 오류,  다시 하세요 ~~");
			mv.addObject("message", "~~ ID 오류,  다시 하세요 ~~");
		} // else
//	return uri;
		mv.setViewName(uri);
		return mv;
	} // login
		// =============================================================================================================================================

	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		// ** session 인스턴스 정의 후 삭제하기
		// => 매개변수: 없거나, true, false
		// => false : session 이 없을때 null 을 return;
		HttpSession session = request.getSession(false);
		if (session != null)
			session.invalidate();
//	String uri="home";
//	request.setAttribute("message", "~~ 로그아웃 되었습니다 ~~");
		mv.addObject("message", "~~ 로그아웃 되었습니다 ~~");
		mv.setViewName("home");
//	return uri;
		return mv;
	} // logout
		// =============================================================================================================================================

	// ** Join : 회원가입
	@RequestMapping(value = "/joinf")
	public ModelAndView joinf(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		mv.setViewName("/member/joinForm");
		return mv;
	} // joinf

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	// => 매핑 네임과 method가 일치하는 요청만 처리함
	public ModelAndView join(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, MemberVO vo)
			throws IOException {
		// 1. 요청 분석
		// => Service 준비
		// => request, 한글 처리
		// => Spring : 한글(web.xml => Filter), request(매핑메서드의 매개변수 선언으로 처리)
		// => 매개변수에 MemberVO vo 추가
		// => Join 성공 : 로그인 유도 loginForm
		// => Join 실패 : joinForm
		String uri = "/member/loginForm";

		// ** MultipartFile ***********************
		// => MultipartFile 타입의 uploadfilef 의 정보에서
		// upload된 image 화일과 화일명을 get 처리,
		// => upload된 image 화일은 서버의 정해진 폴더 (물리적위치)에 저장 하고, -> file1
		// => 이 위치에 대한 정보를 table에 저장 (vo의 UploadFile 에 set) -> file2
		// ** image 화일명 중복시 : 나중 이미지로 update 됨.

		// ** Image 물리적위치 에 저장
		// 1) 현재 웹어플리케이션의 실행 위치 확인 :
		// => eslipse 개발환경 (배포전)
		// D:\MTest\MyWork\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\Spring01\
		// => 톰캣서버에 배포 후 : 서버내에서의 위치가 됨
		// D:\MTest\IDESet\apache-tomcat-9.0.41\webapps\Spring01\
		String realPath = request.getRealPath("/"); // deprecated Method
		System.out.println("** realPath => " + realPath);

		// 2) 위 의 값을 이용해서 실제저장위치 확인
		// => 개발중인지, 배포했는지 에 따라 결정
		if (realPath.contains(".eclipse.")) // eslipse 개발환경 (배포전)
			realPath = "C:\\MTest\\myWork\\Spring02\\src\\main\\webapp\\resources\\uploadImage\\";
		else // 톰캣서버에 배포 후 : 서버내에서의 위치
			realPath += "resources\\uploadImage\\";

		// ** 폴더 만들기 (File 클래스활용)
		// => 위의 저장경로에 폴더가 없는 경우 (uploadImage가 없는경우) 만들어 준다
		File f1 = new File(realPath);
		if (!f1.exists())
			f1.mkdir();
		// => realPath 디렉터리가 존재하는지 검사 (uploadImage 폴더 존재 확인)
		// 존재하지 않으면 디렉토리 생성

		// ** 기본 이미지 지정하기
		String file1, file2 = "resources/uploadImage/basicman1.jpg";

		// ** MultipartFile
		// => 업로드한 파일에 대한 모든 정보를 가지고 있으며 이의 처리를 위한 메서드를 제공한다.
		// -> String getOriginalFilename(),
		// -> void transferTo(File destFile),
		// -> boolean isEmpty()

		MultipartFile uploadfilef = vo.getUploadfilef(); // file의 내용 및 파일명 등 전송된 정보들
		if (uploadfilef != null && !uploadfilef.isEmpty()) {

			// ** Image를 선택함 -> Image저장 ( 경로_realPath + 화일명 )
			// 1) 물리적 저장경로에 Image저장
			file1 = realPath + uploadfilef.getOriginalFilename(); // 경로완성
			uploadfilef.transferTo(new File(file1)); // Image저장

			// 2) Table 저장 준비
			file2 = "resources/uploadImage/" + uploadfilef.getOriginalFilename();
		}
		// ** 완성된 경로 vo 에 set
		vo.setUploadfile(file2);

		// *** PasswordEncoder (암호화 적용하기)
		// => BCryptPasswordEncoder 적용
		// encode(rawData) -> digest 생성 & vo 에 set
		vo.setPassword(passwordEncoder.encode(vo.getPassword()));

		// 2. Service 처리
		if (service.insert(vo) > 0) {
			mv.addObject("message", "*** 회원가입 성공!!! 로그인 후 이용하세요~ ***");
		} else {
			mv.addObject("message", "*** 회원가입 실패ㅠㅠ 다시 시도하세요!!! ***");
			uri = "/member/joinForm";
		}

		// 3. 결과(ModelAndView) 처리
//		mv.setViewName("/member/join");
		mv.setViewName(uri);
		return mv;
	} // joinf
		// ====================================================================================================================

	// ** Update : 내 정보 수정하기
	@RequestMapping(value = "/mupdate", method = RequestMethod.POST)
	public ModelAndView mupdate(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, MemberVO vo)
			throws IOException {
		// 1. 요청 분석
		// => Update 성공 : 내 정보 표시 => memberDetail.jsp
		// => Update 실패 : 재수정 유도 => updateForm.jsp
		String uri = "/member/memberDetail";
		mv.addObject("apple", vo);
		// => Update 성공 / 실패 모두 출력시 필요하므로
		// => Image 추가 후에는 Update 성공 후에는 uploadfile의 값이 변경될 수 있으므로
		// 성공 후에는 재처리 해야함

		// ** Image Update 추가
		// ** Image 물리적위치에 저장
		String realPath = request.getRealPath("/"); // deprecated Method

		// 2) 위 의 값을 이용해서 실제저장위치 확인
		// => 개발중인지, 배포했는지 에 따라 결정
		if (realPath.contains(".eclipse.")) // eslipse 개발환경 (배포전)
			realPath = "C:\\MTest\\myWork\\Spring02\\src\\main\\webapp\\resources\\uploadImage\\";
		else // 톰캣서버에 배포 후 : 서버내에서의 위치
			realPath += "resources\\uploadImage\\";

		// 1.2) 폴더 만들기 (File 클래스활용)
		// => 위의 저장경로에 폴더가 없는 경우 (uploadImage가 없는 경우) 만들어 준다
		File f1 = new File(realPath);
		if (!f1.exists())
			f1.mkdir();

		// 1.3) 기본 이미지 지정하기
		String file1, file2 = "resources/uploadImage/basicman1.jpg";

		// 1.4) MultipartFile : file은 저장, 저장된 경로는 vo에 set
		// => 새 파일 선택했으면 : uploadfilef 처리
		// => 새 파일 선택 안 했으면 : uploadfilef 처리 없이 uploadfile 사용
		MultipartFile uploadfilef = vo.getUploadfilef(); // file의 내용 및 파일명 등 전송된 정보들
		if (uploadfilef != null && !uploadfilef.isEmpty()) {

			// ** Image를 선택함 -> Image저장 ( 경로_realPath + 화일명 )
			// 1.4.1) 물리적 저장경로에 Image저장
			file1 = realPath + uploadfilef.getOriginalFilename(); // 경로완성
			uploadfilef.transferTo(new File(file1)); // Image저장

			// 1.4.2) Table 저장 준비
			file2 = "resources/uploadImage/" + uploadfilef.getOriginalFilename();
			vo.setUploadfile(file2);
		}
		// ** new_Image 를 선택하지 않은 경우
		// => form 에서 전송되어 vo 에 담겨진 uploadfile 값을 사용하면 됨.

		// *** PasswordEncoder (암호화 적용하기)
		// => update에 적용하기 전에
		// - login : loginPW session에 보관
		// - detail : updateForm에 raw_password가 출력되도록 수정함
		// => BCryptPasswordEncoder 적용
		// encode(rawData) -> digest 생성 & vo 에 set
		vo.setPassword(passwordEncoder.encode(vo.getPassword()));

		// 2. Service 처리
		if (service.update(vo) > 0) {
			mv.addObject("message", "*** 회원 정보 수정 성공!!! ***");
			mv.addObject("apple", vo);
		} else {
			mv.addObject("message", "*** 회원 정보 수정 실패ㅠㅠ 다시 시도하세요 ***");
			uri = "/member/updateForm";
		}

		// 3. 결과(ModelAndView) 전달
		mv.setViewName(uri);
		return mv;

	} // update
		// ===============================================================================================

	// ** Delete : 회원 탈퇴
	@RequestMapping(value = "/mdelete")
	public ModelAndView mdelete(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, MemberVO vo,
			RedirectAttributes rttr) {
		// 1. 요청 분석
		// => Delete 성공 : session 무효화, 홈 화면으로 이동 => home.jsp
		// => Delete 실패 : message 표시 => home.jsp
		// 삭제 대상 확인 작업(삭제 대상이 로그인 된 아이디인지 확인 필요)
		// => loginID 또는 관리자가 삭제하는 경우 request.getParameter

		String id = null;
		HttpSession session = request.getSession(false);
		// => 메서드 내에서 session을 사용 가능하도록 정의
		// 삭제 성공 후 session 무효화
		if (session != null && session.getAttribute("loginID") != null) {
			id = (String) session.getAttribute("loginID");
			// => 본인 탈퇴 : loginID 이용한 삭제
			// 그러나 id는 관리자가 아니어야 함.
			// - 관리자 작업인 경우 : 이미 vo에 삭제할 ID가 set 되어있음
			// - 관리자 작업이 아닌 경우 : session에서 get한 ID를 vo에 set
			if (!"admin".equals(id))
				vo.setId(id);

			// 2. Service 처리
			if (service.delete(vo) > 0) {
				// 성공 : session 무효화, home.jsp
				rttr.addFlashAttribute("message", "*** 회원 탈퇴 성공!!! ***");
				// 본인이 탈퇴하는 경우에만 session 무효화
				if (!"admin".equals(id))
					session.invalidate();
			} else {
				rttr.addFlashAttribute("message", "*** 회원 탈퇴 실패 ㅠㅠ 다시 하세요");
			} // Service
		} else {
			// ** session 정보가 없어 삭제가 불가능한 경우
			// => session is null
			rttr.addFlashAttribute("message", "*** 삭제할 id 없음, 로그인 후 이용하세요");
		} // session 확인_if_else
//		
//		
//
//		if (vo.getId() == null || vo.getId().length() < 1) {
//			// => 본인이 직접 탈퇴
//			// => parameter id의 값이 없고 session에서 가져옴
//			if (session != null && session.getAttribute("loginID") != null) {
//				id = ((String) session.getAttribute("loginID"));
//				vo.setId(id);
//			} else {
//				rttr.addFlashAttribute("message", "=> 삭제할 id가 없음, Login 후 이용하세요");
//			}
//		} else
//			// => 관리자가 삭제하는 경우
//			id = vo.getId(); // getParameter_else
//
////		String uri = "home";
//
//		// 2. Service 처리
//		if (service.delete(vo) > 0) {
//			// 성공 : session 무효화, home.jsp
//			rttr.addFlashAttribute("message", "*** 회원 탈퇴 성공!!! ***");
//			// 본인이 탈퇴하는 경우에만
//			if (!"admin".equals(id))
//				session.invalidate();
//		} else {
//			rttr.addFlashAttribute("message", "*** 회원 탈퇴 실패ㅠㅠ 다시 시도하세요 ***");
//		}
//
		// 3. 결과(ModelAndView) 전달
		// => 성공 & 실패 : redirect:home가 바람직하다
		// redirect:home => 웹브라우져의 주소창의 주소가 home이 표시되도록 setViewName에
		mv.setViewName("redirect:home");
		return mv;
	}
} // class
