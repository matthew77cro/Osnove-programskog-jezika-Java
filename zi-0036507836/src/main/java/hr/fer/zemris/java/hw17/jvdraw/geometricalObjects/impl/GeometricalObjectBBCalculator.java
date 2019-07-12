package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl;

import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObjectVisitor;

/**
 * Visitor used for calculating the bounding box of the image of {@link GeometricalObject}s
 * @author Matija
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	private int leftmostX;
	private int rightmostX;
	private int upperY;
	private int lowerY;
	
	private boolean firstVisit = true;
	
	public Rectangle getBoundingBox() {
		return new Rectangle(leftmostX, upperY, rightmostX-leftmostX+1, lowerY-upperY+1);
	}
	
	@Override
	public void visit(Line line) {
		int leftX = line.getX1() < line.getX2() ? line.getX1() : line.getX2();
		int rightX = line.getX1() > line.getX2() ? line.getX1() : line.getX2();
		int upY = line.getY1() < line.getY2() ? line.getY1() : line.getY2();
		int downY = line.getY1() > line.getY2() ? line.getY1() : line.getY2();
		
		if(firstVisit) {
			leftmostX = leftX;
			rightmostX = rightX;
			upperY = upY;
			lowerY = downY;
			firstVisit = false;
		} else {
			leftmostX = leftmostX < leftX ? leftmostX : leftX;
			rightmostX = rightmostX > rightX ? rightmostX : rightX;
			upperY = upperY < upY ? upperY : upY;
			lowerY = lowerY > downY ? lowerY : downY;
		}
	}

	@Override
	public void visit(Circle circle) {
		int leftX = circle.getX() - circle.getR();
		int rightX = circle.getX() + circle.getR();
		int upY = circle.getY() - circle.getR();
		int downY = circle.getY() + circle.getR();
		
		if(firstVisit) {
			leftmostX = leftX;
			rightmostX = rightX;
			upperY = upY;
			lowerY = downY;
			firstVisit = false;
		} else {
			leftmostX = leftmostX < leftX ? leftmostX : leftX;
			rightmostX = rightmostX > rightX ? rightmostX : rightX;
			upperY = upperY < upY ? upperY : upY;
			lowerY = lowerY > downY ? lowerY : downY;
		}
	}

	@Override
	public void visit(FilledCircle circle) {
		int leftX = circle.getX() - circle.getR();
		int rightX = circle.getX() + circle.getR();
		int upY = circle.getY() - circle.getR();
		int downY = circle.getY() + circle.getR();
		
		if(firstVisit) {
			leftmostX = leftX;
			rightmostX = rightX;
			upperY = upY;
			lowerY = downY;
			firstVisit = false;
		} else {
			leftmostX = leftmostX < leftX ? leftmostX : leftX;
			rightmostX = rightmostX > rightX ? rightmostX : rightX;
			upperY = upperY < upY ? upperY : upY;
			lowerY = lowerY > downY ? lowerY : downY;
		}
	}

	@Override
	public void visit(FilledTriangle t) {
		int leftX = t.getX0() < t.getX1() ? t.getX0() : t.getX1();
		leftX = t.getX2() < leftX ? t.getX2() : leftX;
		
		int rightX = t.getX0() > t.getX1() ? t.getX0() : t.getX1();
		rightX = t.getX2() > rightX ? t.getX2() : rightX;
		
		int up = t.getY0() < t.getY1() ? t.getY0() : t.getY1();
		up = t.getY2() < up ? t.getY2() : up;
		
		int down = t.getY0() > t.getY1() ? t.getY0() : t.getY1();
		down = t.getY2() > down ? t.getY2() : down;
		
		if(firstVisit) {
			leftmostX = leftX;
			rightmostX = rightX;
			upperY = up;
			lowerY = down;
			firstVisit = false;
		} else {
			leftmostX = leftmostX < leftX ? leftmostX : leftX;
			rightmostX = rightmostX > rightX ? rightmostX : rightX;
			upperY = upperY < up ? upperY : up;
			lowerY = lowerY > down ? lowerY : down;
		}
	}

}
