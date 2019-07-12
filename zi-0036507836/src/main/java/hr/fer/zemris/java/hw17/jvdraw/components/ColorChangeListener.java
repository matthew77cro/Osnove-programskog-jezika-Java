package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Color;

/**
 * Listener used for observing the change of the color in the {@link IColorProvider}.
 * @author Matija
 *
 */
public interface ColorChangeListener {
	
	/**
	 * Method triggers when a color changes
	 * @param source {@link IColorProvider} whose color has changed
	 * @param oldColor old color
	 * @param newColor new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
	
}