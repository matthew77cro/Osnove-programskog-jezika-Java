package hr.fer.zemris.math;

/**
 * This class represents a complex polynomial function.
 * @author Matija
 *
 */
public class ComplexPolynomial {
	
	private Complex[] factors;
	
	/**
	 * Creates a new polynomial out of the given factors. Factors are in the order as follows: <br>
	 * first factor z0 is alongside power of zero (z0*z^0)<br>
	 * second factor z1 is alongside power of one (z1*z^1)<br>
	 * third factor z2 is alongside power of two (z2*z^2)<br>
	 * and so on...
	 * @param factors factors in the complex polynomial function
	 */
	public ComplexPolynomial(Complex ... factors) {
		this.factors = factors;
	}
	
	/**
	 * Returns order of this polynomial; eg. for (7+2i)z^3+2z^2+5z+1 returns 3
	 * @return order of this polynomial
	 */
	public short order() {
		return (short) (factors.length-1);
	}
	
	/**
	 * computes a new polynomial this*p
	 * @param p polynomial with which to multiply this polynomial
	 * @return new polynomial result of this*p
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] factors = new Complex[this.order()+p.order()+1];
		
		for(int i=0; i<factors.length; i++) {
			factors[i] = Complex.ZERO;
		}
		
		for(int i=0, stop=this.order()+1; i<stop; i++) {
			for(int j=0, stop2=p.order()+1; j<stop2; j++) {
				factors[i+j] = factors[i+j].add(this.factors[i].multiply(p.factors[j]));
			}
		}
		
		return new ComplexPolynomial(factors);
	}
	
	/**
	 * Computes first derivative of this polynomial; for example, for
	 * (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * @return the first derivative of this polynomial 
	 */
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[factors.length-1];
		for(int i=1; i<factors.length; i++) {
			newFactors[i-1] = factors[i].multiply(new Complex(i,0));
		}
		return new ComplexPolynomial(newFactors);
	}
	
	/**
	 * Computes polynomial value at given point z
	 * @param z point at which to evaluate this polynomial
	 * @return result of evaluation of this polynomial at the given point
	 */
	public Complex apply(Complex z) {
		Complex cplx = Complex.ZERO;
		
		for(int i=0; i<factors.length; i++) {
			cplx = cplx.add(factors[i].multiply(z.power(i)));
		}
		
		return cplx;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("(" + factors[factors.length-1].toString() + ")*z^" + (factors.length-1));
		
		for(int i=factors.length-2; i>=0; i--) {
			sb.append("+(" + factors[i] + ")");
			if(i!=0) sb.append("*z^" + i);
		}
		
		return sb.toString();
	}
	
}