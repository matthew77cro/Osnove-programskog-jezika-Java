package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * 
 * Element which contains single read-only property of type int.
 * 
 * @author Matija
 *
 */
public class ElementConstantInteger extends Element{

	private final int value;

	/**
	 * Constructor that initialises ElementConstantInteger with specific int value
	 * @param value value of int constant
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
	/**
	 * Returns int property of ElementConstantInteger
	 * @return int property of ElementConstantInteger
	 */
	public int getValue() {
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
		if (!(obj instanceof ElementConstantInteger))
			return false;
		ElementConstantInteger other = (ElementConstantInteger) obj;
		return value == other.value;
	}
	
}
