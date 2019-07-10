package hr.fer.zemris.lsystems.demo;

import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

public class Test {

	public static void main(String[] args) {
		LSystemBuilder l = new LSystemBuilderImpl();
		
		for(int i=0; i<=2; i++)
		System.out.println(l.setAxiom("F").registerProduction('F', "F+F--F+F").build().generate(i));
		
		System.out.println("    hey      hey    ".trim().split(" ").length);
	}

}
