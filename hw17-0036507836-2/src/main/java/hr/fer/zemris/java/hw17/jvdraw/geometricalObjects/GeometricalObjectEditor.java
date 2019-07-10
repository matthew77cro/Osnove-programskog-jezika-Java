package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects;

import javax.swing.JPanel;

/**
 * Editor of a geometric object represented as a JPanel of editable attributes.
 * @author Matija
 *
 */
@SuppressWarnings("serial")
public abstract class GeometricalObjectEditor extends JPanel {
	/**
	 * Method that needs to be called after user has finished editing the attributes.
	 */
	public abstract void checkEditing();

	/**
	 * Method that needs to be called after user has finished editing the attributes
	 * and those changes need to be stored.
	 */
	public abstract void acceptEditing();
}
