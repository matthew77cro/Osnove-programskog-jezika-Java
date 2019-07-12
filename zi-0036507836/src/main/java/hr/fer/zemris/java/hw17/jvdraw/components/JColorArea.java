package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * A Java Swing component for picking the color.
 * @author Matija
 *
 */
@SuppressWarnings("serial")
public class JColorArea extends JComponent implements IColorProvider {
	
	private Color selectedColor;
	private List<ColorChangeListener> listeners;
	
	/**
	 * Initializes the component with initial color.
	 * @param selectedColor initial color.
	 */
	public JColorArea(Color selectedColor) {
		super();
		this.selectedColor = selectedColor;
		this.listeners = new ArrayList<>();
		init();
	}
	
	/**
	 * Initializes the component
	 */
	private void init() {
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color oldColor = JColorArea.this.selectedColor;
				Color selectedColor = JColorChooser.showDialog(JColorArea.this, "Color chooser", JColorArea.this.selectedColor);
				if(selectedColor!=null) {
					JColorArea.this.selectedColor = selectedColor;
					notifyListeners(oldColor, selectedColor);
					repaint();
				}
			}
			
		});
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(selectedColor);
		g2d.fillRect(getInsets().left, getInsets().top, getSize().width, getSize().height);
		
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Notifies all listeners that a color has been changed.
	 * @param oldColor old color
	 * @param newColor new color
	 */
	private void notifyListeners(Color oldColor, Color newColor) {
		for(ColorChangeListener l : listeners) {
			l.newColorSelected(this, oldColor, newColor);
		}
	}

}
