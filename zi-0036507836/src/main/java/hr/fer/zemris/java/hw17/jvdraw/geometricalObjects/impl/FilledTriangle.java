package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObjectVisitor;

/**
 * Class that represents a filled circle.
 * @author Matija
 *
 */
public class FilledTriangle extends GeometricalObject {
	
	private int x0, y0, x1, y1, x2, y2;
	private Color outerColor, fillColor;
	private String name;

		
	
	public FilledTriangle(int x0, int y0, int x1, int y1, int x2, int y2, Color outerColor, Color fillColor) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.outerColor = outerColor;
		this.fillColor = fillColor;
		this.name = String.format("FT (%d,%d), (%d,%d), (%d,%d) #%02X%02X%02X", x0, y0, x1, y1, x2, y2, 
				fillColor.getRed(),
				fillColor.getGreen(), 
				fillColor.getBlue());
	}
	
	public int getX0() {
		return x0;
	}
	
	public int getY0() {
		return y0;
	}

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}

	public int getX2() {
		return x2;
	}

	public int getY2() {
		return y2;
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
		return new FilledTriangleEditor();
	}
	
	@SuppressWarnings("serial")
	private class FilledTriangleEditor extends GeometricalObjectEditor {

		private JColorChooser chooserOuter, chooserFill;
		private Color outerColor, fillColor;
		
		public FilledTriangleEditor() {
			chooserOuter = new JColorChooser(FilledTriangle.this.outerColor);
			chooserFill = new JColorChooser(FilledTriangle.this.fillColor);
			
			this.setLayout(new GridLayout(1, 2));
			
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
			outerColor = chooserOuter.getColor();
			fillColor = chooserFill.getColor();
			if(outerColor==null) throw new RuntimeException();
			if(fillColor==null) throw new RuntimeException();
		}

		@Override
		public void acceptEditing() {
			checkEditing();
			
			FilledTriangle.this.outerColor = outerColor;
			FilledTriangle.this.fillColor = fillColor;
			FilledTriangle.this.name = 
					String.format("FT (%d,%d), (%d,%d), (%d,%d) #%02X%02X%02X", x0, y0, x1, y1, x2, y2, 
							fillColor.getRed(),
							fillColor.getGreen(), 
							fillColor.getBlue());
			
			FilledTriangle.this.notifyListeners();
		}
		
	}
	
	@Override
	public String toString() {
		return name;
	}

}
