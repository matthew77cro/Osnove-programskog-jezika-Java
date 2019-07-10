package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComparisonOperatorsTest {
	
	@Test
	void testEquals() {
		assertTrue(ComparisonOperators.EQUALS.satisfied("String1", "String1"));
		assertFalse(ComparisonOperators.EQUALS.satisfied("String1", "string1"));
	}
	
	@Test
	void testGreater() {
		assertTrue(ComparisonOperators.GREATER.satisfied("BCD", "ABC"));
		assertFalse(ComparisonOperators.GREATER.satisfied("Atring1", "String1"));
	}
	
	@Test
	void testGreaterOrEquals() {
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("String1", "String1"));
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("String2", "String1"));
		assertFalse(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Atring1", "String1"));
		assertFalse(ComparisonOperators.GREATER_OR_EQUALS.satisfied("abc", "bcd"));
	}
	
	@Test
	void testLess() {
		assertTrue(ComparisonOperators.LESS.satisfied("ABC", "BCD"));
		assertFalse(ComparisonOperators.LESS.satisfied("String1", "Atring1"));
	}
	
	@Test
	void testLessOrEquals() {
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("String1", "String1"));
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("String1", "String2"));
		assertFalse(ComparisonOperators.LESS_OR_EQUALS.satisfied("String1", "Atring1"));
		assertFalse(ComparisonOperators.LESS_OR_EQUALS.satisfied("bcd", "abc"));
	}
	
	@Test
	void testNotEquals() {
		assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("ABC", "BCD"));
		assertFalse(ComparisonOperators.NOT_EQUALS.satisfied("ABC", "ABC"));
	}
	
	@Test
	void testLike() {
		assertTrue(ComparisonOperators.LIKE.satisfied("AAAA", "AA*AA"));
		assertFalse(ComparisonOperators.LIKE.satisfied("AAA", "AA*AA"));
	}

}
