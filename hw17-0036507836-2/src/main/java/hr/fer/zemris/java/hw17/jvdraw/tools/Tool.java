package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Tool models a state in a state design pattern. This interface is used for
 * rendering objects on the screen.
 * @author Matija
 *
 */
public interface Tool {
	/**
	 * Called when mouse press event occurs
	 * @param e mouse event
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * Called when mouse release event occurs
	 * @param e mouse event
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Called when mouse click event occurs
	 * @param e mouse event
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Called when mouse move event occurs
	 * @param e mouse event
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Called when mouse drag event occurs
	 * @param e mouse event
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Method used for painting temporary selections on the given {@link Graphics2D}
	 * @param g2d {@link Graphics2D} on which to render the temporary selections
	 */
	public void paint(Graphics2D g2d);
}