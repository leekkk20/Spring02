package javaTest;

import static org.junit.Assert.*;

import org.junit.Test;

// ** Book class
// => 맴버필드 3개 정의, 맴버필드 3개를 초기화 하는 생성자를 만드세요~
// => 접근 범위는 default

class Book {
	String author;
	String title;
	int price;

	Book(String author, String title, int price) { // 맴버필드 3개 정의
		this.author = author;
		this.title = title;
		this.price = price;
		// 맴버필드 3개 초기화
	}

	public boolean isBook(Boolean b) {
		return b;
	}

} // Book

// ** @ 종류
// => @Before - @Test - @After
// => @ 적용 메서드 : non static, void 로 정의 해야 함.

// ** org.junit.Assert 가 지원하는 다양한 Test용 Method 

// 1) assertEquals(a,b) : a와 b의 값(Value) 이 같은지 확인
// 2) assertSame(a,b) : 객체 a와b가 같은 객체임(같은 주소) 을 확인
// 3) assertTrue(a) : a가 참인지 확인
// 4) assertNotNull(a) : a객체가 Null 이 아님을 확인
// 5) assertArrayEquals(a,b) : 배열 a와b가 일치함을 확인

public class Ex01_BookTest {

// 1) assertEquals(a,b) : a와 b의 값(Value) 이 같은지 확인 =============================================================
//	@Test // JUnit 4 library
	public void equalsTest() {
		Book b1 = new Book("엄미현", "Java 정복", 9900);
//		assertEquals(b1.author, "엄미현"); // true : Green Line
		assertEquals(b1.author, "홍길동"); // false : Red Line => Failur Trace에 오류 내용 표시
	}

// 2) assertSame(a,b) : 객체 a와b가 같은 객체임(같은 주소) 을 확인 =====================================================
//	@Test
	public void sameTest() {
		Book b1 = new Book("엄미현", "Java 정복", 9900);
		Book b2 = new Book("엄미현", "Java 정복", 9900);
		Book b3 = new Book("김경식", "돈키호테", 15000);
		b3 = b1; // true => Green Line
		assertSame(b1, b3);
	}

// 3) assertTrue(a) : a가 참인지 확인 ==================================================================================
//	@Test
	public void trueTest() {
		Book b1 = new Book("김경식", "돈키호테", 15000);
		assertTrue(b1.isBook(false)); // false => Red Line
	}

// 4) assertNotNull(a) : a객체가 Null 이 아님을 확인 ===================================================================
//	@Test
	public void notNullTest() {
		Book b1 = new Book("김경식", "돈키호테", 15000);
		System.out.println("** b1 => " + b1);
		assertNotNull(b1); // Green Line
	}

// 5) assertArrayEquals(a,b) : 배열 a와b가 일치함을 확인 ===============================================================
	@Test
	public void arrayEqualsTest() {
		String[] a1 = new String[] { "가", "나", "다" };
		String[] a2 = new String[] { "가", "나", "다" };
		String[] a3 = new String[] { "가", "다", "나" };
		String[] a4 = new String[] { "가", "다", "라" };

		// 5.1) 두 배열의 순서, 값 모두 동일 (a1, a2)
//		assertArrayEquals(a1, a2); // Green
		// 5.2) 두 배열의 순서는 다르고, 값 모두 동일 (a1, a3)
		
//		assertArrayEquals(a1, a3); // Red
		// 5.3) 모두 다른 경우 (a1, a4)
		
		assertArrayEquals(a1, a4); // Red
	}

} // class