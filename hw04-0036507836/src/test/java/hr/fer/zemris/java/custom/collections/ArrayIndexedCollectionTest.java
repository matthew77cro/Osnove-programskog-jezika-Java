package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrayIndexedCollectionTest {

	static ArrayIndexedCollection<String> c1;
	static ArrayIndexedCollection<String> c2;
	static ArrayIndexedCollection<String> c3;
	static ArrayIndexedCollection<String> c4;

	@BeforeEach
	void setUp() throws Exception {
		c1 = new ArrayIndexedCollection<String>();
		c2 = new ArrayIndexedCollection<String>(20);
		c2.add("0");
		c2.add("1");
		c2.add("2");
		c2.add("3");
		c2.add("4");
		c2.add("5");
		c2.add("6");
		c2.add("7");
		c2.add("8");
		c2.add("9");
		c2.add("10");
		c2.add("11");
		c2.add("12");
		c2.add("13");
		c2.add("14");
		c2.add("15");
		c2.add("16");
		c2.add("17");
		c2.add("18");
		c2.add("19");
		c3 = new ArrayIndexedCollection<String>(c2);
		c4 = new ArrayIndexedCollection<String>(c2,10);
	}
	
	@Test
	void testSizeAndIsEmpty() {
		assertEquals(0, c1.size());
		assertTrue(c1.isEmpty());
		assertEquals(20, c2.size());
		assertFalse(c2.isEmpty());
		assertEquals(20, c3.size());
		assertFalse(c3.isEmpty());
		assertEquals(20, c4.size());
		assertFalse(c4.isEmpty());
	}
	
	@Test
	void testAdd() {
		c1.add("0");
		assertTrue(c1.contains("0"));
		assertEquals(1, c1.size());
		assertEquals(0, c1.indexOf("0"));
		
		c1.add("1");
		assertTrue(c1.contains("1"));
		assertEquals(2, c1.size());
		assertEquals(1, c1.indexOf("1"));
	}
	
	@Test
	void testGet() {
		assertThrows(IndexOutOfBoundsException.class, ()->c1.get(0));
		assertThrows(IndexOutOfBoundsException.class, ()->c2.get(-1));
		assertThrows(IndexOutOfBoundsException.class, ()->c3.get(20));
		assertEquals("0", c2.get(0));
		assertEquals("15", c3.get(15));
	}
	
	@Test
	void testClear() {
		c2.clear();
		assertEquals(0, c2.size());
		assertTrue(c2.isEmpty());
		assertFalse(c2.contains("4"));
		assertEquals(-1, c2.indexOf("5"));
	}
	
	@Test
	void testContains() {
		assertTrue(c3.contains("4"));
		assertFalse(c3.contains("-1"));
	}
	
	@Test
	void testInsert() {
		c3.insert("-1", 20);
		c3.insert("-2", 0);
		c3.insert("-3", 5);
		assertTrue(c3.contains("-1"));
		assertTrue(c3.contains("-2"));
		assertTrue(c3.contains("-3"));
		assertEquals(2, c3.indexOf("1"));
		assertEquals(7, c3.indexOf("5"));
		assertEquals(23, c3.size());
		assertThrows(NullPointerException.class, ()->c3.insert(null, 6));
		assertThrows(IndexOutOfBoundsException.class, ()->c3.insert("-4", 24));
	}
	
	@Test
	void testIndexOf() {
		assertEquals(1, c2.indexOf("1"));
		assertEquals(5, c3.indexOf("5"));
		assertEquals(-1, c2.indexOf("java"));
	}
	
	@Test
	void testRemoveAtIndex() {
		c2.remove(3);
		c2.remove(4);
		assertFalse(c2.contains(3));
		assertFalse(c2.contains(5));
		assertTrue(c2.contains("4"));
		assertEquals(18, c2.size());
		assertEquals(3, c2.indexOf("4"));
		assertEquals(4, c2.indexOf("6"));
	}
	
	@Test
	void testRemoveObject() {
		c2.remove("3");
		c2.remove("5");
		assertFalse(c2.contains("3"));
		assertFalse(c2.contains("5"));
		assertTrue(c2.contains("4"));
		assertEquals(18, c2.size());
		assertEquals(3, c2.indexOf("4"));
		assertEquals(4, c2.indexOf("6"));
	}

}
