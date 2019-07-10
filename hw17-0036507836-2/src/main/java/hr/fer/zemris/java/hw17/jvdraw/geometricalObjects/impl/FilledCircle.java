package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl;

import java.awt.BorderLayout;
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
 * Class that represents a filled circle.
 * @author Matija
 *
 */
public class FilledCircle extends GeometricalObject {
	
	private int x, y, r;
	private Color outerColor, fillColor;
	private String name;

	/**
	 * Initializes the filled circle.
	 * @param x x coordinate of the center
	 * @param y y coordinate of the center
	 * @param r radius of the circle
	 * @param outerColor color with which circle needs to be rendered
	 * @param fillColor color with which circle needs to be filled
	 */
	public FilledCircle(int x, int y, int r, Color outerColor, Color fillColor) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.outerColor = outerColor;
		this.fillColor = fillColor;
		this.name = String.format("Filled circle (%d,%d), %d, #%02X%02X%02X", x, y, r, 
				fillColor.getRed(),
				fillColor.getGreen(), 
				fillColor.getBlue());
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
	public Color getOuterColor() {
		return outerColor;
	}
	
	/**
	 * Returns the color with which circle needs to be rendered
	 * @return the color with which circle needs to be rendered
	 */
	public Color getFillColor() {
		return fillColor;
	}
	
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor();
	}
	
	@SuppressWarnings("serial")
	private class FilledCircleEditor extends GeometricalObjectEditor {

		private JTextField xJ, yJ, rJ;
		private JColorChooser chooserOuter, chooserFill;
		private int x, y, r;
		private Color outerColor, fillColor;
		
		public FilledCircleEditor() {
			xJ = new JTextField(Integer.toString(FilledCircle.this.x));
			yJ = new JTextField(Integer.toString(FilledCircle.this.y));
			rJ = new JTextField(Integer.toString(FilledCircle.this.r));
			chooserOuter = new JColorChooser(FilledCircle.this.outerColor);
			chooserFill = new JColorChooser(FilledCircle.this.fillColor);
			
			this.setLayout(new GridLayout(1, 2));
			
			JPanel panel = new JPanel(new GridLayout(0, 2));
			panel.add(new JLabel("X coordinate:"));
			panel.add(xJ);
			panel.add(new JLabel("Y coordinate:"));
			panel.add(yJ);
			panel.add(new JLabel("Radius:"));
			panel.add(rJ);
			this.add(panel);
			
			JPanel panel2 = new JPanel(new GridLayout(2, 1));
			
			JPanel panel3 = new JPanel(new BorderLayout());
			panel3.add(new JLabel("Outer color:"), BorderLayout.PAGE_START);
			panel3.add(chooserOuter, BorderLayout.CENTER);
			panel2.add(panel3);
			
			JPanel panel4 = new JPanel(new BorderLayout());
			panel4.add(new JLabel("Inner color:"), BorderLayout.PAGE_START);
			panel4.add(chooserFill, BorderLayout.CENTER);
			panel2.add(panel4);
			
			this.add(panel2);
			
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
			outerColor = chooserOuter.getColor();
			fillColor = chooserFill.getColor();
			if(outerColor==null) throw new RuntimeException();
			if(fillColor==null) throw new RuntimeException();
		}

		@Override
		public void acceptEditing() {
			checkEditing();
			
			FilledCircle.this.x = x;
			FilledCircle.this.y = y;
			FilledCircle.this.r = r;
			FilledCircle.this.outerColor = outerColor;
			FilledCircle.this.fillColor = fillColor;
			FilledCircle.this.name = 
					String.format("Filled circle (%d,%d), %d, #%02X%02X%02X", x, y, r, 
					fillColor.getRed(),
					fillColor.getGreen(), 
					fillColor.getBlue());
			
			FilledCircle.this.notifyListeners();
		}
		
	}
	
	@Override
	public String toString() {
		return name;
	}

}
