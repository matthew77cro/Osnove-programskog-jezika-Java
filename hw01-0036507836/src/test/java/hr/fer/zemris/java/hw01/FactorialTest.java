package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FactorialTest {

	@Test
	void factorialTestExceptionThrowing() {
		assertThrows(IllegalArgumentException.class, () -> Factorial.factorial(-1));
	}
	
	@Test
	void factorialTestZero() {
		assertEquals(1, Factorial.factorial(0));
	}
	
	@Test
	void factorialTestInteger() {
		assertEquals(120, Factorial.factorial(5));
	}

}
