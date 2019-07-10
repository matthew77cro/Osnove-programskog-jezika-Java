package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Color;

/**
 * Provider of the {@link Color}.
 * @author Matija
 *
 */
public interface IColorProvider {
	
	/**
	 * Returns the currently selected color.
	 * @return the currently selected color.
	 */
	public Color getCurrentColor();

	/**
	 * Adds a color change listener to the list of listeners.
	 * @param l color change listener which needs to be added to the list of listeners.
	 */
	public void addColorChangeListener(ColorChangeListener l);

	
	/**
	 * Removes a color change listener from the list of listeners.
	 * @param l color change listener which needs to be removed from the list of listeners.
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}
