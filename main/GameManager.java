package main;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import gamestate.ClassicGame;
import gamestate.GameStateType;
import gamestate.MainMenu;
import gamestate.VersusGame;

import static main.Utilities.Constants.WindowConstants.*;

public class GameManager implements Runnable{

	// UI objects
	private GamePanel gamePanel;
    private Thread gameThread;

	// game states
	private VersusGame versusGame;
	private ClassicGame classicGame;
	private MainMenu mainMenu;

	// game objects
	private GameStateType currentGameState = GameStateType.CLASSIC_GAME;

    public GameManager() {
		// init classes and game loop
        initGameClasses();
		initGameLoop();
    }
	
    private void initGameClasses() {
		// init game states
		classicGame = new ClassicGame(this);
		mainMenu = new MainMenu(this);
		versusGame = new VersusGame(this);

		// init game window
		gamePanel = new GamePanel(this);
        new GameWindow(gamePanel); 
    }

    private void initGameLoop() {
		// start the whole game loop
        gameThread = new Thread(this);
        gameThread.start();
	}

	@Override
	public void run() { // will automatically be executed so no need to call

		// time in nanoseconds for precision
		double timePerFrame = 1000000000.0 / FPS;
		double timePerUpdate = 1000000000.0 / UPS; 

		long timeOfLastUpdate = System.nanoTime();

		// count refreshes per second
		int fps = 0;
		int ups = 0;
		long timeOfLastCheck = System.currentTimeMillis();

		// number of refreshes required since last check
		double numUpdateRefresh = 0;
		double numFrameRefresh = 0;

		// start game loop
		while (true) {
			long currentTime = System.nanoTime();

			numUpdateRefresh += (currentTime - timeOfLastUpdate) / timePerUpdate;
			numFrameRefresh += (currentTime - timeOfLastUpdate) / timePerFrame;
			timeOfLastUpdate = currentTime;

			if (numUpdateRefresh >= 1) { 
				// update the game
				update();
				ups++;
				numUpdateRefresh--;
			}

			if (numFrameRefresh >= 1) { 
				// refresh the game after the objects have been updated
				gamePanel.repaint();
				fps++;
				numFrameRefresh--;
			}

			// print refreshes per second and reset counters
			if (System.currentTimeMillis() - timeOfLastCheck >= 1000) {
				timeOfLastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + fps + " | UPS: " + ups);
				fps = 0;
				ups = 0;
			}
		}
	}

    public void draw(Graphics g) {
		switch(currentGameState) {
			case CLASSIC_GAME:
				classicGame.draw(g);
				break;
			case VERSUS_GAME:
				versusGame.draw(g);
				break;
			case MAIN_MENU:
				break;
			case PAUSE_MENU:
				break;
			case OPTIONS_MENU:
				break;
			case END_SCREEN:
				break;
		}
	}

    public void update() {
		switch(currentGameState) {
			case CLASSIC_GAME:
				classicGame.update();
				break;
			case VERSUS_GAME:
				versusGame.update();
				break;
			case MAIN_MENU:
				break;
			case PAUSE_MENU:
				break;
			case OPTIONS_MENU:
				break;
			case END_SCREEN:
				break;
		}
    }

	// keyboard inputs
	public void keyPressed(KeyEvent e) {
		switch(currentGameState) {
			case CLASSIC_GAME:
				classicGame.keyPressed(e);
				break;
			case VERSUS_GAME:
				versusGame.keyPressed(e);
			case END_SCREEN:
				break;
			case MAIN_MENU:
				break;
			case OPTIONS_MENU:
				break;
			case PAUSE_MENU:
				break;
        }
	} 
	// getters and setters

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public int getTimeLeft() {
		return versusGame.getTimeLeft();
	}

	public GameStateType getGameState() {
		return currentGameState;
	}
}