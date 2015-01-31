import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Driver starts the game, renders the window, takes keyboard input, and updates at each frame
 * @author Carter
 *
 */
public class Driver extends BasicGame {
	private Game game;

	public Driver(String title) {
		super(title);
	}
	
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		game.render(arg1);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		game = new Game();
		game.fillRandom();
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		game.update();
	}
	
	public static void main(String[] args) {
		try {
			AppGameContainer container = new AppGameContainer(new Driver("Ghost-B-Gone"));
			container.setDisplayMode(490, 490, false);
			container.setTargetFrameRate(24);
			container.setShowFPS(false);
			container.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void keyPressed(int key, char c) {
		game.cursorMove();
		game.slide();
	}
}
