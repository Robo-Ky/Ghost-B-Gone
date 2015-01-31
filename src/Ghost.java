import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * A single ghost piece on the grid
 * @author Carter
 *
 */
public class Ghost {
	private int trueColor, dispColor;
	private boolean poltergeist, specter;
	private int points;
	private SpriteSheet sprite;
	
	/**
	 * Creates a new normal ghost with the given color
	 * @param trueColor
	 */
	public Ghost(int trueColor) {
		try {
			this.trueColor = trueColor; dispColor = trueColor;
			poltergeist = false; specter = false; points = 100;
			sprite = new SpriteSheet(DrawStuff.s[trueColor], 45, 45);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a new specter of the given color
	 * @param trueColor
	 * @param specter
	 */
	public Ghost(int trueColor, boolean specter) {
		this.trueColor = trueColor; dispColor = trueColor;
		poltergeist = false;
		if (specter) {this.specter = true; points = 400;}
		else {this.specter = false; points = 100;}
	}
	
	/**
	 * Creates a new poltergeist (ghost disguised as a color different than its actual color)
	 * @param trueColor
	 * @param dispColor
	 */
	public Ghost(int trueColor, int dispColor) {
		this.trueColor = trueColor; this.dispColor = dispColor;
		if (trueColor != dispColor) {poltergeist = true; points = 200;}
		else {poltergeist = false; points = 100;}
	}
	
	/**
	 * Matches the displayed color of this ghost with another
	 * @param g the ghost to be matched against
	 * @return matched or not
	 */
	public boolean dispMatch(Ghost g) {
		if (dispColor != -1 && g.dispColor != -1) {
			return dispColor == g.dispColor;
		}
		return false;
	}
	
	/**
	 * @return the displayed color of this ghost
	 */
	public int getColor() {
		return dispColor;
	}
	
	/**
	 * @return the amount of points awarded when matched and destroyed
	 */
	public int getPoints() {
		return points;
	}
	
	/**
	 * @return the true color of this ghost
	 */
	public int getTrueColor() {
		return trueColor;
	}
	
	/**
	 * Matches the true color of this ghost with another
	 * @param g the ghost to be matched against
	 * @return matched or not
	 */
	public boolean match(Ghost g) {
		if (trueColor != -1 && g.trueColor != -1) {
			return trueColor == g.trueColor;
		}
		return false;
	}
	
	/**
	 * @return whether this ghost is a poltergeist
	 */
	public boolean poltergeist() {
		return poltergeist;
	}
	
	/**
	 * Renders this ghost's sprite to the screen at the given location in the grid
	 * @param row
	 * @param col
	 */
	public void render(int row, int col) {
		sprite.draw(DrawStuff.squares[col], DrawStuff.squares[row]);
	}
	
	/**
	 * Reveals this ghost to be a poltergeist
	 */
	public void reveal() {
		System.out.println("Poltergeist!");
		dispColor = trueColor;
		poltergeist = false;
		points = 100;
	}
	
	/**
	 * @return whether this ghost is a specter
	 */
	public boolean specter() {
		return specter;
	}
}
