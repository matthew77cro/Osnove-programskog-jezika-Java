package hr.fer.zemris.math;

import java.util.Objects;

/**
 * This class represents a complex polynomial in the rooted form (as a product of (z-zi) where
 * zi is i-th root of the polynomial and a constant c). Example: <br>
 * 5*(z-3)*(z-4)*(z-(5+3i)) is a complex polynomial of degree 3
 * @author Matija
 *
 */
public class ComplexRootedPolynomial {
	
	private final Complex constant;
	private Complex[] roots;
	
	/**
	 * Creates and initialises the complex polynomial
	 * @param constant constant factor
	 * @param roots roots of the polynomial
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		if(roots.length < 1) throw new IllegalArgumentException("Polynomial degree cannot be less than 1!");
		this.constant = Objects.requireNonNull(constant);
		this.roots = Objects.requireNonNull(roots);
	}
	
	/**
	 * Computes polynomial value at given point z
	 * @param z value at which polynomial should be evaluated
	 * @return value which polynomial function has at this point
	 */
	public Complex apply(Complex z) {
		Complex result = constant;
		for(Complex cplx : roots) {
			result = result.multiply(z.sub(cplx));
		}
		return result;
	}
	
	/**
	 * Converts this representation to ComplexPolynomial type
	 * @return ComplexPolynomial which represents the same polynomial function
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial polynomial = new ComplexPolynomial(constant);
		
		for(Complex root : roots) {
			polynomial = polynomial.multiply(
					new ComplexPolynomial(
							root.negate(), Complex.ONE));
		}
		
		return polynomial;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("(" + constant.toString() + ")");
		for(Complex cplx : roots) {
			sb.append("*(z-(" + cplx.toString() + "))");
		}
		
		return sb.toString();
	}
	
	/**
	 * Finds index of closest root for given complex number z that is within
	 * threshold; if there is no such root, returns -1
	 * first root has index 0, second index 1, etc.
	 * @param z a complex number
	 * @param treshold threshold for finding the complex root
	 * @return index of the closest root within threshold if it exists, -1 otherwise
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		if(roots.length==0) return -1;
		
		int closestRootIndex = -1;
		double closestRootDistance = Double.POSITIVE_INFINITY;
		for(int i=0; i<roots.length; i++) {
			double distance = z.sub(roots[i]).module();
			if(distance<closestRootDistance && distance<treshold) {
				closestRootIndex = i;
				closestRootDistance = distance;
			}
		}
		
		return closestRootIndex;
	}
	
}