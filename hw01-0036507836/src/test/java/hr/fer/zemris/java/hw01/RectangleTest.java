package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RectangleTest {

	@Test
	void areaTestExceptionThrowing() {
		assertThrows(IllegalArgumentException.class, () -> Rectangle.area(-1, 5));
		assertThrows(IllegalArgumentException.class, () -> Rectangle.area(5, -1));
	}
	
	@Test
	void areaTestZero() {
		assertEquals(0, Rectangle.area(0, 3));
		assertEquals(0, Rectangle.area(3, 0));
	}
	
	@Test
	void areaTest() {
		assertEquals(30, Rectangle.area(5, 6));
	}
	
	@Test
	void perimeterTestExceptionThrowing() {
		assertThrows(IllegalArgumentException.class, () -> Rectangle.perimeter(-1, 5));
		assertThrows(IllegalArgumentException.class, () -> Rectangle.perimeter(5, -1));
	}
	
	@Test
	void perimeterTestZero() {
		assertEquals(6, Rectangle.perimeter(0, 3));
		assertEquals(6, Rectangle.perimeter(3, 0));
	}
	
	@Test
	void perimeterTest() {
		assertEquals(22, Rectangle.perimeter(5, 6));
	}

}
