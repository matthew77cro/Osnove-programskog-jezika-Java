package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Models a layout for calculator app. Layout is a grid with 5 rows and 7 columns. Object
 * in cell (1,1) stretches across all the way to (1,5).
 * @author Matija
 *
 */
public class CalcLayout implements LayoutManager2{
	
	private static final int ROWS = 5;
	private static final int COLUMNS = 7;
	private static final int DEFAULT_SPACING = 0;
	
	private int spacing;
	private Map<RCPosition, Component> layout;

	/**
	 * Creates and initialises a calc layout with default spacing (0)
	 */
	public CalcLayout() {
		this(DEFAULT_SPACING);
	}
	
	/**
	 * Creates and initialises a calc layout with custom spacing
	 * @param spacing spacing between cells
	 */
	public CalcLayout(int spacing) {
		this.spacing = spacing;
		this.layout = new HashMap<RCPosition, Component>();
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(!(constraints instanceof RCPosition)) throw new UnsupportedOperationException();
		
		RCPosition position = (RCPosition) constraints;
		if(position.row<1 || position.row>5 || position.column<1 || position.column>7) 
			throw new UnsupportedOperationException();
		if(position.row == 1 && (position.column>=2 && position.column<=5)) throw new UnsupportedOperationException();
	
		if(layout.containsKey(position)) throw new UnsupportedOperationException();
		
		layout.put(position, comp);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		var layoutList = layout.values();
		layoutList.removeIf((component) -> component.equals(comp));
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = layoutSize(parent, (comp) -> comp.getPreferredSize(), false);
		return dim;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Dimension dim = layoutSize(parent, (comp) -> comp.getMinimumSize(), false);
		return dim;
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		Dimension dim = layoutSize(target, (comp) -> comp.getMaximumSize(), true);
		return dim;
	}
	
	private Dimension layoutSize(Container cont, Function<Component, Dimension> getDimension, boolean maximum) {
		int componentWidth = 0, componentHeight = 0;
		Component[] components = cont.getComponents();
		for(Component c : components) {
			Dimension d = getDimension.apply(c);
			if ((componentWidth<d.width && !maximum) || (componentWidth>d.width && maximum)) componentWidth = d.width;
			if ((componentHeight<d.height && !maximum) || (componentHeight>d.height && maximum)) componentHeight = d.height;
		}
		int w = componentWidth * COLUMNS + spacing * (COLUMNS-1);
		int h = componentHeight * ROWS + spacing * (ROWS-1);
		
		return new Dimension(w, h);
	}
	
	@Override
	public void layoutContainer(Container parent) {
		double componentWidth = (parent.getWidth() - spacing * (COLUMNS-1))/(COLUMNS);
		double componentHeight = (parent.getHeight() - spacing * (ROWS-1))/(ROWS);
		
		int widths[] = new int[COLUMNS];
		int heights[] = new int[ROWS];
		
		int width = (int) componentWidth;
		int widthRemainder = parent.getWidth() - (width*COLUMNS + spacing*(COLUMNS-1));
		int height = (int) componentHeight;
		int heightRemainder = parent.getHeight() - (height*ROWS + spacing*(ROWS-1));
		
		for(var entry : layout.entrySet()) {			
			double cmpWidth = componentWidth;
			
			if(entry.getKey().equals(RCPosition.first)) {
				cmpWidth*=5;
				cmpWidth+=4*spacing;
			}
			
			entry.getValue().setBounds(((int)componentWidth+spacing)*(entry.getKey().column-1), 
									   ((int)componentHeight+spacing)*(entry.getKey().row-1),
									   (int) cmpWidth, 
									   (int) componentHeight);
		}
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub
		
	}

}
