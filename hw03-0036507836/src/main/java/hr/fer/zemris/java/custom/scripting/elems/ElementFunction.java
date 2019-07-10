package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * 
 * Element which contains single read-only property of type String which represents a name of a function.
 * 
 * @author Matija
 *
 */
public class ElementFunction extends Element{

	private final String name;

	/**
	 * Constructor that initialises ElementFunction with specific String value which represents a name of the function.
	 * @param value name of a function
	 */
	public ElementFunction(String name) {
		this.name = Objects.requireNonNull(name);
	}
	
	@Override
	public String asText() {
		return name;
	}
	
	/**
	 * Returns name of the function
	 * @return name of the function
	 */
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ElementFunction))
			return false;
		ElementFunction other = (ElementFunction) obj;
		return Objects.equals(name, other.name);
	}
	
	@Override
	public String toString() {
		return "@" + asText();
	}
	
}
