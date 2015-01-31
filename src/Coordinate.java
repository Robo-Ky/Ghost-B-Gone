/**
 * A coordinate holds a row/column pair for the grid's use when deleting matches
 * @author Carter
 *
 */
public class Coordinate {
	private int row, col;
	
	/**
	 * Create a new coordinate with the given row and column
	 * @param row
	 * @param col
	 */
	public Coordinate(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	/**
	 * @return the vertical position of this coordinate
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * @return the horizontal position of this coordinate
	 */
	public int getCol() {
		return col;
	}
	
	/**
	 * Print the coordinates
	 */
	public void printCoords() {
		System.out.print(row + "," + col + "; ");
		System.out.println();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Coordinate)) {return false;}
		if (o == this) {return true;}
		Coordinate c = (Coordinate)o;
		if (c.row == row && c.col == col) {return true;}
		return false;
	}
}
