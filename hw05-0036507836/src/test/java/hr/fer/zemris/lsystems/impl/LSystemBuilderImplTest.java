package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;

class LSystemBuilderImplTest {

	static LSystemBuilderImpl builder;
	static LSystem system;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		builder = (LSystemBuilderImpl) new LSystemBuilderImpl().setAxiom("F").registerProduction('F', "F+F--F+F");
		system = builder.new LSystemImpl();
	}

	@Test
	void test() {
		String level0 = system.generate(0);
		String level1 = system.generate(1);
		String level2 = system.generate(2);
		
		assertEquals("F", level0);
		assertEquals("F+F--F+F", level1);
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", level2);
	}

}
