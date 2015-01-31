/**
 * A single space in the grid which holds a ghost
 * @author Carter
 *
 */
public class Space {
	private Ghost ghost = null;
	private int color = -1;
	private boolean poltergeist, specter;
	
	/**
	 * Add a given ghost to this space
	 * @param g
	 */
	public void addGhost(Ghost g) {
		ghost = g;
		color = ghost.getColor();
		poltergeist = g.poltergeist();
		specter = g.specter();
	}
	
	public void drawGhost(int row, int col) {
		if (hasGhost()) {
			ghost.render(row, col);
		}
	}
	
	/**
	 * @return the ghost in this space
	 */
	public Ghost get() {
		return ghost;
	}
	
	/**
	 * @return the color of the ghost in this space
	 */
	public int getColor() {
		return color;
	}
	
	/**
	 * @return whether this space has a ghost in it
	 */
	public boolean hasGhost() {
		if (ghost == null) return false;
		return true;
	}
	
	/**
	 * If a specter stretches into this space, remove it
	 */
	public void killSpecter() {
		specter = false;
		color = -1;
	}
	
	/**
	 * Match this space's ghost against another space
	 * @param s the space to match against
	 * @return whether this space's ghost matches the other's
	 */
	public boolean match(Space s) {
		if (hasGhost() && s.hasGhost()) {
			if (ghost.match(s.ghost)) {
				return true;
			}
			polterCheck(s);
		}
		return false;
	}
	
	/**
	 * Check for poltergeists in this space and another
	 * @param s the adjacent space to check
	 */
	private void polterCheck(Space s) {
		if (poltergeist) {
			ghost.reveal();
			color = ghost.getColor();
			poltergeist = ghost.poltergeist();
		}
		if (s.poltergeist) {
			s.ghost.reveal();
			s.color = s.ghost.getColor();
			s.poltergeist = s.ghost.poltergeist();
		}
	}
	
	/**
	 * Print the color of the ghost in this space
	 */
	public void print() {
		System.out.print(color + " ");
	}
	
	public void removeGhost() {
		ghost = null;
		color = -1;
		poltergeist = false;
		specter = false;
	}
	
	/**
	 * Stretch a given specter into this space
	 * @param g the specter that stretches here
	 */
	public void setSpecter(Ghost g) {
		specter = true;
		color = g.getColor();
	}
}
