package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * 
 * Element which contains single read-only property of type double.
 * 
 * @author Matija
 *
 */
public class ElementConstantDouble extends Element{

	private final double value;

	/**
	 * Constructor that initialises ElementConstantDouble with specific double value
	 * @param value value of double constant
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return Double.toString(value);
	}
	
	/**
	 * Returns double property of ElementConstantDouble
	 * @return double property of ElementConstantDouble
	 */
	public double getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ElementConstantDouble))
			return false;
		ElementConstantDouble other = (ElementConstantDouble) obj;
		return Double.doubleToLongBits(value) == Double.doubleToLongBits(other.value);
	}
	
}
