package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.Line;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;

public class LineTool implements Tool {
	
	private DrawingModel model;
	private IColorProvider fgColor;
	private JDrawingCanvas canvas;
	
	private boolean selectionInProgress = false;
	private int x1, y1, x2, y2;

	public LineTool(DrawingModel model, IColorProvider fgColor, JDrawingCanvas canvas) {
		this.model = model;
		this.fgColor = fgColor;
		this.canvas = canvas;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//DO NOTHING
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//DO NOTHING
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!selectionInProgress) {
			x1 = e.getX();
			y1 = e.getY();
			selectionInProgress = true;
		} else {
			x2 = e.getX();
			y2 = e.getY();
			selectionInProgress = false;
			model.add(new Line(x1, x2, y1, y2, fgColor.getCurrentColor()));
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(selectionInProgress) {
			x2 = e.getX();
			y2 = e.getY();
			canvas.repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//DO NOTHING
	}

	@Override
	public void paint(Graphics2D g2d) {

		if(selectionInProgress) {
			g2d.setColor(fgColor.getCurrentColor());
			g2d.drawLine(x1, y1, x2, y2);
		}
		
	}

}
