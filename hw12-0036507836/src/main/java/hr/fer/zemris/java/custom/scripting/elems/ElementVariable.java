package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * 
 * Element which contains single read-only property of type String which represents a name of a variable.
 * 
 * @author Matija
 *
 */
public class ElementVariable extends Element{
	
	private final String name;
	
	/**
	 * Constructor that initialises ElementVariable with specific String value which represents a name of a variable.
	 * @param value name of a function
	 */
	public ElementVariable(String name) {
		this.name = Objects.requireNonNull(name);
	}
	
	@Override
	public String asText() {
		return name;
	}
	
	/**
	 * Returns name of the variable
	 * @return name of the variable
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
		if (!(obj instanceof ElementVariable))
			return false;
		ElementVariable other = (ElementVariable) obj;
		return Objects.equals(name, other.name);
	}
	
}
