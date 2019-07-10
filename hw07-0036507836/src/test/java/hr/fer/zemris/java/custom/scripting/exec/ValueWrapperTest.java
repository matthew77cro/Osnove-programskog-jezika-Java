package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValueWrapperTest {
	
	static ValueWrapper wrapperNull;
	static ValueWrapper wrapperInteger;
	static ValueWrapper wrapperDouble;
	static ValueWrapper wrapperLegalString;
	static ValueWrapper wrapperIllegalString;
	static ValueWrapper wrapperObject;
	
	static Object obj;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		obj = new Object();
		wrapperNull = new ValueWrapper(null);
		wrapperInteger = new ValueWrapper(5);
		wrapperDouble = new ValueWrapper(5.0);
		wrapperLegalString = new ValueWrapper("5.0E2");
		wrapperIllegalString = new ValueWrapper("5.0E3e");
		wrapperObject = new ValueWrapper(obj);
	}
	
	@BeforeEach
	void setUpBeforeEachTest() throws Exception {
		wrapperNull.setValue(null);
		wrapperInteger.setValue(5);
		wrapperDouble.setValue(5.0);
		wrapperLegalString.setValue("5.0E2");
		wrapperIllegalString.setValue("5.0E3e");
		wrapperObject.setValue(obj);
	}
	
	@Test
	void testWrapperValueNullWithNull() {
		wrapperNull.add(null);
		assertEquals(0, wrapperNull.getValue());
	}
	
	@Test
	void testWrapperValueNullWithInt() {
		wrapperNull.add(5);
		assertEquals(5, wrapperNull.getValue());
	}
	
	@Test
	void testWrapperValueNullWithDouble() {
		wrapperNull.add(5.5);
		assertEquals(5.5, wrapperNull.getValue());
	}
	
	@Test
	void testWrapperValueNullWithLegalString() {
		wrapperNull.add("5.0E2");
		assertEquals(500.0, wrapperNull.getValue());
	}
	
	@Test
	void testWrapperValueNullWithIllegalString() {
		assertThrows(RuntimeException.class, () -> wrapperNull.add("IllegalString"));
		assertEquals(null, wrapperNull.getValue());
	}
	
	@Test
	void testWrapperValueNullWithObject() {
		assertThrows(RuntimeException.class, () -> wrapperNull.add(obj));
		assertEquals(null, wrapperNull.getValue());
	}
	
	@Test
	void testWrapperValueIntegerWithNull() {
		wrapperInteger.add(null);
		assertEquals(5, wrapperInteger.getValue());
	}
	
	@Test
	void testWrapperValueIntegerWithInt() {
		wrapperInteger.add(5);
		assertEquals(10, wrapperInteger.getValue());
	}
	
	@Test
	void testWrapperValueIntegerWithDouble() {
		wrapperInteger.add(5.5);
		assertEquals(10.5, wrapperInteger.getValue());
	}
	
	@Test
	void testWrapperValueIntegerWithLegalString() {
		wrapperInteger.add("5.0E2");
		assertEquals(505.0, wrapperInteger.getValue());
	}
	
	@Test
	void testWrapperValueIntegerWithIllegalString() {
		assertThrows(RuntimeException.class, () -> wrapperInteger.add("IllegalString"));
		assertEquals(5, wrapperInteger.getValue());
	}
	
	@Test
	void testWrapperValueIntegerWithObject() {
		assertThrows(RuntimeException.class, () -> wrapperInteger.add(obj));
		assertEquals(5, wrapperInteger.getValue());
	}
	
	@Test
	void testWrapperValueDoubleWithNull() {
		wrapperDouble.add(null);
		assertEquals(5.0, wrapperDouble.getValue());
	}
	
	@Test
	void testWrapperValueDoubleWithInt() {
		wrapperDouble.add(5);
		assertEquals(10.0, wrapperDouble.getValue());
	}
	
	@Test
	void testWrapperValueDoubleWithDouble() {
		wrapperDouble.add(5.5);
		assertEquals(10.5, wrapperDouble.getValue());
	}
	
	@Test
	void testWrapperValueDoubleWithLegalString() {
		wrapperDouble.add("5.0E2");
		assertEquals(505.0, wrapperDouble.getValue());
	}
	
	@Test
	void testWrapperValueDoubleWithIllegalString() {
		assertThrows(RuntimeException.class, () -> wrapperDouble.add("IllegalString"));
		assertEquals(5.0, wrapperDouble.getValue());
	}
	
	@Test
	void testWrapperValueDoubleWithObject() {
		assertThrows(RuntimeException.class, () -> wrapperDouble.add(obj));
		assertEquals(5.0, wrapperDouble.getValue());
	}
	
	@Test
	void testWrapperLegalStringWithNull() {
		wrapperLegalString.add(null);
		assertEquals(500.0, wrapperLegalString.getValue());
	}
	
	@Test
	void testWrapperLegalStringWithInt() {
		wrapperLegalString.add(5);
		assertEquals(505.0, wrapperLegalString.getValue());
	}
	
	@Test
	void testWrapperLegalStringWithDouble() {
		wrapperLegalString.add(5.5);
		assertEquals(505.5, wrapperLegalString.getValue());
	}
	
	@Test
	void testWrapperLegalStringWithLegalString() {
		wrapperLegalString.add("5.0E2");
		assertEquals(1000.0, wrapperLegalString.getValue());
	}
	
	@Test
	void testWrapperLegalStringWithIllegalString() {
		assertThrows(RuntimeException.class, () -> wrapperLegalString.add("IllegalString"));
		assertEquals("5.0E2", wrapperLegalString.getValue());
	}
	
	@Test
	void testWrapperLegalStringWithObject() {
		assertThrows(RuntimeException.class, () -> wrapperLegalString.add(obj));
		assertEquals("5.0E2", wrapperLegalString.getValue());
	}
	
	@Test
	void testWrapperIllegalStringWithNull() {
		assertThrows(RuntimeException.class, () -> wrapperIllegalString.add(null));
		assertEquals("5.0E3e", wrapperIllegalString.getValue());
	}
	
	@Test
	void testWrapperIllegalStringWithInt() {
		assertThrows(RuntimeException.class, () -> wrapperIllegalString.add(5));
		assertEquals("5.0E3e", wrapperIllegalString.getValue());
	}
	
	@Test
	void testWrapperIllegalStringWithDouble() {
		assertThrows(RuntimeException.class, () -> wrapperIllegalString.add(5.0));
		assertEquals("5.0E3e", wrapperIllegalString.getValue());
	}
	
	@Test
	void testWrapperIllegalStringWithLegalString() {
		assertThrows(RuntimeException.class, () -> wrapperIllegalString.add("5.0E2"));
		assertEquals("5.0E3e", wrapperIllegalString.getValue());
	}
	
	@Test
	void testWrapperIllegalStringWithIllegalString() {
		assertThrows(RuntimeException.class, () -> wrapperIllegalString.add("IllegalString"));
		assertEquals("5.0E3e", wrapperIllegalString.getValue());
	}
	
	@Test
	void testWrapperIllegalStringWithObject() {
		assertThrows(RuntimeException.class, () -> wrapperIllegalString.add(obj));
		assertEquals("5.0E3e", wrapperIllegalString.getValue());
	}
	
	@Test
	void testWrapperObjectWithNull() {
		assertThrows(RuntimeException.class, () -> wrapperObject.add(null));
		assertEquals(obj, wrapperObject.getValue());
	}
	
	@Test
	void testWrapperObjectWithInt() {
		assertThrows(RuntimeException.class, () -> wrapperObject.add(5));
		assertEquals(obj, wrapperObject.getValue());
	}
	
	@Test
	void testWrapperObjectWithDouble() {
		assertThrows(RuntimeException.class, () -> wrapperObject.add(5.5));
		assertEquals(obj, wrapperObject.getValue());
	}
	
	@Test
	void testWrapperObjectWithLegalString() {
		assertThrows(RuntimeException.class, () -> wrapperObject.add("5.0E2"));
		assertEquals(obj, wrapperObject.getValue());
	}
	
	@Test
	void testWrapperObjectWithIllegalString() {
		assertThrows(RuntimeException.class, () -> wrapperObject.add("5.0E2e"));
		assertEquals(obj, wrapperObject.getValue());
	}
	
	@Test
	void testWrapperObjectWithObject() {
		assertThrows(RuntimeException.class, () -> wrapperObject.add(obj));
		assertEquals(obj, wrapperObject.getValue());
	}

}
