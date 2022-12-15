package com.ncs.green;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import criTest.SearchCriteria;
import service.JoService;
import service.MemberService;
import vo.JoVO;

@Controller
public class JoController {
	@Autowired
	JoService service;
	@Autowired
	MemberService mservice;

	// ** JoList
	@RequestMapping(value = "/jolist")
	public ModelAndView jolist(HttpServletRequest request, HttpServletResponse response, JoVO vo, ModelAndView mv, RedirectAttributes rttr) {
		
		// ** RedirectAttributes의 addFlashAttribute로 전달된 값 확인
		// => insert에서 rttr.addFlashAttribute("mytest", "addFlashAttribute 메서드 Test");
		System.out.println("************ Test => " + rttr.getFlashAttributes());
		System.out.println("************ Test => " + request.getSession().getAttribute("mytest"));
		
		List<JoVO> list = new ArrayList<JoVO>();
		list = service.selectList();
		if (list != null) {
			mv.addObject("banana", list);
		} else {
			mv.addObject("message", "~~ 출력 자료가 없습니다 ~~");
		}
		mv.setViewName("/jo/joList");
		return mv;
	} // jolist
		// =====================================================================================================================================================

	// ** BoardDetail
	// => 글 내용 확인, 수정 화면 요청시 jCode=U&seq=....
	// => 조회수 증가
	// - 증가 조건 : 글 보는 이(loginID)와 글쓴이가 다를 때 && jCode != U
	// - 증가 메서드 : DAO, Service에 countUp 메서드 추가
	// - 증가 시점 : selectOne 성공 후dhs

	// ** JoDetail
	// => 아래쪽에 조원 목록 출력
	// => memjo Table에서 조건 검색 jno=#{jno} => banana
	@RequestMapping(value = "/jodetail", method=RequestMethod.GET)
	public ModelAndView jodetail(HttpServletRequest request, HttpServletResponse response, JoVO vo, ModelAndView mv, SearchCriteria cri) {
		
		// ** 수정 성공 후 redirect 요청으로 전달된 경우 message 처리 **********************************
		if (request.getParameter("message") != null && request.getParameter("message").length() > 0)
			mv.addObject("message", request.getParameter("message"));
		System.out.println("****** Jno 전달 확인 ****** => " + vo.getJno());
		System.out.println("****** Jname 한글 전달 확인 ****** => " + vo.getJname());
		
		// 1. 요청분석
		String uri = "/jo/joDetail";

		// 2. Service 처리
		vo = service.selectOne(vo);
		if (vo != null) {
			// 2.1) 조회수 증가
			if ("U".equals(request.getParameter("jCode")))
				uri = "/jo/joupdateForm";
			else { 
				// 조원 목록 읽어오기
				// => 조별로 조회가 가능한 searchList 메서드를 활용
				// 1 Page만 있으면 되므로 기본값을 지정함
				// 단, RowsPerPage는 현재 Paging을 하지 않기 때문에 큰 값을 지정함
				cri.setRowsPerPage(30); // 현재 Paging은 하지 않기 때문에 큰 값을 지정
				cri.setCurrPage(1);
				cri.setSnoEno();
				cri.setKeyword(Integer.toString(vo.getJno()));
				cri.setSearchType("j");
				mv.addObject("banana", mservice.searchList(cri));
			}
			// 2.3) 결과 전달
			mv.addObject("apple", vo);
		} else
			mv.addObject("message", "***" + request.getParameter("jname") + "의 자료가 없습니다 ***");

		mv.setViewName(uri);
		return mv;
	} // jodetail
		// ============================================================================================================================================

	// ** Insert : 새 글 등록
	@RequestMapping(value = "/joinsertf")
	public ModelAndView joinsertf(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		mv.setViewName("/jo/joinsertForm");
		return mv;
	} // joinsertf

	@RequestMapping(value = "/joinsert", method = RequestMethod.POST)
	// => 매핑 네임과 method가 일치하는 요청만 처리함
	public ModelAndView joinsert(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, JoVO vo,
			RedirectAttributes rttr) {
		// 1. 요청 분석
		// => insert 성공 : jlist (redirect 요청, message 전달)
		// => insert 실패 : jinsertForm.jsp
		String uri = "redirect:jolist";

		// 2. Service 처리
		if (service.insert(vo) > 0) {
			rttr.addFlashAttribute("message", "*** 새 그룹 등록 성공!!! ***");
			rttr.addFlashAttribute("mytest", "addFlashAttribute 메서드 Test");
		} else {
			mv.addObject("message", "*** 그룹 등록 실패ㅠㅠ 다시 시도하세요!!! ***");
			uri = "/jo/joinsertForm";
		}

		// 3. 결과(ModelAndView) 전달
		mv.setViewName(uri);
		return mv;
	} // joinsert
		// ====================================================================================================================

	// ** Update : 조 수정하기
	@RequestMapping(value = "/joupdate", method = RequestMethod.POST)
	public ModelAndView joupdate(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, JoVO vo) {
		// 1. 요청 분석
		// => Update 성공 : => boardDetail.jsp
		// => Update 실패 : 재수정 유도 => joupdateForm.jsp
//		String uri = "/jo/joDetail";
//		String uri = "redirect:jdetail?jno="+vo.getJno(); // ver01
		String uri = "redirect:jodetail"; // ver02
		
		// ** Spring의 redirect
		// => mv.addObject에 보관한 값들을 쿼리스트링의 parameter로 붙여 전달해줌
		// 그러므로 전달하려는 값들을 mv.addObject로 처리하면 편리
		// 단, 브라우져의 주소창에 보여짐
		
		// 단, 위처럼 redirect에서 parameter를 사용하여 전달하면서 RedirectAttributes rttr 사용시 오류 발생
		// jdetail 메서드의 매개변수에서 vo로 전달된 parameter를 받는 경우에 오류 발생함
		// vo로 받지 않는 경우에는 쿼리스트링으로 전달하면서 RedirectAttributes rttr 사용 가능함
		// ** RedirectAttributes : Redirect 할 때 파라미터를 쉽게 전달할 수 있도록 지원함
		// => addAttribute : URL에 파라미터가 붙어 전달되게 된다.
		// 그렇기 때문에 전달된 페이지에서 파라미터가 노출됨
		// => addFlashAttribute : Redirect 동작이 수행되기 전에 Session에 값이 저장되고 전달 후 소멸
		// Session을 선언해서 집어넣고 사용 후 지워주는 수고를 덜어준다
		// (insert 성공 후 redirect:jlist에서 Test)
		
		mv.addObject("apple", vo);
		// => Update 성공/실패 모두 출력시 필요하므로

		// 2. Service 처리
		if (service.update(vo) > 0) {
			mv.addObject("jno", vo.getJno()); // String uri를 ver02로 바꾸면서 추가
			mv.addObject("jname", vo.getJname());
			mv.addObject("message", "*** 정보 수정 성공!!! ***");
		} else {
			mv.addObject("message", "*** 정보 수정 실패ㅠㅠ 다시 시도하세요 ***");
			uri = "/jo/joupdateForm";
		}

		// 3. 결과(ModelAndView) 전달
		mv.setViewName(uri);
		return mv;
	} // joupdate
		// ===============================================================================================

	// ** Delete : 글 삭제
	@RequestMapping(value = "/jodelete")
	public ModelAndView jodelete(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, JoVO vo,
			RedirectAttributes rttr) {
		// 1. 요청 분석
		// => Delete 성공 : blist
		// => Delete 실패 : bdetail
		String uri = "redirect:jolist";

		// 2. Service 처리
		if (service.delete(vo) > 0) {
			rttr.addFlashAttribute("message", "*** 그룹 삭제 성공!!! ***");
		} else {
			rttr.addFlashAttribute("message", "*** 그룹 삭제 실패 ㅠㅠ 다시 하세요 ***");
			uri = "redirect:jodetail?jno=" + vo.getJno();
		} // Service

		// 3. 결과(ModelAndView) 전달
		mv.setViewName(uri);
		return mv;
	} // jodelete
		// ============================================================================================================================

}
