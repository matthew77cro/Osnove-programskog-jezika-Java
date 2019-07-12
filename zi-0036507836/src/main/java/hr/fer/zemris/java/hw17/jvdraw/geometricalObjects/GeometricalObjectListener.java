package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects;

/**
 * Listener used as an observer of changes of a geometric object.
 * @author Matija
 *
 */
public interface GeometricalObjectListener {
	/**
	 * Method called when a {@link GeometricalObject} is changed.
	 * @param o geometric object that has been changed.
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}