import java.util.Arrays;
import java.util.Random;

import org.newdawn.slick.*;

public class TestClass implements KeyListener{
	private static Game game;
	
	public void inputEnded() {
		// TODO Auto-generated method stub
		
	}

	public void inputStarted() {
		// TODO Auto-generated method stub
		
	}

	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setInput(Input input) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(int key, char c) {
		game.cursorMove();
		game.slide();
	}
	
	public void keyReleased(int key, char c) {
		
	}
}
