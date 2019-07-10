package hr.fer.zemris.math.demo;

import hr.fer.zemris.math.Complex;

public class ComplexDemo {

	public static void main(String[] args) {
		Complex cmp = new Complex(10, 5);
		System.out.println(cmp);
		System.out.println(cmp.multiply(Complex.ONE_NEG));
		System.out.println(cmp.multiply(new Complex(10, 5)));
		System.out.println(cmp.multiply(new Complex(-10, -5)));
	}

}
