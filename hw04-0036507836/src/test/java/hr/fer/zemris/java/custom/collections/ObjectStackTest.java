package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObjectStackTest {

	ObjectStack<String> stack;
	
	@BeforeEach
	void setUp() throws Exception {
		stack = new ObjectStack<String>();
	}
	
	@Test
	void testSizeAndIsEmpty() {
		assertEquals(0, stack.size());
		assertTrue(stack.isEmpty());
		stack.push("0");
		assertEquals(1, stack.size());
		assertFalse(stack.isEmpty());
	}
	
	@Test
	void testPushAndPop() {
		stack.push("0");
		stack.push("1");
		stack.push("2");
		assertEquals("2", stack.pop());
		assertEquals("1", stack.pop());
		assertEquals("0", stack.pop());
		assertThrows(EmptyStackException.class, () -> stack.pop());
	}
	
	@Test
	void testPeek() {
		stack.push("0");
		stack.push("1");
		stack.push("2");
		assertEquals("2", stack.peek());
		assertEquals("2", stack.peek());
		stack.pop();
		assertEquals("1", stack.peek());
		stack.pop();
		stack.pop();
		assertThrows(EmptyStackException.class, () -> stack.peek());
	}
	
	@Test
	void testClear() {
		stack.push("0");
		stack.push("1");
		stack.push("2");
		stack.clear();
		assertEquals(0, stack.size());
		assertTrue(stack.isEmpty());
	}

}
