package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.function.Supplier;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;

/**
 * A canvas on which the geometric shapes are drawn.
 * @author Matija
 *
 */
@SuppressWarnings("serial")
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	private Supplier<Tool> toolSupplier;
	private DrawingModel source;
	
	/**
	 * Initializes the canvas.
	 * @param toolSupplier {@link Supplier} of the currently selected {@link Tool}
	 * @param source currently used drawing model
	 */
	public JDrawingCanvas(Supplier<Tool> toolSupplier, DrawingModel source) {
		this.toolSupplier = toolSupplier;
		this.source = source;
		this.source.addDrawingModelListener(this);
		
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				toolSupplier.get().mouseReleased(e);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				toolSupplier.get().mousePressed(e);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				toolSupplier.get().mouseClicked(e);
			}
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				toolSupplier.get().mouseMoved(e);
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				toolSupplier.get().mouseDragged(e);
			}
		});
	}
	
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if(source==null) return;
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		GeometricalObjectVisitor painter = new GeometricalObjectPainter(g2d);
		
		for(int i=0, stop=source.getSize(); i<stop; i++)
			source.getObject(i).accept(painter);
		
		toolSupplier.get().paint(g2d);
	}

}
