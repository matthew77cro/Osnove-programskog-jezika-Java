package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObjectVisitor;

/**
 * Class that represents a circle.
 * @author Matija
 *
 */
public class Circle extends GeometricalObject {
	
	private int x, y, r;
	private Color color;
	private String name;

	/**
	 * Initializes the circle.
	 * @param x x coordinate of the center
	 * @param y y coordinate of the center
	 * @param r radius of the circle
	 * @param color color with which circle needs to be rendered
	 */
	public Circle(int x, int y, int r, Color color) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.color = color;
		this.name = String.format("Circle (%d,%d), %d", x, y, r);
	}
	
	/**
	 * Returns the x coordinate of the center
	 * @return the x coordinate of the center
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the y coordinate of the center
	 * @return the y coordinate of the center
	 */
	public int getY() {
		return y;
	}

	/**
	 * Returns the radius of the circle
	 * @return the radius of the circle
	 */
	public int getR() {
		return r;
	}

	/**
	 * Returns the color with which circle needs to be rendered
	 * @return the color with which circle needs to be rendered
	 */
	public Color getColor() {
		return color;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor();
	}
	
	@SuppressWarnings("serial")
	private class CircleEditor extends GeometricalObjectEditor {

		private JTextField xJ, yJ, rJ;
		private JColorChooser chooser;
		private int x, y, r;
		private Color color;
		
		public CircleEditor() {
			xJ = new JTextField(Integer.toString(Circle.this.x));
			yJ = new JTextField(Integer.toString(Circle.this.y));
			rJ = new JTextField(Integer.toString(Circle.this.r));
			chooser = new JColorChooser(Circle.this.color);
			
			this.setLayout(new GridLayout(2, 1));
			
			JPanel panel = new JPanel(new GridLayout(0, 2));
			panel.add(new JLabel("X coordinate:"));
			panel.add(xJ);
			panel.add(new JLabel("Y coordinate:"));
			panel.add(yJ);
			panel.add(new JLabel("Radius:"));
			panel.add(rJ);
			this.add(panel);
			
			this.add(chooser);
		}
		
		@Override
		public void checkEditing() {
			try {
				x = Integer.parseInt(this.xJ.getText());
				y = Integer.parseInt(this.yJ.getText());
				r = Integer.parseInt(this.rJ.getText());
			} catch (Exception ex) {
				throw new RuntimeException();
			}
			if(r<=0) throw new IllegalArgumentException();
			color = chooser.getColor();
			if(color==null) throw new RuntimeException();
		}

		@Override
		public void acceptEditing() {
			checkEditing();
			
			Circle.this.x = x;
			Circle.this.y = y;
			Circle.this.r = r;
			Circle.this.color = color;
			Circle.this.name = String.format("Circle (%d,%d), %d", x, y, r);
			
			Circle.this.notifyListeners();
		}
		
	}
	
	@Override
	public String toString() {
		return name;
	}

}
