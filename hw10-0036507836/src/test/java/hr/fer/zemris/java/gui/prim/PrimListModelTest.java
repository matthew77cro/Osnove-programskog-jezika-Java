package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrimListModelTest {
	
	static PrimListModel model;

	@BeforeEach
	void setUpBeforeEachTest() throws Exception {
		model = new PrimListModel();
	}

	@Test
	void test() {
		assertEquals(1, model.getSize());
		assertEquals(1, model.getElementAt(0));
	}
	
	@Test
	void test2() {
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		
		assertEquals(6, model.getSize());
		assertEquals(1, model.getElementAt(0));
		assertEquals(2, model.getElementAt(1));
		assertEquals(3, model.getElementAt(2));
		assertEquals(5, model.getElementAt(3));
		assertEquals(7, model.getElementAt(4));
		assertEquals(11, model.getElementAt(5));
	}

}
