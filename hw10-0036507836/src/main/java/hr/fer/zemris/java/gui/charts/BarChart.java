package hr.fer.zemris.java.gui.charts;

import java.util.List;
import java.util.Objects;

/**
 * This class models a simple bar chart for storing and later displaying that data.
 * @author Matija
 *
 */
public class BarChart {
	
	private List<XYValue> list;
	private String xDesc;
	private String yDesc;
	private int minY; 
	private int maxY;
	private int dist;
	
	/**
	 * Creates and initialises this chart.
	 * @param list list of XY values in the graph
	 * @param xDesc descriptive name for x axis
	 * @param yDesc descriptive name for y axis
	 * @param minY minimal value for y axis
	 * @param maxY maximal value for y axis
	 * @param dist distance between two points on the y axis
	 */
	public BarChart(List<XYValue> list, String xDesc, String yDesc, int minY, int maxY, int dist) {
		if(minY<0) throw new IllegalArgumentException("Min y cannot be negative!");
		if(maxY<=minY) throw new IllegalArgumentException("Max y must be > than min y!");
		
		for(var value : list) {
			if(value.getY()<minY) throw new IllegalArgumentException("All y values in the list must be >= to yMin!");
		}
		
		this.list = Objects.requireNonNull(list);
		this.xDesc = Objects.requireNonNull(xDesc);
		this.yDesc = Objects.requireNonNull(yDesc);
		this.minY = minY;
		this.maxY = maxY;
		this.dist = dist;
	}

	/**
	 * Returns list of XY values in this graph
	 * @return list of XY values in this graph
	 */
	public List<XYValue> getList() {
		return list;
	}

	/**
	 * Returns descriptive name for x axis
	 * @return descriptive name for x axis
	 */
	public String getxDesc() {
		return xDesc;
	}

	/**
	 * Returns descriptive name for y axis
	 * @return descriptive name for y axis
	 */
	public String getyDesc() {
		return yDesc;
	}

	/**
	 * Returns minY minimal value for y axis
	 * @return minY minimal value for y axis
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Returns maxY maximal value for y axis
	 * @return maxY maximal value for y axis
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * Returns distance between two points on the y axis
	 * @return distance between two points on the y axis
	 */
	public int getDist() {
		return dist;
	}

}
