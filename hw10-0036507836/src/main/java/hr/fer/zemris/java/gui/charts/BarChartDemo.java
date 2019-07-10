package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Models a desktop application for displaying bar charts. Program requires just one argument (path to
 * a file containing chart data). <br><br>
 * File must be structured as follows:<br>
 * 1. line - x axis name<br>
 * 2. line - y axis name<br>
 * 3. line - XY values (x1,y1 x2,y2 x3,y3 ...)<br>
 * 4. line - minimal y value for display<br>
 * 5. line - maximal y value for display<br>
 * 6. line - distance between two y values displayed<br>
 * all other lines are ignored<br>
 * @author Matija
 */
public class BarChartDemo extends JFrame{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates and initialises a bar chart visualiser
	 * @param model model to be displayed
	 * @param file path to the file with chart data
	 */
	public BarChartDemo(BarChart model, String file) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 500);
		
		setLayout(new BorderLayout());
		add(new JLabel(file, SwingConstants.CENTER), BorderLayout.PAGE_START);
		add(new BarChartComponent(model), BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		
		if(args.length!=1) {
			System.err.println("Program takes one argument!");
		}
		
		Path p;
		List<String> lines;
		try {
			p = Paths.get(args[0]);
			lines = Files.readAllLines(p);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}
		
		BarChart model;
		try {
			model = parse(lines);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}
		
		SwingUtilities.invokeLater(() -> {
			BarChartDemo frame = new BarChartDemo(model, p.toAbsolutePath().toString());
			frame.setVisible(true);
		});
	}
	
	/**
	 * Parses file containing chart data. Creates and returns bar chart
	 * of that data.
	 * @param lines lines in a file
	 * @return bar chart created from data in a file
	 */
	private static BarChart parse(List<String> lines) {
		String[] XY = lines.get(2).split("\\s+");
		List<XYValue> values = new ArrayList<>();
		
		for(String s : XY) {
			String[] splitted = s.split(",");
			values.add(new XYValue(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1])));
		}
		
		return new BarChart(values, lines.get(0), lines.get(1), 
				Integer.parseInt(lines.get(3)), 
				Integer.parseInt(lines.get(4)), 
				Integer.parseInt(lines.get(5)));	
	}

}
