package hr.fer.zemris.java.gui.charts;

/**
 * This class models an XY value for bar chart.
 * @author Matija
 *
 */
public class XYValue {
	
	private final int x;
	private final int y;

	/**
	 * Creates and initialises an XY value pair
	 * @param x x value
	 * @param y y value
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the x value
	 * @return the x value
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns the y value
	 * @return the y value
	 */
	public int getY() {
		return y;
	}

}
