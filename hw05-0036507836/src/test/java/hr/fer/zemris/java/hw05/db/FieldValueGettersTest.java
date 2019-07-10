package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FieldValueGettersTest {
	
	static StudentRecord record;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		record = new StudentRecord("0036507836", "Bacic", "Matija", 5);
	}
	
	@Test
	void testFirstName() {
		assertEquals("Matija", FieldValueGetters.FIRST_NAME.get(record));
	}
	
	@Test
	void testLastName() {
		assertEquals("Bacic", FieldValueGetters.LAST_NAME.get(record));
	}
	
	@Test
	void testJmbag() {
		assertEquals("0036507836", FieldValueGetters.JMBAG.get(record));
	}

}
