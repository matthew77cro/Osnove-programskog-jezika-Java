package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObjectVisitor;

/**
 * Visitor used for painting the geometric object onto the {@link Graphics2D}
 * @author Matija
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	private Graphics2D g2d;
	
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}
	
	@Override
	public void visit(Line line) {
		g2d.setColor(line.getColor());
		g2d.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
	}

	@Override
	public void visit(Circle circle) {
		g2d.setColor(circle.getColor());
		g2d.drawOval(circle.getX()-circle.getR(), circle.getY()-circle.getR(), circle.getR()*2, circle.getR()*2);
	}

	@Override
	public void visit(FilledCircle circle) {
		g2d.setColor(circle.getFillColor());
		g2d.fillOval(circle.getX()-circle.getR(), circle.getY()-circle.getR(), circle.getR()*2, circle.getR()*2);
		g2d.setColor(circle.getOuterColor());
		g2d.drawOval(circle.getX()-circle.getR(), circle.getY()-circle.getR(), circle.getR()*2, circle.getR()*2);
	}

	@Override
	public void visit(FilledTriangle t) {
		g2d.setColor(t.getFillColor());
		g2d.fillPolygon(new int[] {t.getX0(), t.getX1(), t.getX2()}, new int[] {t.getY0(), t.getY1(), t.getY2()}, 3);
		g2d.setColor(t.getOuterColor());
		g2d.drawPolygon(new int[] {t.getX0(), t.getX1(), t.getX2()}, new int[] {t.getY0(), t.getY1(), t.getY2()}, 3);
	}

}
