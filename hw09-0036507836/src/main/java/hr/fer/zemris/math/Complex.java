package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a support for working with complex numbers. It has cached
 * values for 0, 1, -1, i and -i.
 * 
 * @author Matija
 *
 */
public class Complex {

	/**
	 * 0 + 0i
	 */
	public static final Complex ZERO = new Complex(0,0);
	
	/**
	 * 1 + 0i
	 */
	public static final Complex ONE = new Complex(1,0);
	
	/**
	 * -1 + 0i
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);
	
	/**
	 * 0 + 1i
	 */
	public static final Complex IM = new Complex(0,1);
	
	/**
	 * 0 - 1i
	 */
	public static final Complex IM_NEG = new Complex(0,-1);
	
	private final double re;
	private final double im;
	private final double magnitude;
	private final double angle;
	
	/**
	 * Creates new complex number with value set to 0 + 0i
	 */
	public Complex() {
		this(0, 0);
	}
	
	/**
	 * Creates new complex number with value set to given real and imaginary component
	 * @param re real component of the complex number
	 * @param im imaginary component of the complex number
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
		this.magnitude = Math.sqrt(Math.pow(re, 2)+Math.pow(im, 2));
		double angle = Math.atan2(im, re);
		this.angle = angle > 0 ? angle : angle+2*Math.PI;
	}
	
	/**
	 * Returns module of complex number
	 * @return module of complex number
	 */
	public double module() {
		return magnitude;
	}
	
	/**
	 * Returns this*c
	 * @param c complex number with which to multiply this complex number
	 * @return new complex number result of this*c
	 */
	public Complex multiply(Complex c) {
		return new Complex(this.re*c.re - this.im*c.im, 
						   this.re*c.im + c.re*this.im);
	}
	
	/**
	 * Returns this*c
	 * @param c complex number with which to divide this complex number
	 * @return new complex number result of this/c
	 * @throws if c is null
	 */
	public Complex divide(Complex c) {
		if(c==null) throw new NullPointerException();
		
		Complex numerator = this.multiply(c.conjugate());
		double denominator = Math.pow(c.re, 2) + Math.pow(c.im, 2);
		
		return new Complex(numerator.re/denominator,numerator.im/denominator);
	}
	
	/**
	 * For number x+yi returns x-yi
	 * @return conjugated version of this complex number
	 */
	private Complex conjugate() {
		return new Complex(this.re, -this.im);
	}
	
	/**
	 * Returns this+c
	 * @param c complex number which to sum with this complex number
	 * @return new complex number result of this+c
	 */
	public Complex add(Complex c) {
		return new Complex(this.re+c.re, this.im+c.im);
	}
	
	/**
	 * Returns this-c
	 * @param c complex number which to subtract from this complex number
	 * @return new complex number result of this-c
	 */
	public Complex sub(Complex c) {
		return new Complex(this.re-c.re, this.im-c.im);
	}
	
	/**
	 * For complex number x+yi returns -x-yi
	 * @return negated complex number
	 */
	public Complex negate() {
		return new Complex(-this.re, -this.im);
	}
	
	/**
	 * returns this^n, n is non-negative integer
	 * @param n
	 * @return
	 * @throws IllegalArgumentException if n<0
	 */
	public Complex power(int n) {
		if(n<0) throw new IllegalArgumentException();
		
		double newMagnitude = Math.pow(magnitude, n);
		return new Complex(newMagnitude*Math.cos(n*angle),
				                 newMagnitude*Math.sin(n*angle));
	}
	
	/**
	 * Returns n-th root of this, n must be a positive integer.
	 * @param n root power
	 * @return n-th root of this complex number
	 * @throws IllegalArgumentException if n is <= 0
	 */
	public List<Complex> root(int n) {
		if(n<=0) throw new IllegalArgumentException();
		
		List<Complex> roots = new ArrayList<>();
		double newMagnitude = Math.pow(magnitude, 1.0/n);
		
		for(int i=0; i<n; i++) {
			double newAngle = (angle+2*i*Math.PI)/n;
			roots.add(new Complex(newMagnitude*Math.cos(newAngle),
								  newMagnitude*Math.sin(newAngle)));
		}
		
		return roots;
	}
	
	/**
	 * Returns the real component of the complex number
	 * @return the real component of the complex number
	 */
	public double getReal() {
		return re;
	}
	
	/**
	 * Returns the imaginary component of the complex number
	 * @return the imaginary component of the complex number
	 */
	public double getImaginary() {
		return im;
	}
	
	/**
	 * Parses a string and extracts real and imaginary part from it. Returns a new
	 * instance of a Complex number with those parameters. Here are some examples
	 * of legal string paramters:
	   <style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:black;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:black;}
		.tg .tg-0lax{text-align:left;vertical-align:top}
		</style>
		<table class="tg">
		  <tr>
		    <th class="tg-0lax">+5.6+i3.3<br></th>
		    <th class="tg-0lax">-5.6-i3.3</th>
		    <th class="tg-0lax">5.6+i3.3</th>
		  </tr>
		  <tr>
		    <td class="tg-0lax">+5.6+i</td>
		    <td class="tg-0lax">-5.6+i</td>
		    <td class="tg-0lax">5.6-i</td>
		  </tr>
		  <tr>
		    <td class="tg-0lax">+5.6</td>
		    <td class="tg-0lax">-5.6</td>
		    <td class="tg-0lax">5.6</td>
		  </tr>
		  <tr>
		    <td class="tg-0lax">+i3.3</td>
		    <td class="tg-0lax">-i3.3</td>
		    <td class="tg-0lax">i3.3</td>
		  </tr>
		  <tr>
		    <td class="tg-0lax">+i</td>
		    <td class="tg-0lax">-i</td>
		    <td class="tg-0lax">i</td>
		  </tr>
		</table>
		
	 * Examples of illegal parameters:
	 * <br/> abc <br/> 3+4+5i-6i <br/> -375i <br/> 5+3i	
	 * @param s String to be parsed
	 * @return A new instance of a Complex number with those parameters.
	 * @throws NullPointerException if parameter s is null
	 * @throws IllegalArgumentException if string s cannot be parsed to ComplexNumber (see the table above).
	 */
	public static Complex parse(String s) {
		if(s==null) throw new NullPointerException();
		
		double real = 0;
		double imaginary = 0;
		
		String realPositiveNumberRegex = "[0-9]+([.][0-9]+)?"; //real positive number (ex. 5.7 or just 5)
		String signRegex = "[+|-]"; //plus or minus sign
		
		if(s.matches(signRegex + "?" + realPositiveNumberRegex + signRegex + "{1}i(" + realPositiveNumberRegex + ")*")) { // +5.6+i3.3 -5.6-i3.3 5.6+i3.3 +5.6+i -5.6+i 5.6-i
			
			//if imaginary number is written as x+iy then extract real and imaginary value by splitting it on positive/negative signs
			int a = s.indexOf('+', 1);
			a = a==-1 ? s.indexOf('-', 1) : a;
			String realPart = s.substring(0, a);
			String imaginaryPart = normaliseImaginaryPart(s.substring(a, s.length()));
			
			real = Double.parseDouble(realPart);
			imaginary = Double.parseDouble(imaginaryPart);
			
		} else if(s.matches(signRegex + "?" + realPositiveNumberRegex)) {  // +5.6 -5.6 5.6 +5 -5 5
			
			//if imaginary number is written as just real part, no extraction needed, just parse it to double
			real = Double.parseDouble(s);
			
		} else if(s.matches(signRegex + "?i(" + realPositiveNumberRegex + ")*")) { // +i3.3 -i3.3 i3.3 +i -i i +i3 -i3 3i
			
			//if imaginary number is written as just imaginary part, normalise it and parse to double
			String imaginaryPart = normaliseImaginaryPart(s);
			imaginary = Double.parseDouble(imaginaryPart);
			
		} else {
			
			//if it does not match any regex, it is not the right format
			throw new IllegalArgumentException(s + " cannot be parsed to ComplexNumber.");
			
		}
		
		return new Complex(real, imaginary);
	}
	
	//this method is for internal use only, it normalises a value of imaginary part of a complex number because
	//it can be written in 3 different ways : +i, i, i1
	private static String normaliseImaginaryPart(String s) {
		String imaginaryPart = s;
		imaginaryPart = imaginaryPart.replace("i", "");
		if(imaginaryPart.length()<=1) imaginaryPart = imaginaryPart + "1"; //if no y value is given in imaginary part, add 1 as default value
		return imaginaryPart;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("%.1f", re));
		if(im<0) {
			sb.append("-");
		} else {
			sb.append("+");
		}
		sb.append(String.format("i%.1f", Math.abs(im)));
			
		return sb.toString();
	}
	
}