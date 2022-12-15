package vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

// ** VO 작성
// => private 변수 : Table의 컬럼명과 동일
// => setter , getter , toString()

// ** Lombok
// setter, getter, toString, 생성자 등을 자동으로 만들어줌
// 코드를 줄이고 가독성을 높여 편리하지만, 장.단점이 있어 충분히 고려 후 사용해야 한다.  
// => 모든 필드의  public setter 와  getter 를 사용하는 일반적인 경우 유용하며, 
// => 보안을 위해 setter 와  getter 의 접근 범위를 지정해야 하는 경우 등
// => 대규모의 프로젝트에서 다양한 setter 와  getter code를 작성하는 경우에는 충분히 고려해야함. 

// => @Data 즉, 다음 애너테이션을 모두 한번에 처리 한다.
// => @Getter(모든 필드) : getter 생성하도록 지원
// => @Setter(모든 필드-final로 선언되지 않은) : setter를 생성하도록 지원
// => @ToString :  모든 필드를 출력하는 toString() 메소드 생성 

@Data
// 정의된 모든 필드에 대한
// Getter, Setter, ToString과 같은 모든 요소를 한번에 만들어
public class MemberVO {
	private String id;
	private String password;
	private String name;
	private String info;
	private String birthday;
	private int jno;
	private int age;
	private double point;
	private String uploadfile;
	// => Table에 저장 및 view 처리를 위한 필드(저장값 : 경로 및 파일명)
	// => Table의 컬럼명과 일치
	private MultipartFile uploadfilef;
	// UI_form의 Upload_Image 정보를 전달받기 위한 필드
	// MultipartFile(Interface) -> CommonsMultipartFile
	// UI의 Tag의 name 속성과 일치
	
	private int[] check;
	   // ** 배열타입 (CheckBox 처리) 
	   // => 배열타입 검색조건 처리
	   // ** 전송 자료가 {'A','B','C'} 가정하면
	   // => Sql 
	   // where lev='A' or lev='B' or lev='C' ..
	   // where lev in ('A','B','C')

} // class
