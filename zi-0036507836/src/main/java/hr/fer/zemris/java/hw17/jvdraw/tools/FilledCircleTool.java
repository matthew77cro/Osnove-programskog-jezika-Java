package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;

public class FilledCircleTool implements Tool {

	private DrawingModel model;
	private IColorProvider fgColor, bgColor;
	private JDrawingCanvas canvas;
	
	private boolean selectionInProgress = false;
	private int x, y, r;

	public FilledCircleTool(DrawingModel model, IColorProvider fgColor, IColorProvider bgColor, JDrawingCanvas canvas) {
		this.model = model;
		this.fgColor = fgColor;
		this.bgColor = bgColor;
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
			x = e.getX();
			y = e.getY();
			selectionInProgress = true;
		} else {
			r = (int) Math.round(Math.sqrt(Math.pow(e.getX() - x, 2) + Math.pow(e.getY() - y, 2)));
			selectionInProgress = false;
			model.add(new FilledCircle(x, y, r, fgColor.getCurrentColor(), bgColor.getCurrentColor()));
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(selectionInProgress) {
			r = (int) Math.round(Math.sqrt(Math.pow(e.getX() - x, 2) + Math.pow(e.getY() - y, 2)));
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
			g2d.setColor(bgColor.getCurrentColor());
			g2d.fillOval(x - r, y - r, r*2, r*2);
			g2d.setColor(fgColor.getCurrentColor());
			g2d.drawOval(x - r, y - r, r*2, r*2);
		}
		
	}

}
