import java.util.List;

/**
 * A match holds a number of coordinates indicating adjacent spaces that have been matched together with ghosts of the same color
 * @author Carter
 *
 */
public class Match {
	private char type;
	private List<Coordinate> matched;
	private int size;
	
	public Match(List<Coordinate> matched) {
		this.matched = matched;
		size = matched.size();
	}
	
	/**
	 * Check if this match and another match contain identical coordinates
	 * @param check the match to check intersection with this one
	 * @return whether this match intersects with the other
	 */
	public boolean intersecting(Match check) {
		if (check != null && !check.equals(this)) {
				for (Coordinate c : matched) {
					for (Coordinate d : check.matched) {
						if (c.equals(d)) {return true;}
					}
				}
		}
		return false;
	}
	
	/**
	 * Merge this match with another given match
	 * @param merging the match to be combined with this one
	 * @return a combined match if it was made, null otherwise
	 */
	public Match merge(Match merging) {
		if (merging != null) {
			if (intersecting(merging)) {
				List<Coordinate> newMatched = new java.util.ArrayList<Coordinate>();
				newMatched.addAll(matched);
				for (Coordinate c : merging.matched) {
					if (!newMatched.contains(c)) {//add all coordinates from the other match not present in this match
						newMatched.add(c);
					}
				}
				Match merged = new Match(newMatched);
				return merged;
			}
		}
		return null;
	}
	
	/**
	 * @return the coordinates of this match
	 */
	public List<Coordinate> getSpaces() {
		return matched;
	}
	
	/**
	 * Print the coordinates of this match to the console
	 */
	public void print() {
		System.out.print(type + ": ");
		for (Coordinate c : matched) {
			c.printCoords();
		}
		System.out.println();
	}
}
