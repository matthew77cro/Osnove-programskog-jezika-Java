package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.FilledTriangle;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;

public class FilledTriangleTool implements Tool {

	private DrawingModel model;
	private IColorProvider fgColor, bgColor;
	private JDrawingCanvas canvas;
	
	private int selectionProgressState = 0;
	private int x0, y0, x1, y1, x2, y2;

	public FilledTriangleTool(DrawingModel model, IColorProvider fgColor, IColorProvider bgColor, JDrawingCanvas canvas) {
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
		if(selectionProgressState==0) {
			x0 = e.getX();
			y0 = e.getY();
			selectionProgressState = 1;
		} else if(selectionProgressState==1) {
			x1 = e.getX();
			y1 = e.getY();
			selectionProgressState = 2;
		} else {
			x2 = e.getX();
			y2 = e.getY();
			model.add(new FilledTriangle(x0, y0, x1, y1, x2, y2, fgColor.getCurrentColor(), bgColor.getCurrentColor()));
			selectionProgressState = 0;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(selectionProgressState==1) {
			x1 = e.getX();
			y1 = e.getY();
			canvas.repaint();
		} else if(selectionProgressState==2) {
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

		if(selectionProgressState==1) {
			g2d.setColor(fgColor.getCurrentColor());
			g2d.drawLine(x0, y0, x1, y1);
		} else if(selectionProgressState==2) {
			g2d.setColor(bgColor.getCurrentColor());
			g2d.fillPolygon(new int[] {x0, x1, x2},new int[] {y0, y1, y2}, 3);
			g2d.setColor(fgColor.getCurrentColor());
			g2d.drawPolygon(new int[] {x0, x1, x2},new int[] {y0, y1, y2}, 3);
		}
		
	}

}
