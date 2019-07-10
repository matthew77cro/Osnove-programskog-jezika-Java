package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Vector2DTest {
	
	static Vector2D vector1;
	static Vector2D vector2;
	static Vector2D vector3;
	static Vector2D vector4;
	static Vector2D vector5;
	static Vector2D vector6;
	static Vector2D vector7;

	@BeforeEach
	void setUp() throws Exception {
		vector1 = new Vector2D(0, 0);
		vector2 = new Vector2D(1, 0);
		vector3 = new Vector2D(0, 1);
		vector4 = new Vector2D(1, 1);
		vector5 = new Vector2D(-1, 0);
		vector6 = new Vector2D(0, -1);
		vector7 = new Vector2D(-1, -1);
	}

	@Test
	void testTranslate() {
		vector1.translate(new Vector2D(5, 3));
		vector2.translate(new Vector2D(5, 3));
		vector3.translate(new Vector2D(5, 3));
		vector4.translate(new Vector2D(5, 3));
		vector5.translate(new Vector2D(5, 3));
		vector6.translate(new Vector2D(5, 3));
		vector7.translate(new Vector2D(5, 3));
		
		assertEquals(5, vector1.getX());
		assertEquals(3, vector1.getY());
		assertEquals(6, vector2.getX());
		assertEquals(3, vector2.getY());
		assertEquals(5, vector3.getX());
		assertEquals(4, vector3.getY());
		assertEquals(6, vector4.getX());
		assertEquals(4, vector4.getY());
		assertEquals(4, vector5.getX());
		assertEquals(3, vector5.getY());
		assertEquals(5, vector6.getX());
		assertEquals(2, vector6.getY());
		assertEquals(4, vector7.getX());
		assertEquals(2, vector7.getY());
	}
	
	@Test
	void testRotate() {
		vector1.rotate(Math.PI/2.0);
		vector2.rotate(Math.PI/2.0);
		vector3.rotate(Math.PI/2.0);
		vector4.rotate(Math.PI/2.0);
		vector5.rotate(Math.PI/2.0);
		vector6.rotate(Math.PI/2.0);
		vector7.rotate(Math.PI/2.0);
		
		assertTrue(Math.abs(0-vector1.getX())<1E-7);
		assertTrue(Math.abs(0-vector1.getY())<1E-7);
		assertTrue(Math.abs(0-vector2.getX())<1E-7);
		assertTrue(Math.abs(1-vector2.getY())<1E-7);
		assertTrue(Math.abs(-1-vector3.getX())<1E-7);
		assertTrue(Math.abs(0-vector3.getY())<1E-7);
		assertTrue(Math.abs(-1-vector4.getX())<1E-7);
		assertTrue(Math.abs(1-vector4.getY())<1E-7);
		assertTrue(Math.abs(0-vector5.getX())<1E-7);
		assertTrue(Math.abs(-1-vector5.getY())<1E-7);
		assertTrue(Math.abs(1-vector6.getX())<1E-7);
		assertTrue(Math.abs(0-vector6.getY())<1E-7);
		assertTrue(Math.abs(1-vector7.getX())<1E-7);
		assertTrue(Math.abs(-1-vector7.getY())<1E-7);
	}
	
	@Test
	void testScale() {
		vector1.scale(1);
		vector2.scale(0);
		vector3.scale(-1);
		vector4.scale(5);
		vector5.scale(-5);
		vector6.scale(Math.PI);
		vector7.scale(-Math.PI);
		
		assertTrue(Math.abs(0-vector1.getX())<1E-7);
		assertTrue(Math.abs(0-vector1.getY())<1E-7);
		assertTrue(Math.abs(0-vector2.getX())<1E-7);
		assertTrue(Math.abs(0-vector2.getY())<1E-7);
		assertTrue(Math.abs(0-vector3.getX())<1E-7);
		assertTrue(Math.abs(-1-vector3.getY())<1E-7);
		assertTrue(Math.abs(5-vector4.getX())<1E-7);
		assertTrue(Math.abs(5-vector4.getY())<1E-7);
		assertTrue(Math.abs(5-vector5.getX())<1E-7);
		assertTrue(Math.abs(0-vector5.getY())<1E-7);
		assertTrue(Math.abs(0-vector6.getX())<1E-7);
		assertTrue(Math.abs(-Math.PI-vector6.getY())<1E-7);
		assertTrue(Math.abs(Math.PI-vector7.getX())<1E-7);
		assertTrue(Math.abs(Math.PI-vector7.getY())<1E-7);
	}

}
