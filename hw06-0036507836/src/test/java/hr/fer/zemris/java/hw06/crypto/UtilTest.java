package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void testOddNumberedArray() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("aabbc"));
	}
	
	@Test
	void testIllegalCharacter() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("aabbcy"));
	}
	
	@Test
	void testAllLowerCase() {
		assertTrue(Arrays.equals(Util.hextobyte("01ae22"),new byte[] {1, -82, 34}));
	}
	
	@Test
	void testAllUpperCase() {
		assertTrue(Arrays.equals(Util.hextobyte("01AE22"),new byte[] {1, -82, 34}));
	}
	
	@Test
	void testMixed() {
		assertTrue(Arrays.equals(Util.hextobyte("01aE22"),new byte[] {1, -82, 34}));
	}
	
	@Test
	void test() {
		assertTrue("01ae22".equals(Util.bytetohex(new byte[] {1, -82, 34})));
	}

}
