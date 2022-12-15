package mapperInterface;

import java.util.List;

import criTest.Criteria;
import criTest.SearchCriteria;
import vo.BoardVO;

public interface BoardMapper {

	List<BoardVO> checkList(SearchCriteria cri);

	int checkCount(SearchCriteria cri);

	// ** Member Check List
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

	// ** insert : 새 글 등록x
	int insert(BoardVO vo);

	// ** Update : 글 수정
	int update(BoardVO vo);

	// ** Delete
	int delete(BoardVO vo);

	// ** 조회수 증가
	int countUp(BoardVO vo);

	// ** 답글 등록
	int rinsert(BoardVO vo);

	//
	int stepUpdate(BoardVO vo);
}
