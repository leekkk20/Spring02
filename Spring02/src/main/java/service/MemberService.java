package service;

import java.util.List;

import criTest.SearchCriteria;
import vo.MemberVO;

public interface MemberService {

	// ** Member Check List
	List<MemberVO> checkList(MemberVO vo);
	
	// ** SearchCriteria PageList
	List<MemberVO> searchList(SearchCriteria cri);
	
	int searchCount(SearchCriteria cri);
	
	// ** selectList
	List<MemberVO> selectList();

	// ** selectOne
	MemberVO selectOne(MemberVO vo);

	// ** Insert
	int insert(MemberVO vo);

	// ** Update
	int update(MemberVO vo);

	// ** Delete
	int delete(MemberVO vo);

}