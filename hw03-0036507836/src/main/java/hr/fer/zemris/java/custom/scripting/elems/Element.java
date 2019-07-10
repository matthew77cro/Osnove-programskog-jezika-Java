package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Base class of the element hierarchy.
 * @author Matija
 *
 */
public class Element {

	/**
	 * Returns a string representation of the element.
	 * @return a string representation of the element.
	 */
	public String asText() {
		return "";
	}
	
	@Override
	public String toString() {
		return asText();
	}
	
}
