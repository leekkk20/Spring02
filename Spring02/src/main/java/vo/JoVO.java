package vo;

import lombok.Data;

// ** VO 작성
// => private 변수 : Table의 컬럼명과 동일
// => setter, getter, toString()

@Data
public class JoVO extends MemberVO {
	private int jno;
	private String chief;
	private String jname;
	private String note;
	
} // class
