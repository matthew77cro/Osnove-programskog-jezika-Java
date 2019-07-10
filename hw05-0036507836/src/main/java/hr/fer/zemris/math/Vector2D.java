package hr.fer.zemris.math;

/**
 * A 2 element vector that is represented by double precision floating point x,y coordinates.
 * 
 * @author Matija
 *
 */
public class Vector2D {
	
	private double x;
	private double y;

	/**
	 * Constructs and initialises a Vector2D from the specified xy coordinates.
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns x coordinate of the vector.
	 * @return x coordinate of the vector
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Returns y coordinate of the vector.
	 * @return y coordinate of the vector
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Translates the vector along a given vector
	 * @param offset translation vector
	 */
	public void translate(Vector2D offset) {
		if(offset==null) throw new NullPointerException();
		
		this.x += offset.x;
		this.y += offset.y;
	}
	
	/**
	 * Translates the vector along a given vector
	 * @param offset translation vector
	 * @return translated vector
	 */
	public Vector2D translated(Vector2D offset) {
		if(offset==null) throw new NullPointerException();
		
		Vector2D newVector = this.copy();
		newVector.translate(offset);
		return newVector;
	}
	
	/**
	 * Rotates the vector for a given angle
	 * @param offset rotation angle
	 */
	public void rotate(double angle) {
		double oldX = this.x;
		double oldY = this.y;
		this.x = oldX * Math.cos(angle) - oldY * Math.sin(angle);
	    this.y = oldX * Math.sin(angle) + oldY * Math.cos(angle);
	}
	
	/**
	 * Rotates the vector for a given angle
	 * @param offset rotation angle
	 * @return rotated vector
	 */
	public Vector2D rotated(double angle) {
		Vector2D newVector = this.copy();
		newVector.rotate(angle);
		return newVector;
	}
	
	/**
	 * Scales the vector for a given scalar
	 * @param offset scaler for which the vector is to be scaled
	 */
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}
	
	/**
	 * Scales the vector for a given scalar
	 * @param offset scaler for which the vector is to be scaled
	 * @return scaled vector
	 */
	public Vector2D scaled(double scaler) {
		Vector2D newVector = this.copy();
		newVector.scale(scaler);
		return newVector;
	}
	
	/**
	 * Returns a new copy of the vector.
	 * @return a new copy of the vector
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}

	@Override
	public String toString() {
		return "Vector2D [x=" + x + ", y=" + y + "]";
	}
	
}
