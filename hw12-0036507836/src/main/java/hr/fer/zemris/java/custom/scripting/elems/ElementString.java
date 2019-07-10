package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * 
 * Element which contains single read-only property of type String which represents the value of a string.
 * 
 * @author Matija
 *
 */
public class ElementString extends Element{

	private final String value;

	/**
	 * Constructor that initialises ElementString with specific String value which represents the value of a string.
	 * @param value value of a string
	 */
	public ElementString(String value) {
		this.value = Objects.requireNonNull(value);
	}
	
	@Override
	public String asText() {
		return value;
	}
	
	/**
	 * Returns value of a string
	 * @return value of a string
	 */
	public String getValue() {
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
		if (!(obj instanceof ElementString))
			return false;
		ElementString other = (ElementString) obj;
		return Objects.equals(value, other.value);
	}
	
	@Override
	public String toString() {
		return "\"" + asText() + "\"";
	}
	
}
