package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import criTest.Criteria;
import criTest.SearchCriteria;
import mapperInterface.BoardMapper;
import vo.BoardVO;
import vo.MemberVO;

// ** interface 자동완성
// => Alt + Shift + T
// => 또는 마우스우클릭 PopUp Menu 의  Refactor - Extract Interface...

@Service
public class BoardServiceImpl implements BoardService {
//	BoardDAO dao; // Mapper로 교체

	@Autowired
	BoardMapper mapper;
	// ** Mybatis 적용 (interface 방식)
	// => interface BoardMapper 를 통해서
	// BoardMapper.xml 의 SQL구문 접근

	// => @Autowired
//	= new BoardDAO(); => @Repository

	// ** Board Check List
	@Override
	public List<BoardVO> checkList(SearchCriteria cri) {
		return mapper.checkList(cri);
	}

	@Override
	public int checkCount(SearchCriteria cri) {
		return mapper.checkCount(cri);
	}

	// ** Board Check List
	@Override
	public List<BoardVO> checkList(BoardVO vo) {
		return mapper.checkList(vo);
	}

	// ** Criteria PageList
	@Override // ver02
	public List<BoardVO> searchList(SearchCriteria cri) {
		return mapper.searchList(cri);
	}

	public int searchCount(SearchCriteria cri) {
		return mapper.searchCount(cri);
	}

	// ** Criteria PageList
	@Override
	public List<BoardVO> criList(Criteria cri) {
		return mapper.criList(cri);
	}

	public int criTotalCount() {
		return mapper.criTotalCount();
	}

	// ** selectList
	@Override
	public List<BoardVO> selectList() {
		return mapper.selectList();
	}

	// ** selectOne
	@Override
	public BoardVO selectOne(BoardVO vo) {
		return mapper.selectOne(vo);
	}

	@Override
	public int insert(BoardVO vo) {
		return mapper.insert(vo);
	}

	@Override
	public int update(BoardVO vo) {
		return mapper.update(vo);
	}

	@Override
	public int delete(BoardVO vo) {
		return mapper.delete(vo);
	}

	// ** countUp
	@Override
	public int countUp(BoardVO vo) {
		return mapper.countUp(vo);
	}

	@Override
	public int rinsert(BoardVO vo) {
		int result = mapper.rinsert(vo);
		if (result > 0)
			System.out.println("** stepUpdate Count => " + mapper.stepUpdate(vo));
		else
			result = 0;
		return result;
	}
}
// class