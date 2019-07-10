package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Model a state (position, direction, color etc.) of a turtle that is used for drawing
 * Lindenmayer systems of fractals.
 * @author Matija
 *
 */
public class TurtleState {
	
	private Vector2D position;
	private Vector2D direction;
	private Color color;
	private double step;
	
	/**
	 * Creates and initialises new instance of a TuretleState.
	 * @param position initial turtle position
	 * @param direction initial turtle direction
	 * @param color initial turtle color
	 * @param step initial turtle step length
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double step) {
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.step = step;
	}

	/**
	 * Returns a new turtle state with same parameters as this instance.
	 * @return a new turtle state with same parameters
	 */
	public TurtleState copy() {
		return new TurtleState(position.copy(), direction.copy(), color, step);
	}

	/**
	 * Returns the turtle position
	 * @return the turtle position
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Returns the turtle direction
	 * @return the turtle direction
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Returns the turtle color
	 * @return the turtle color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Returns the turtle step length
	 * @return the turtle step length
	 */
	public double getStep() {
		return step;
	}

	/**
	 * Sets the turtle position.
	 * @param position new position of the turtle
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * Sets the turtle direction.
	 * @param position new direction of the turtle
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Sets the turtle color.
	 * @param position new color of the turtle
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Sets the turtle step length.
	 * @param position new step length of the turtle
	 */
	public void setStep(double step) {
		this.step = step;
	}

}
