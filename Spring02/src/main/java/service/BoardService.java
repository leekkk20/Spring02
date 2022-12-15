package service;

import java.util.List;

import criTest.Criteria;
import criTest.SearchCriteria;
import vo.BoardVO;

public interface BoardService {

	// ** Board Check List
	List<BoardVO> checkList(SearchCriteria cri);

	int checkCount(SearchCriteria cri);

	List<BoardVO> checkList(BoardVO vo);

	// => ver02
	List<BoardVO> searchList(SearchCriteria cri);

	int searchCount(SearchCriteria cri);

	// => ver01
	// ** Criteria PageList
	List<BoardVO> criList(Criteria cri);

	int criTotalCount();

	// ** selectList
	List<BoardVO> selectList();

	// ** selectOne
	BoardVO selectOne(BoardVO vo);

	// ** Insert
	int insert(BoardVO vo);

	// ** Update
	int update(BoardVO vo);

	// ** Delete
	int delete(BoardVO vo);

	// ** countUp
	int countUp(BoardVO vo);

	// ** Rinsert
	int rinsert(BoardVO vo);

}