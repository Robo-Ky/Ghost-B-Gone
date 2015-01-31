import java.util.List;

/**
 * A grid keeps track of the layout of the ghosts in the current game, matching them when called to
 * 
 * @author Carter
 * 
 */
public class Grid {
	/** The array housing the spaces which hold the ghosts */
	private Space[][] grid;
	private Row[] rows;
	private Column[] columns;
	/** Keeps track of the existence and position of a special ghost that takes up two rows and columns */
	private boolean specter = false;
	private int specRow = 0, specCol = 0;
	
	/**
	 * Creates a new grid with no layout of ghosts passed in
	 */
	public Grid() {
		grid = new Space[10][10];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new Space();
			}
		}
		rows = new Row[10];
		for (int i = 0; i < rows.length; i++) {
			rows[i] = new Row();
			rows[i].updateRow(i);
		}
		columns = new Column[10];
		for (int i = 0; i < columns.length; i++) {
			columns[i] = new Column();
			columns[i].updateColumn(i);
		}
	}
	
	/**
	 * Creates a new grid from a layout of ghosts
	 * @param layout a premade 2-d grid of ghosts
	 */
	public Grid(Space[][] layout) {
		grid = new Space[10][10];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new Space();
			}
		}
		rows = new Row[10];
		for (int i = 0; i < rows.length; i++) {
			rows[i] = new Row();
			rows[i].updateRow(i);
		}
		columns = new Column[10];
		for (int i = 0; i < columns.length; i++) {
			columns[i] = new Column();
			columns[i].updateColumn(i);
		}
		if (layout.length == 10 && layout[0].length == 10) {
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					if (layout[i][j].hasGhost()) {
						add(layout[i][j].get(), i, j);
					}
				}
			}
		}
	}
	
	/**
	 * Adds a new normal-sized ghost to the grid
	 * @param g the ghost to be added
	 * @param row the row to add the ghost to
	 * @param col the column to add the ghost to
	 * @return whether a ghost was successfully added
	 */
	public boolean add(Ghost g, int row, int col) {
		if (g != null) {
			if (!grid[row][col].hasGhost()) {
				grid[row][col].addGhost(g);
				rows[row].updateRow(row);
				columns[col].updateColumn(col);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds a new 2x2 specter to the grid
	 * @param g the specter to be added
	 * @param row the row of the top left space to add to
	 * @param col the column of the top left space to add to
	 * @return whether a ghost was successfully added
	 */
	public boolean addSpecter(Ghost g, int row, int col) {
		if (g != null) {
			for (int i = row; i < 2; i++) {
				for (int j = col; i < 2; i++) {
					if (i < 10 && j < 10 && grid[i][j].hasGhost()) {
						return false;
					}
				}
			}
			grid[row][col].addGhost(g); grid[row+1][col].setSpecter(g);
			grid[row][col+1].setSpecter(g); grid[row+1][col+1].setSpecter(g);
			rows[row].setSpecter(true); rows[row+1].setSpecter(true);
			columns[col].setSpecter(true); columns[col+1].setSpecter(true);
			rows[row].updateRow(row); rows[row+1].updateRow(row+1);
			columns[col].updateColumn(col); columns[col+1].updateColumn(col+1);
			specter = true; specRow = row; specCol = col;
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the column specified by the index passed in
	 * @param col the column number to return
	 * @return the column at the index
	 */
	public Space[] getColumn(int col) {
		return columns[col].spaces;
	}
	
	/**
	 * Gets the array backing up the grid
	 * @return the backing array
	 */
	public Space[][] getGrid() {
		return grid;
	}
	
	/**
	 * Gets the row specified by the index passed in
	 * @param row the row number to return
	 * @return the row at the index
	 */
	public Space[] getRow(int row) {
		return rows[row].spaces;
	}
	
	/**
	 * Checks if a specter is currently in the grid
	 * @return whether a specter is present
	 */
	public boolean hasSpecter() {
		return specter;
	}
	
	/**
	 * Highlights the location of the cursor, and the corresponding row and column
	 */
	public void highlight() {
		
	}
	
	/**
	 * Creates matches of 2 ghosts or more, combines any intersecting matches, and remove them from the grid
	 * Called when a chain of matches has already begun
	 */
	public void matchingTwo() {
		Matcher m = new Matcher();
		for (int i = 0; i < 10; i++) {
			m.matchRowTwo(i);
			m.matchColumnTwo(i);
		}
		//m.merging();
		m.destruction();
	}
	
	/**
	 * Creates matches of 3 ghosts or more, combines any intersecting matches, and removes them from the grid
	 * Called when no match chain is active, thereby creating a chain
	 */
	public void matchingThree() {
		Matcher m = new Matcher();
		for (int i = 0; i < 10; i++) {
			m.matchRowThree(i);
			m.matchColumnThree(i);
		}
		//m.merging(); //currently buggy
		m.destruction();
	}
	
	public void printGrid(Space[][] a) {
		   for(int i = 0; i < a.length; i++)
		   {
		      for(int j = 0; j < a[i].length; j++)
		      {
		         System.out.print(a[i][j].getColor());
		      }
		      System.out.println();
		   }
		   System.out.println();
		}
	
	/**
	 * Draws each ghost in its position on the grid
	 */
	public void render() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				grid[i][j].drawGhost(i,j);
			}
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				grid[i][j].drawGhost(i,j);
			}
		}
	}
	
	/**
	 * Slides each ghost in the specified column down one space
	 * @param col the column to slide
	 */
	public void slideDown(int col) {
		columns[col].slideDown();
	}
	
	/**
	 * Slides each ghost in the specified row left one space
	 * @param row the row to slide
	 */
	public void slideLeft(int row) {
		rows[row].slideLeft();
	}
	
	/**
	 * Slides each ghost in the specified row right one space
	 * @param row the row to slide
	 */
	public void slideRight(int row) {
		rows[row].slideRight();
	}
	
	/**
	 * Slides each ghost in the specified column up one space
	 * @param col the column to slide
	 */
	public void slideUp(int col) {
		columns[col].slideUp();
	}
	
	/**
	 * A vertical arrangement of ghosts for easy moving and matching
	 * @author Carter
	 *
	 */
	private class Column {
		private Space[] spaces = new Space[10];
		int index;
		boolean specter = false;
		
		/**
		 * Sets whether a specter is now in this column
		 * @param specter
		 */
		private void setSpecter(boolean specter) {
			this.specter = specter;
		}
		
		/**
		 * If there is not a specter in this column, moves the content of each space down 1, moves the bottom space to the top
		 */
		private void slideDown() {
			if (!specter) {
				Space temp = spaces[spaces.length - 1];
				for (int i = spaces.length - 1; i > 0; i--) {
					spaces[i] = spaces[i - 1];
				}
				spaces[0] = temp;
				for (int i = 0; i < spaces.length; i++) {
					grid[i][index] = spaces[i];
				}
			}
			else {
				System.out.println("blocked by specter");
			}
		}
		
		/**
		 * If there is not a specter in this column, moves the content of each space up 1, moves the top space to the bottom
		 */
		private void slideUp() {
			if (!specter) {
				Space temp = spaces[0];
				for (int i = 1; i < spaces.length; i++) {
					spaces[i - 1] = spaces[i];
				}
				spaces[spaces.length - 1] = temp;
				for (int i = 0; i < spaces.length; i++) {
					grid[i][index] = spaces[i];
				}
			}
			else {
				System.out.println("blocked by specter");
			}
		}
		
		/**
		 * Sets this column's contents to the right spaces in the backing array
		 * @param index the column index of the array this object represents
		 * @return the updated spaces of this column
		 */
		private Space[] updateColumn(int index) {
			for (int i = 0; i < spaces.length; i++) {
				spaces[i] = grid[i][index];
			}
			this.index = index;
			return spaces;
		}
	}
	
	/**
	 * A horizontal arrangement of ghosts for easy moving and matching
	 * @author Carter
	 *
	 */
	private class Row {
		private Space[] spaces = new Space[10];
		int index;
		boolean specter = false;
		
		/**
		 * Sets whether a specter is now in this column
		 * @param specter
		 */
		private void setSpecter(boolean specter) {
			this.specter = specter;
		}
		
		/**
		 * If there is not a specter in this row, moves the content of each space left 1, moves the leftmost space to the rightmost
		 */
		private void slideLeft() {
			if (!specter) {
				Space temp = spaces[0];
				for (int i = 1; i < spaces.length; i++) {
					spaces[i - 1] = spaces[i];
				}
				spaces[spaces.length - 1] = temp;
				grid[index] = spaces;
			}
			else {
				System.out.println("blocked by specter");
			}
		}
		
		/**
		 * If there is not a specter in this row, moves the content of each space right 1, moves the rightmost space to the leftmost
		 */
		private void slideRight() {
			if (!specter) {
				Space temp = spaces[spaces.length - 1];
				for (int i = spaces.length - 1; i > 0; i--) {
					spaces[i] = spaces[i - 1];
				}
				spaces[0] = temp;
				grid[index] = spaces;
			}
			else {
				System.out.println("blocked by specter");
			}
		}
		
		/**
		 * Sets this row's contents to the right spaces in the backing array
		 * @param index the row index of the array this object represents
		 * @return the updated spaces of this row
		 */
		private Space[] updateRow(int index) {
			spaces = grid[index];
			this.index = index;
			return spaces;
		}
	}
	
	/**
	 * A matcher goes through the contents of the grid, matching the ghosts by rows and columns and removes any matches that are long enough
	 * @author Carter
	 *
	 */
	private class Matcher {
		/** hMatches keeps the matches made from rows, vMatches from columns, tMatches holds matches combined from horizontal and vertical matches*/
		private List<Match> hMatches = new java.util.ArrayList<Match>();
		private List<Match> vMatches = new java.util.ArrayList<Match>();
		private List<Match> tMatches = new java.util.ArrayList<Match>();
		
		/**
		 * Creates matches of length 2 or longer from the given row
		 * @param row
		 */
		private void matchRowTwo(int row) {
			Row matching = rows[row];
			int i = 0;
			while (i < 9) {
				Space start = matching.spaces[i];
				List<Coordinate> matched = new java.util.ArrayList<Coordinate>();
				matched.add(new Coordinate(row, i));
				Space current = matching.spaces[++i];
				while (start.match(current) && i < 9) {
					matched.add(new Coordinate(row, i));
					if (i < 9) {
						current = matching.spaces[++i];
					}
				}
				if (i == 9) {
					if (start.match(matching.spaces[i])) {
						matched.add(new Coordinate(row, i));
					}
				}
				if (matched.size() > 1) {
					hMatches.add(new Match(matched));
				}
			}
		}
		
		/**
		 * Creates matches of length 3 or longer from the given row
		 * @param row
		 */
		private void matchRowThree(int row) {
			Row matching = rows[row];
			int i = 0;
			while (i < 9) {
				Space start = matching.spaces[i];
				List<Coordinate> matched = new java.util.ArrayList<Coordinate>();
				matched.add(new Coordinate(row, i));
				Space current = matching.spaces[++i];
				if (start.match(current) && i < 9) {
					matched.add(new Coordinate(row, i));
					current = matching.spaces[++i];
					while (start.match(current) && i < 9) {
						matched.add(new Coordinate(row, i));
						if (i < 9) {
							current = matching.spaces[++i];
						}
					}
					if (i == 9) {
						if (start.match(matching.spaces[i])) {
							matched.add(new Coordinate(row, i));
						}
					}
				}
				if (matched.size() > 2) {
					hMatches.add(new Match(matched));
				}
			}
		}
		
		/**
		 * Creates matches of length 2 or longer from the given column
		 * @param col
		 */
		private void matchColumnTwo(int col) {
			Column matching = columns[col];
			int i = 0;
			while (i < 9) {
				Space start = matching.spaces[i];
				List<Coordinate> matched = new java.util.ArrayList<Coordinate>();
				matched.add(new Coordinate(i, col));
				Space current = matching.spaces[++i];
				while (start.match(current) && i < 9) {
					matched.add(new Coordinate(i, col));
					if (i < 9) {
						current = matching.spaces[++i];
					}
				}
				if (i == 9) {
					if (start.match(matching.spaces[i])) {
						matched.add(new Coordinate(i, col));
					}
				}
				if (matched.size() > 1) {
					vMatches.add(new Match(matched));
				}
			}
		}
		
		/**
		 * Creates matches of length three or longer from the given row
		 * @param row
		 */
		private void matchColumnThree(int col) {
			Column matching = columns[col];
			int i = 0;
			while (i < 9) {
				Space start = matching.spaces[i];
				List<Coordinate> matched = new java.util.ArrayList<Coordinate>();
				matched.add(new Coordinate(i, col));
				Space current = matching.spaces[++i];
				if (start.match(current) && i < 9) {
					matched.add(new Coordinate(i, col));
					current = matching.spaces[++i];
					while (start.match(current) && i < 9) {
						matched.add(new Coordinate(i, col));
						if (i < 9) {
						 current = matching.spaces[++i];
						}
					}
					if (i == 9) {
						if (start.match(matching.spaces[i])) {
							matched.add(new Coordinate(i, col));
						}
					}
				}
				if (matched.size() > 2) {
					vMatches.add(new Match(matched));
				}
			}
		}
		
		/**
		 * Compares horizontal with vertical matches and combines any that matched the same spaces
		 * Then, compares the combined matches with each other and 
		 */
		private void merging() {
			int hi = 0, vi = 0, ti = 0;
			boolean[] hout = new boolean[hMatches.size()];
			boolean[] vout = new boolean[vMatches.size()];
			while (hi < hMatches.size()) {
				Match h = hMatches.get(hi);
				while (vi < vMatches.size()) {
					Match v = vMatches.get(vi);
					Match t = h.merge(v);//(merge(match) returns a match containing coordinates of both matches if intersecting, null otherwise
					if (t != null) {
						tMatches.add(t);
						hout[hi] = true;
						vout[vi] = true;
					}
					vi++;
				}
				hi++;
			}
			//now compare and combine any already combined matches that cover the same spaces
			boolean[] tout = new boolean[tMatches.size()];
			while (ti < tMatches.size()) {
				int t1 = tMatches.size() - 1;
				while (t1 > ti) {
					Match t = tMatches.get(ti).merge(tMatches.get(t1));
					if (t != null) {
						tMatches.set(ti, t);
						tout[t1] = true;
					}
					t1--;
				}
				ti++;
			}
			for (int i = hout.length-1; i > -1; i--) {
				if (hout[i]) hMatches.remove(i);
			}
			for (int i = vout.length-1; i > -1; i--) {
				if (vout[i]) vMatches.remove(i);
			}
			for (int i = tout.length-1; i > -1; i--) {
				if (tout[i]) tMatches.remove(i);
			}
		}
		
		/**
		 * Removes all matches created in this cycle from the grid. Will eventually update the score
		 */
		private void destruction() {
			int hi = 0, vi = 0, ti = 0;
			while (ti < tMatches.size()) {
				Match t = tMatches.get(ti);
				for (Coordinate c : t.getSpaces()) {
					grid[c.getRow()][c.getCol()].removeGhost();
				}
				ti++;
			}
			while (hi < hMatches.size()) {
				Match h = hMatches.get(hi);
				for (Coordinate c : h.getSpaces()) {
					grid[c.getRow()][c.getCol()].removeGhost();
				}
				hi++;
			}
			while (vi < vMatches.size()) {
				Match v = vMatches.get(vi);
				for (Coordinate c : v.getSpaces()) {
					grid[c.getRow()][c.getCol()].removeGhost();
				}
				vi++;
			}
		}
	}
}
