package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * This class models a component for visualising and displaying bar charts.
 * @author Matija
 *
 */
public class BarChartComponent extends JComponent{
	
	private static final long serialVersionUID = 1L;
	private BarChart chart;
	private static final int border = 10;
	private static final int pointyEnd = 5;

	/**
	 * Creates and initialises a bar chart visualiser
	 * @param chart chart to be displayed
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}
	
	@Override
	protected void paintComponent(Graphics gOld) {
		Graphics2D g = (Graphics2D) gOld;
		
		g.setFont(g.getFont().deriveFont(12.5f));
		
		FontMetrics fm = g.getFontMetrics();

		//DRAWING TEXT DESC
		int xDescLength = fm.stringWidth(chart.getxDesc());
		g.drawString(chart.getxDesc(), (this.getWidth()-xDescLength)/2, this.getHeight()-border);
		
		AffineTransform transform = g.getTransform();
		g.setTransform(AffineTransform.getQuadrantRotateInstance(-1));
		int yDescLength = fm.stringWidth(chart.getyDesc());
		g.drawString(chart.getyDesc(), -(this.getHeight()-yDescLength)/2-yDescLength, border + fm.getAscent());
		g.setTransform(transform);
		
		//DRAWING AXIS
		int xStartXAxis = border + fm.getHeight() + border + fm.stringWidth(Integer.toString(chart.getMaxY())) + border;
		int yStartXAxis = (int) (this.getHeight()-border-fm.getHeight()-2*fm.getHeight());
		int xAxisLength = this.getWidth()-2*border - xStartXAxis;
		g.drawLine(xStartXAxis, yStartXAxis, xStartXAxis + xAxisLength, yStartXAxis);
		Polygon arrowHeadRight = new Polygon();
		arrowHeadRight.addPoint(this.getWidth()-2*border, yStartXAxis-5);
		arrowHeadRight.addPoint(this.getWidth()-2*border, yStartXAxis+5);
		arrowHeadRight.addPoint(this.getWidth()-2*border+5, yStartXAxis);
		g.fill(arrowHeadRight);
		
		int xStartYAxis = border + fm.getHeight() + border + fm.stringWidth(Integer.toString(chart.getMaxY())) + border + pointyEnd;
		int yStartYAxis = (int) (this.getHeight()-border-fm.getHeight()-2*fm.getHeight()) + pointyEnd;
		int yAxisLength = yStartYAxis - 2*border;
		g.drawLine(xStartYAxis, yStartYAxis, xStartYAxis, yStartYAxis - yAxisLength);
		Polygon arrowHeadUp = new Polygon();
		arrowHeadUp.addPoint(xStartYAxis-5, 2*border);
		arrowHeadUp.addPoint(xStartYAxis+5, 2*border);
		arrowHeadUp.addPoint(xStartYAxis, 2*border-5);
		g.fill(arrowHeadUp);
		
		//DRAWING GRID
		int countGridLinesHorizontal = (chart.getMaxY()-chart.getMinY())/chart.getDist();//not including x axis
		int countGridLinesVertical = chart.getList().size(); //not including y axis
		int spacingX = (xAxisLength-pointyEnd)/countGridLinesVertical;
		int spacingY = (yAxisLength-pointyEnd)/countGridLinesHorizontal;
		
		g.setColor(Color.LIGHT_GRAY);
		for(int i=1; i<=countGridLinesHorizontal; i++) {
			g.drawLine(xStartXAxis, yStartXAxis-i*spacingY, xStartXAxis + xAxisLength, yStartXAxis-i*spacingY);
		}
		for(int i=1; i<=countGridLinesVertical; i++) {
			g.drawLine(xStartYAxis+i*spacingX, yStartYAxis, xStartYAxis+i*spacingX, yStartYAxis-yAxisLength);
		}
		g.setColor(Color.BLACK);
		
		//DRAWING NUMBERS
		g.setFont(new Font(g.getFont().getName(), Font.BOLD, g.getFont().getSize()));
		int maxX = 0;
		for(var value : chart.getList()) {
			if(maxX<value.getX()) maxX=value.getX();
		}
		
		for(int i=0; i<maxX; i++) {
			int textStartX = xStartYAxis+spacingX/2-fm.stringWidth(Integer.toString(i+1))/2 + i*spacingX;
			int textStartY = yStartXAxis + fm.getHeight() + border;
			g.drawString(Integer.toString(i+1), textStartX, textStartY);
		}
		
		for(int j=chart.getMinY(), i=0; j<=chart.getMaxY(); j+=chart.getDist(), i++) {
			int textStartX = xStartYAxis - border - fm.stringWidth(Integer.toString(j));
			int textStartY = yStartXAxis + fm.getAscent()/2 - i*spacingY;
			g.drawString(Integer.toString(j), textStartX, textStartY);
		}
		
		//DRAWING BARS
		int originX = xStartYAxis, originY = yStartXAxis;
		g.setColor(Color.ORANGE);
		for(var value : chart.getList()) {
			int height = ((value.getY()-chart.getMinY())*spacingY)/chart.getDist();
			g.fillRect(originX+(value.getX()-1)*spacingX+border, originY-height, spacingX-border*2, height);
		}
	}
	
}
