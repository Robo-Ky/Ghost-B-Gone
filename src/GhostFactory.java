/**
 * A factory class for creating ghosts, either randomly or with a given color
 * @author Carter
 *
 */
public class GhostFactory {
	private java.util.Random r;
	
	/**
	 * @param r a randomizer for making ghosts
	 */
	public GhostFactory(java.util.Random r) {
		this.r = r;
	}
	
	/**
	 * Make a ghost of a specific color
	 * @param color 
	 * @return new ghost with given color and right image path, or null if index out of bounds
	 */
	public Ghost makeGhost(int color) {
		if (color >= 0 && color < 5) {
			return new Ghost(color);
		}
		return null;
	}
	
	/**
	 * @return a new ghost of a random color
	 */
	public Ghost makeGhostRandom() {
		int color = r.nextInt(5);
		return new Ghost(color);
	}
}
