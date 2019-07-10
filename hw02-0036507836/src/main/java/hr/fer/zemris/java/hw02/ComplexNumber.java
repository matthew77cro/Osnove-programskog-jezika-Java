package hr.fer.zemris.java.hw02;

/**
 * This class represents a support for working with complex numbers. Class ComplexNumber
 * represents an unmodifiable complex number. 
 * 
 * @author Matija
 *
 */
public class ComplexNumber {

	private double real;
	private double imaginary;
	private double magnitude;
	private double angle;
	
	/**
	 * Creates a new instance of ComplexNumber class with real and imaginary values set to
	 * the values given as constructor arguments.
	 * @param real real part of a complex number
	 * @param imaginary imaginary part of a complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
		this.magnitude = Math.sqrt(Math.pow(real, 2)+Math.pow(imaginary, 2));
		double angle = Math.atan2(imaginary, real);
		this.angle = angle > 0 ? angle : 2*Math.PI-Math.abs(angle);
	}
	
	/**
	 * Returns a new instance of a ComplexNumber with real part set to
	 * methods argument and imaginary part set to 0.
	 * @param real real part of a complex number
	 * @return Returns a new instance of a ComplexNumber with real part set to
	 * methods argument and imaginary part set to 0
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Returns a new instance of a ComplexNumber with imaginary part set to
	 * methods argument and real part set to 0.
	 * @param imaginary imaginary part of a complex number
	 * @return Returns a new instance of a ComplexNumber with imaginary part set to
	 * methods argument and real part set to 0.
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Converts magnitude and angle of a complex number in real and imaginary
	 * part and returns a new instance of ComplexNumber with those arguments.
	 * @param magnitude magnitude of a complex number
	 * @param angle angle of a complex number
	 * @return Returns a new instance of a ComplexNumber that represents a
	 * complex number with a given magnitude and angle
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude*Math.cos(angle), magnitude*Math.sin(angle));
	}
	
	/**
	 * Parses a string and extracts real and imaginary part from it. Returns a new
	 * instance of a ComplexNumber with those parameters. Here are some examples
	 * of legal string paramters:
	   <style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:black;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:black;}
		.tg .tg-0lax{text-align:left;vertical-align:top}
		</style>
		<table class="tg">
		  <tr>
		    <th class="tg-0lax">+5.6+3.3i<br></th>
		    <th class="tg-0lax">-5.6-3.3i</th>
		    <th class="tg-0lax">5.6+3.3i</th>
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
		    <td class="tg-0lax">+3.3i</td>
		    <td class="tg-0lax">-3.3i</td>
		    <td class="tg-0lax">3.3i</td>
		  </tr>
		  <tr>
		    <td class="tg-0lax">+i</td>
		    <td class="tg-0lax">-i</td>
		    <td class="tg-0lax">i</td>
		  </tr>
		</table>
		
	 * Examples of illegal parameters:
	 * <br/> abc <br/> 3+4+5i-6i <br/> -i375	
	 * @param s String to be parsed
	 * @return A new instance of a ComplexNumber with those parameters.
	 * @throws NullPointerException if parameter s is null
	 * @throws IllegalArgumentException if string s cannot be parsed to ComplexNumber (see the table above).
	 */
	public static ComplexNumber parse(String s) {
		if(s==null) throw new NullPointerException();
		
		double real = 0;
		double imaginary = 0;
		
		String realPositiveNumberRegex = "[0-9]+([.][0-9]+)?"; //real positive number (ex. 5.7 or just 5)
		String signRegex = "[+|-]"; //plus or minus sign
		
		if(s.matches(signRegex + "?" + realPositiveNumberRegex + signRegex + "{1}(" + realPositiveNumberRegex + ")*i")) { // +5.6+3.3i -5.6-3.3i 5.6+3.3i +5.6+i -5.6+i 5.6-i
			
			//if imaginary number is written as x+yi then extract real and imaginary value by splitting it on positive/negative signs
			int a = s.indexOf('+', 1);
			a = a==-1 ? s.indexOf('-', 1) : a;
			String realPart = s.substring(0, a);
			String imaginaryPart = extractAndNormaliseImaginaryPart(s, a, s.length()-1);
			
			real = Double.parseDouble(realPart);
			imaginary = Double.parseDouble(imaginaryPart);
			
		} else if(s.matches(signRegex + "?" + realPositiveNumberRegex)) {  // +5.6 -5.6 5.6 +5 -5 5
			
			//if imaginary number is written as just real part, no extraction needed, just parse it to double
			real = Double.parseDouble(s);
			
		} else if(s.matches(signRegex + "?(" + realPositiveNumberRegex + ")*i")) { // +3.3i -3.3i 3.3i +i -i i +3i -3i 3i
			
			//if imaginary number is written as just imaginary part, normalise it and parse to double
			String imaginaryPart = extractAndNormaliseImaginaryPart(s, 0, s.length()-1);
			imaginary = Double.parseDouble(imaginaryPart);
			
		} else {
			
			//if it does not match any regex, it is not the right format
			throw new IllegalArgumentException(s + " cannot be parsed to ComplexNumber.");
			
		}
		
		return new ComplexNumber(real, imaginary);
	}
	
	//this method is for internal use only, it normalises a value of imaginary part of a complex number because
	//it can be written in 3 different ways : +i, i, 1i
	private static String extractAndNormaliseImaginaryPart(String complexNumber, int startIndex, int endIndex) {
		String imaginaryPart = complexNumber.substring(startIndex, endIndex);
		imaginaryPart = imaginaryPart.equals("+") || imaginaryPart.isEmpty() ? "1" : imaginaryPart;
		imaginaryPart = imaginaryPart.equals("-") ? "-1" : imaginaryPart;
		return imaginaryPart;
	}
	
	/**
	 * Returns a real part of a complex number.
	 * @return a real part of a complex number.
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Returns an imaginary part of a complex number.
	 * @return an imaginary part of a complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Returns a magnitude part of a complex number.
	 * @return a magnitude part of a complex number.
	 */
	public double getMagnitude() {
		return magnitude;
	}
	
	/**
	 * Returns an angle part of a complex number.
	 * @return an angle part of a complex number.
	 */
	public double getAngle() {
		return angle;
	}
	
	/**
	 * Adds complex number c to existing complex number. Returns a new instance of ComplexNumber that is a sum of those two.
	 * @param c other complex number
	 * @return a new instance of ComplexNumber that is a sum of two.
	 */
	public ComplexNumber add(ComplexNumber c) {
		if(c==null) throw new NullPointerException();
		return new ComplexNumber(this.real+c.real, this.imaginary+c.imaginary);
	}
	
	/**
	 * Subtracts complex number c from existing complex number. Returns a new instance of ComplexNumber that is a difference of those two.
	 * @param c other complex number
	 * @return a new instance of ComplexNumber that is a difference of two.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		if(c==null) throw new NullPointerException();
		return new ComplexNumber(this.real-c.real, this.imaginary-c.imaginary);
	}
	
	/**
	 * Multiplies complex number c with existing complex number. Returns a new instance of ComplexNumber that is a product of those two.
	 * @param c other complex number
	 * @return a new instance of ComplexNumber that is a product of two.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		if(c==null) throw new NullPointerException();
		return new ComplexNumber(this.real*c.real-this.imaginary*c.imaginary, 
				                 this.real*c.imaginary+c.real*this.imaginary);
	}
	
	/**
	 * Divides existing complex number with complex number c. Returns a new instance of ComplexNumber that is a quotient of those two.
	 * @param c other complex number
	 * @return a new instance of ComplexNumber that is a quotient of two.
	 */
	public ComplexNumber div(ComplexNumber c) {
		if(c==null) throw new NullPointerException();
		
		ComplexNumber numerator = this.mul(c.conjugate());
		double denominator = Math.pow(c.real, 2) + Math.pow(c.imaginary, 2);
		
		return new ComplexNumber(numerator.real/denominator,numerator.imaginary/denominator);
		
	}
	
	/**
	 * Returns a new conjugated complex number.
	 * @return a new conjugated complex number.
	 */
	private ComplexNumber conjugate() {
		return new ComplexNumber(this.real, -this.imaginary);
	}
	
	/**
	 * Returns a new complex number that is existing to the n-th power.
	 * @param n power on which to raise the complex number
	 * @return a new complex number that is existing to the n-th power.
	 */
	public ComplexNumber power(int n) {
		if(n<0) throw new IllegalArgumentException();
		
		double newMagnitude = Math.pow(magnitude, n);
		return new ComplexNumber(newMagnitude*Math.cos(n*angle),
				                 newMagnitude*Math.sin(n*angle));
	}
	
	/**
	 * Calculates and returns all n-th roots of a complex number
	 * @param n degree of root
	 * @return all n-th roots of a complex number
	 */
	public ComplexNumber[] root(int n) {
		if(n<=0) throw new IllegalArgumentException();
		
		ComplexNumber[] roots = new ComplexNumber[n];
		double newMagnitude = Math.pow(magnitude, 1.0/n);
		
		for(int i=0; i<n; i++) {
			double newAngle = (angle+2*i*Math.PI)/n;
			roots[i] = new ComplexNumber(newMagnitude*Math.cos(newAngle),
					                     newMagnitude*Math.sin(newAngle));
		}
		
		return roots;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if(real!=0) {
			sb.append(String.format("%.2f", real));
		}
		if(imaginary!=0) {
			if(imaginary>0 && real!=0) {
				sb.append("+");
			}
			if(imaginary==-1){
				sb.append("-");
			}
			if(imaginary!=1 && imaginary!=-1)
				sb.append(String.format("%.2f", imaginary));
			sb.append("i");
		}
		
		if(sb.length()==0) {
			sb.append("0");
		}
		
		return sb.toString();
		
		//uncomment this if you just want to debug; returns string of complex number as tuple of 2 elements (re,im)
		//return new String("(" + this.real + "," + this.imaginary + ")");
	}
	
}
