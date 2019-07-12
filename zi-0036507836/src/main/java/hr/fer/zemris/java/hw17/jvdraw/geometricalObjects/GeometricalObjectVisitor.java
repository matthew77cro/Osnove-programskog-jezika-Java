package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.FilledTriangle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.Line;

/**
 * Models a geometric object visitor
 * @author Matija
 *
 */
public interface GeometricalObjectVisitor {
	/**
	 * Method calls when a line is visited.
	 * @param line line that is being visited
	 */
	public abstract void visit(Line line);

	/**
	 * Method calls when a circle is visited.
	 * @param circle circle that is being visited
	 */
	public abstract void visit(Circle circle);

	/**
	 * Method calls when a filledCircle is visited.
	 * @param filledCircle filledCircle that is being visited
	 */
	public abstract void visit(FilledCircle filledCircle);
	
	public abstract void visit(FilledTriangle filledTriangle);
}
