package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ComplexNumberTest {

	static ComplexNumber cx1;
	static ComplexNumber cx2;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		cx1 = new ComplexNumber(1, -1);
		cx2 = new ComplexNumber(-1, 1);
	}

	@Test
	void testGetReal() {
		assertEquals(1, cx1.getReal());
	}
	
	@Test
	void testGetImaginary() {
		assertEquals(-1, cx1.getImaginary());
	}
	
	@Test
	void testGetMagnitude() {
		if(!(Math.abs(Math.sqrt(Math.pow(1, 2)+Math.pow(-1, 2))-cx1.getMagnitude()) < 1E-6))
			fail();
	}
	
	@Test
	void testGetAngle() {
		double angle = Math.atan2(-1, 1);
		angle = angle > 0 ? angle : 2*Math.PI-Math.abs(angle);
		if(!(Math.abs(angle-cx1.getAngle()) < 1E-6))
			fail();
	}
	
	@Test
	void testAdd() {
		ComplexNumber cx3 = cx1.add(cx2);
		assertEquals(0, cx3.getReal());
		assertEquals(0, cx3.getImaginary());
	}
	
	@Test
	void testSub() {
		ComplexNumber cx3 = cx1.sub(cx2);
		assertEquals(2, cx3.getReal());
		assertEquals(-2, cx3.getImaginary());
	}
	
	@Test
	void testMul() {
		ComplexNumber cx3 = cx1.mul(cx2);
		assertEquals(0, cx3.getReal());
		assertEquals(2, cx3.getImaginary());
	}
	
	@Test
	void testDiv() {
		ComplexNumber cx3 = cx1.div(cx2);
		assertEquals(-1, cx3.getReal());
		assertEquals(0, cx3.getImaginary());
	}
	
	@Test
	void testPow() {
		ComplexNumber cx3 = cx1.power(10);
		if(!(Math.abs(0-cx3.getReal()) < 1E-6))
			fail();
		if(!(Math.abs(-32-cx3.getImaginary()) < 1E-6))
			fail();
	}
	
	@Test
	void testRoot() {
		ComplexNumber[] cx3 = cx1.root(2);
		if(!(Math.abs(-1.09868411346781-cx3[0].getReal()) < 1E-6))
			fail();
		if(!(Math.abs(0.455089860562227-cx3[0].getImaginary()) < 1E-6))
			fail();
		if(!(Math.abs(1.09868411346781-cx3[1].getReal()) < 1E-6))
			fail();
		if(!(Math.abs(-0.455089860562227-cx3[1].getImaginary()) < 1E-6))
			fail();
	}
	
	@Test
	void testFromReal() {
		ComplexNumber cx3 = ComplexNumber.fromReal(5);
		assertEquals(5, cx3.getReal());
		assertEquals(0, cx3.getImaginary());
	}
	
	@Test
	void testFromImaginary() {
		ComplexNumber cx3 = ComplexNumber.fromImaginary(5);
		assertEquals(0, cx3.getReal());
		assertEquals(5, cx3.getImaginary());
	}
	
	@Test
	void testFromMagnitudeAndAngle() {
		ComplexNumber cx3 = ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(2), (45.0/180)*Math.PI);
		if(!(Math.abs(1-cx3.getReal()) < 1E-6))
			fail();
		if(!(Math.abs(1-cx3.getImaginary()) < 1E-6))
			fail();
	}
	
	@Test
	void testParse() {
		ComplexNumber cx3 = ComplexNumber.parse("+5.5-3.3i");
		ComplexNumber cx4 = ComplexNumber.parse("-5.5-3.3i");
		ComplexNumber cx5 = ComplexNumber.parse("5.5-3.3i");
		ComplexNumber cx6 = ComplexNumber.parse("-5.5-i");
		ComplexNumber cx7 = ComplexNumber.parse("+5.5");
		ComplexNumber cx8 = ComplexNumber.parse("-5.5");
		ComplexNumber cx9 = ComplexNumber.parse("5.5");
		ComplexNumber cx10 = ComplexNumber.parse("+3.3i");
		ComplexNumber cx11 = ComplexNumber.parse("-3.3i");
		ComplexNumber cx12 = ComplexNumber.parse("3.3i");
		ComplexNumber cx13 = ComplexNumber.parse("+i");
		ComplexNumber cx14 = ComplexNumber.parse("-i");
		ComplexNumber cx15 = ComplexNumber.parse("i");
		
		assertEquals(5.5, cx3.getReal());
		assertEquals(-3.3, cx3.getImaginary());
		
		assertEquals(-5.5, cx4.getReal());
		assertEquals(-3.3, cx4.getImaginary());
		
		assertEquals(5.5, cx5.getReal());
		assertEquals(-3.3, cx5.getImaginary());
		
		assertEquals(-5.5, cx6.getReal());
		assertEquals(-1, cx6.getImaginary());
		
		assertEquals(5.5, cx7.getReal());
		assertEquals(0, cx7.getImaginary());
		
		assertEquals(-5.5, cx8.getReal());
		assertEquals(0, cx8.getImaginary());
		
		assertEquals(5.5, cx9.getReal());
		assertEquals(0, cx9.getImaginary());
		
		assertEquals(0, cx10.getReal());
		assertEquals(3.3, cx10.getImaginary());
		
		assertEquals(0, cx11.getReal());
		assertEquals(-3.3, cx11.getImaginary());
		
		assertEquals(0, cx12.getReal());
		assertEquals(3.3, cx12.getImaginary());
		
		assertEquals(0, cx13.getReal());
		assertEquals(1, cx13.getImaginary());
		
		assertEquals(0, cx14.getReal());
		assertEquals(-1, cx14.getImaginary());
		
		assertEquals(0, cx15.getReal());
		assertEquals(1, cx15.getImaginary());
	}
	
	@Test
	void testParseException() {
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("abc"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("i157"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("5.5.5+3.3i"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-5.5-3.3.3i"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-5.6.2"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-3.3.3i"));
	}

}