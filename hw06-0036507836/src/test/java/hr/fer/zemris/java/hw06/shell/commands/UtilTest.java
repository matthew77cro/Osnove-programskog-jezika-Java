package hr.fer.zemris.java.hw06.shell.commands;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilTest {
	
	@Test
	void testWithoutQuotes() {
		String[] result = Util.splitArgs("   one    two    three    ");
		assertEquals(3, result.length);
		assertEquals("one", result[0]);
		assertEquals("two", result[1]);
		assertEquals("three", result[2]);
	}
	
	@Test
	void testQuotes() {
		String[] result = Util.splitArgs("   \"C:\\Users\\Matija\\Documents and Settings\"    ");
		assertEquals(1, result.length);
		assertEquals("C:\\Users\\Matija\\Documents and Settings", result[0]);
	}
	
	@Test
	void testQuotesEscaping() {
		String[] result = Util.splitArgs("   \"C:\\Users\\Matija\\Documents and Settings \\\\ \\\" \"    ");
		assertEquals(1, result.length);
		assertEquals("C:\\Users\\Matija\\Documents and Settings \\ \" ", result[0]);
	}
	
	@Test
	void testCombined() {
		String[] result = Util.splitArgs("  One Two \"C:\\Users\\Matija\\Documents and Settings\"  Three  ");
		assertEquals(4, result.length);
		assertEquals("One", result[0]);
		assertEquals("Two", result[1]);
		assertEquals("C:\\Users\\Matija\\Documents and Settings", result[2]);
		assertEquals("Three", result[3]);
	}

}
