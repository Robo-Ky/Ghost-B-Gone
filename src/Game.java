import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Game {
	private Grid grid;
	private GhostFactory f;
	private int score, keyDelay;
	private Image gridBack;
	private Input inputHandler;
	/**  Keeps track of the current space the user has highlighted */
	private int cursorRow, cursorCol;
	
	public Game() throws SlickException {
		grid = new Grid();
		f = new GhostFactory(new java.util.Random());
		score = 0; keyDelay = 0;
		gridBack = new Image("res/grid.png");
		inputHandler = new Input(490);
		cursorRow = 4; cursorCol = 4;
	}
	
	/**
	 * Adds a given ghost to the grid at the specified location
	 * @param g
	 * @param row
	 * @param col
	 */
	public void add(Ghost g, int row, int col) {
		grid.add(g, row, col);
	}
	
	/**
	 * Adds a random ghost to the grid at the specified location
	 * @param row
	 * @param col
	 */
	public void addRandom(int row, int col) {
		grid.add(f.makeGhostRandom(), row, col);
	}
	
	/**
	 * Moves the cursor based on key input
	 */
	public void cursorMove() {
		if (inputHandler.isKeyDown(Input.KEY_UP)) {
			if (cursorRow > 0) cursorRow--;
		}
		else if (inputHandler.isKeyDown(Input.KEY_DOWN)) {
			if (cursorRow < 9) cursorRow++;
		}
		else if (inputHandler.isKeyDown(Input.KEY_LEFT)) {
			if (cursorCol > 0) cursorCol--;
		}
		else if (inputHandler.isKeyDown(Input.KEY_RIGHT)) {
			if (cursorCol < 9) cursorCol++;
		}
	}
	
	/**
	 * Fills the grid with random ghosts
	 */
	public void fillRandom() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				addRandom(i,j);
			}
		}
	}
	
	/**
	 * @return the backing array of the grid
	 */
	public Space[][] getGrid() {
		return grid.getGrid();
	}
	
	/**
	 * Draws the grid lines, cursor plus row and column, and ghosts to the game window
	 * @param g the graphics object used for highlighting the cursor space and the corresponding row and column
	 */
	public void render(org.newdawn.slick.Graphics g) {
		gridBack.draw(0, 0);
		g.setColor(new Color(255,255,85));
		//highlights the full row and column of the cursor
		for (int i = 0; i < 10; i++) {
			g.fillRect(DrawStuff.squares[cursorCol], DrawStuff.squares[i], 45, 45);
			g.fillRect(DrawStuff.squares[i], DrawStuff.squares[cursorRow], 45, 45);
		}
		//highlights the cursor space with a different color
		g.setColor(new Color(255,125,0));
		g.fillRect(DrawStuff.squares[cursorCol], DrawStuff.squares[cursorRow], 45, 45);
		grid.render();
	}
	
	/**
	 * Slides the current row or column based on key input
	 */
	public void slide() {
		if (inputHandler.isKeyDown(Input.KEY_W)) {
			grid.slideUp(cursorCol);
		}
		else if (inputHandler.isKeyDown(Input.KEY_S)) {
			grid.slideDown(cursorCol);
		}
		else if (inputHandler.isKeyDown(Input.KEY_A)) {
			grid.slideLeft(cursorRow);
		}
		else if (inputHandler.isKeyDown(Input.KEY_D)) {
			grid.slideRight(cursorRow);
		}
	}
	
	/**
	 * Updates the grid by matching ghosts
	 */
	public void update() {
		grid.matchingThree();
	}
}
