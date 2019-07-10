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
 * Class that represents a line.
 * @author Matija
 *
 */
public class Line extends GeometricalObject {
	
	private int x1, x2, y1, y2;
	private Color color;
	private String name;

	/**
	 * Initializes the line
	 * @param x1 x coordinate of the starting point
	 * @param x2 x coordinate of the end point
	 * @param y1 y coordinate of the starting point
	 * @param y2 y coordinate of the end point
	 * @param color color with which the line needs to be rendered
	 */
	public Line(int x1, int x2, int y1, int y2, Color color) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.color = color;
		this.name = String.format("Line (%d,%d)-(%d,%d)", x1, y1, x2, y2);
	}
	
	/**
	 * Returns x coordinate of the starting point
	 * @return x coordinate of the starting point
	 */
	public int getX1() {
		return x1;
	}
	
	/**
	 * Returns x coordinate of the end point
	 * @return x coordinate of the end point
	 */
	public int getX2() {
		return x2;
	}
	
	/**
	 * Returns y coordinate of the starting point
	 * @return y coordinate of the starting point
	 */
	public int getY1() {
		return y1;
	}
	
	/**
	 * Returns y coordinate of the end point
	 * @return y coordinate of the end point
	 */
	public int getY2() {
		return y2;
	}
	
	/**
	 * Returns color with which the line needs to be rendered
	 * @return color with which the line needs to be rendered
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
		return new LineEditor();
	}
	
	@SuppressWarnings("serial")
	private class LineEditor extends GeometricalObjectEditor {

		private JTextField x1J, x2J, y1J, y2J;
		private JColorChooser chooser;
		private int x1, x2, y1, y2;
		private Color color;
		
		public LineEditor() {
			x1J = new JTextField(Integer.toString(Line.this.x1));
			x2J = new JTextField(Integer.toString(Line.this.x2));
			y1J = new JTextField(Integer.toString(Line.this.y1));
			y2J = new JTextField(Integer.toString(Line.this.y2));
			chooser = new JColorChooser(Line.this.color);
			
			this.setLayout(new GridLayout(2, 0));
			
			JPanel panel = new JPanel(new GridLayout(0, 2));
			panel.add(new JLabel("Start x coordinate:"));
			panel.add(x1J);
			panel.add(new JLabel("Start y coordinate:"));
			panel.add(y1J);
			panel.add(new JLabel("End x coordinate:"));
			panel.add(x2J);
			panel.add(new JLabel("End y coordinate:"));
			panel.add(y2J);
			
			this.add(panel);
			this.add(chooser);
		}
		
		@Override
		public void checkEditing() {
			try {
				x1 = Integer.parseInt(this.x1J.getText());
				y1 = Integer.parseInt(this.y1J.getText());
				x2 = Integer.parseInt(this.x2J.getText());
				y2 = Integer.parseInt(this.y2J.getText());
			} catch (Exception ex) {
				throw new RuntimeException();
			}
			color = chooser.getColor();
			if(color==null) throw new RuntimeException();
		}

		@Override
		public void acceptEditing() {
			checkEditing();
			
			Line.this.x1 = x1;
			Line.this.x2 = x2;
			Line.this.y1 = y1;
			Line.this.y2 = y2;
			Line.this.color = color;
			Line.this.name = String.format("Line (%d,%d)-(%d,%d)", x1, y1, x2, y2);
			
			Line.this.notifyListeners();
		}
		
	}
	
	@Override
	public String toString() {
		return name;
	}

}
