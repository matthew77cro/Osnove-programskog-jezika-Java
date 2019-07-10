package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DictionayTest {
	
	static Dictionary<String, Integer> d1;
	static Dictionary<String, Integer> d2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		d1 = new Dictionary<>();
		d2 = new Dictionary<>();
		d2.put("Ana", 1);
		d2.put("Sara", 5);
		d2.put("Petra", 5);
		d2.put("Stjepan", 5);
		d2.put("Fran", 3);
		d2.put("Kristijan", 2);
	}

	@Test
	void testEmptyAndSize() {
		assertTrue(d1.isEmpty());
		assertTrue(d1.size()==0);
		assertTrue(!d2.isEmpty());
		assertTrue(d2.size()==6);
	}
	
	@Test
	void testThrows() {
		assertThrows(NullPointerException.class, () -> d1.put(null, 0));
		assertThrows(NullPointerException.class, () -> d2.put(null, 0));
	}
	
	@Test
	void testGet() {
		assertEquals(null, d1.get(null));
		assertEquals(null, d2.get(null));
		assertEquals(null, d1.get("Test123"));
		assertEquals(null, d2.get("Klarisa"));
		assertEquals(5, d2.get("Sara"));
		assertEquals(5, d2.get("Stjepan"));
		assertEquals(1, d2.get("Ana"));
		assertEquals(2, d2.get("Kristijan"));
	}

}