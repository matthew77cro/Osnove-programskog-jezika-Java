package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JButton;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CalcLayoutTest {
	
	static CalcLayout layout;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		layout = new CalcLayout();
	}

	@Test
	void test1() {
		assertThrows(UnsupportedOperationException.class, () -> layout.addLayoutComponent(new JButton(), new RCPosition(-1, 2)));
	}
	
	@Test
	void test2() {
		assertThrows(UnsupportedOperationException.class, () -> layout.addLayoutComponent(new JButton(), new RCPosition(1, -2)));
	}
	
	@Test
	void test3() {
		assertThrows(UnsupportedOperationException.class, () -> layout.addLayoutComponent(new JButton(), new RCPosition(8, 3)));
	}
	
	@Test
	void test4() {
		assertThrows(UnsupportedOperationException.class, () -> layout.addLayoutComponent(new JButton(), new RCPosition(3, 8)));
	}
	
	@Test
	void test5() {
		assertThrows(UnsupportedOperationException.class, () -> layout.addLayoutComponent(new JButton(), new RCPosition(1, 2)));
		assertThrows(UnsupportedOperationException.class, () -> layout.addLayoutComponent(new JButton(), new RCPosition(1, 3)));
		assertThrows(UnsupportedOperationException.class, () -> layout.addLayoutComponent(new JButton(), new RCPosition(1, 4)));
		assertThrows(UnsupportedOperationException.class, () -> layout.addLayoutComponent(new JButton(), new RCPosition(1, 5)));
	}
	
	@Test
	void test6() {
		layout.addLayoutComponent(new JButton(), new RCPosition(1, 1));
		assertThrows(UnsupportedOperationException.class, () -> layout.addLayoutComponent(new JButton(), new RCPosition(1, 1)));
	}

}
