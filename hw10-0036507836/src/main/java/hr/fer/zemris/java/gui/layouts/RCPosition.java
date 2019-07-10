package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * Models a 2D coordinate for calc layout
 * @author Matija
 *
 */
public class RCPosition {
	public int row;
	public int column;
	
	protected static RCPosition first = new RCPosition(1, 1);
	
	/**
	 * Creates a position at a given row and column
	 * @param row row
	 * @param column column
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Returns the row of the position
	 * @return the row of the position
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the column of the position
	 * @return the column of the position
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		return Objects.hash(column, row);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		return column == other.column && row == other.row;
	}
}