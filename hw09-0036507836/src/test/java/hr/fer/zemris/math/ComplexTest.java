package hr.fer.zemris.math;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ComplexTest {

	static Complex cx1;
	static Complex cx2;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		cx1 = new Complex(1, -1);
		cx2 = new Complex(-1, 1);
	}
	
	@Test
	void testAdd() {
		Complex cx3 = cx1.add(cx2);
		assertEquals(0, cx3.getReal());
		assertEquals(0, cx3.getImaginary());
	}
	
	@Test
	void testSub() {
		Complex cx3 = cx1.sub(cx2);
		assertEquals(2, cx3.getReal());
		assertEquals(-2, cx3.getImaginary());
	}
	
	@Test
	void testMul() {
		Complex cx3 = cx1.multiply(cx2);
		assertEquals(0, cx3.getReal());
		assertEquals(2, cx3.getImaginary());
	}
	
	@Test
	void testDiv() {
		Complex cx3 = cx1.divide(cx2);
		assertEquals(-1, cx3.getReal());
		assertEquals(0, cx3.getImaginary());
	}
	
	@Test
	void testPow() {
		Complex cx3 = cx1.power(10);
		if(!(Math.abs(0-cx3.getReal()) < 1E-6))
			fail();
		if(!(Math.abs(-32-cx3.getImaginary()) < 1E-6))
			fail();
	}
	
	@Test
	void testRoot() {
		List<Complex> cx3 = cx1.root(2);
		if(!(Math.abs(-1.09868411346781-cx3.get(0).getReal()) < 1E-6))
			fail();
		if(!(Math.abs(0.455089860562227-cx3.get(0).getImaginary()) < 1E-6))
			fail();
		if(!(Math.abs(1.09868411346781-cx3.get(1).getReal()) < 1E-6))
			fail();
		if(!(Math.abs(-0.455089860562227-cx3.get(1).getImaginary()) < 1E-6))
			fail();
	}

}