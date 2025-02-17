package main;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import gamestate.ClassicGame;
import gamestate.EndGame;
import gamestate.GameStateType;
import gamestate.MainMenu;
import gamestate.OptionsMenu;
import gamestate.PauseMenu;
import gamestate.VersusGame;

import static main.Utilities.Constants.WindowConstants.*;
import static main.Utilities.Constants.AudioConstants.*;

public class GameManager implements Runnable{

	// UI objects
	private GamePanel gamePanel;
    private Thread gameThread;

	// game states
	private VersusGame versusGame;
	private ClassicGame classicGame;
	private MainMenu mainMenu;
	private OptionsMenu optionsMenu;
	private PauseMenu pauseMenu;
	private EndGame endGame;

	// game objects
	private GameStateType currentGameState = GameStateType.MAIN_MENU, previousGameState;
	private SoundPlayer music, soundEffect;

	// game variables
	private boolean soundOn = true; // sound is on by default
	private int mode = 1; // default sound level is medium
	private int lastPlayerScore, lastEnemyScore, highscore = 0;
	private ColorMode colorMode = ColorMode.DARK; // make dark mode default


    public GameManager() {
		// init classes and game loop
        initGameClasses();
		initGameLoop();

		gamePanel.requestFocus();
    }
	
    private void initGameClasses() {
		// init game states
		classicGame = new ClassicGame(this);
		mainMenu = new MainMenu(this);
		versusGame = new VersusGame(this);
		optionsMenu = new OptionsMenu(this);
		pauseMenu = new PauseMenu(this);

		// init game window
		gamePanel = new GamePanel(this);
        new GameWindow(gamePanel); 

		// init end game
		endGame = new EndGame(this);

		// init music player
		music = new SoundPlayer(0.8f);
		music.playMusic(MAIN_MENU_MUSIC);

		// init sound effect
		soundEffect = new SoundPlayer(0.77f);
    }

    private void initGameLoop() {
		// start the whole game loop
        gameThread = new Thread(this);
        gameThread.start();
	}

	private void resetGame() {
		switch(currentGameState) {
			case CLASSIC_GAME:
				classicGame.reset();
				break;
			case VERSUS_GAME:
				versusGame.reset();
				break;
			case END_SCREEN:
			case MAIN_MENU:
			case OPTIONS_MENU:
			case PAUSE_MENU:
				break;
		}
	}

	@Override
	public void run() { // will automatically be executed so no need to call

		// time in nanoseconds for precision
		double timePerFrame = 1000000000.0 / FPS;
		double timePerUpdate = 1000000000.0 / UPS; 

		long timeOfLastUpdate = System.nanoTime();

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
				numUpdateRefresh--;
			}

			if (numFrameRefresh >= 1) { 
				// refresh the game after the objects have been updated
				gamePanel.repaint();
				numFrameRefresh--;
			}
		}
	}

    public void draw(Graphics g) {
		g.setFont(DEFAULT_FONT);

		switch(currentGameState) {
			case CLASSIC_GAME:
				classicGame.draw(g);
				break;
			case VERSUS_GAME:
				versusGame.draw(g);
				break;
			case MAIN_MENU:
				mainMenu.draw(g);
				break;
			case PAUSE_MENU:
				pauseMenu.draw(g);
				break;
			case OPTIONS_MENU:
				optionsMenu.draw(g);
				break;
			case END_SCREEN:
				endGame.draw(g);
				break;
		}
	}

    public void update() {
		gamePanel.updateBg();
		gamePanel.updateHUD();

		switch(currentGameState) {
			case CLASSIC_GAME:
				classicGame.update();
				break;
			case VERSUS_GAME:
				versusGame.update();
				break;
			case MAIN_MENU:
				mainMenu.update();
				break;
			case PAUSE_MENU:
				pauseMenu.update();
				break;
			case OPTIONS_MENU:
				optionsMenu.update();
				break;
			case END_SCREEN:
				endGame.update();
				break;
		}
    }

	// inputs
	public void keyPressed(KeyEvent e) {
		switch(currentGameState) {
			case CLASSIC_GAME:
				classicGame.keyPressed(e);
				break;
			case VERSUS_GAME:
				versusGame.keyPressed(e);
			case END_SCREEN:
				endGame.keyPressed(e);
				break;
			case MAIN_MENU:
				mainMenu.keyPressed(e);
				break;
			case OPTIONS_MENU:
				optionsMenu.keyPressed(e);
				break;
			case PAUSE_MENU:
				pauseMenu.keyPressed(e);
				break;
        }
	} 

    public void mousePressed(MouseEvent e) {
		switch(currentGameState) {
			case CLASSIC_GAME:
				classicGame.mousePressed(e);
				break;
			case VERSUS_GAME:
				versusGame.mousePressed(e);
			case END_SCREEN:
				endGame.mousePressed(e);
				break;
			case MAIN_MENU:
				mainMenu.mousePressed(e);
				break;
			case OPTIONS_MENU:
				optionsMenu.mousePressed(e);
				break;
			case PAUSE_MENU:
				pauseMenu.mousePressed(e);
				break;
        }
    }

    public void mouseReleased(MouseEvent e) {
		switch(currentGameState) {
			case CLASSIC_GAME:
				classicGame.mouseReleased(e);
				break;
			case VERSUS_GAME:
				versusGame.mouseReleased(e);
			case END_SCREEN:
				endGame.mouseReleased(e);
				break;
			case MAIN_MENU:
				mainMenu.mouseReleased(e);
				break;
			case OPTIONS_MENU:
				optionsMenu.mouseReleased(e);
				break;
			case PAUSE_MENU:
				pauseMenu.mouseReleased(e);
				break;
        }
    }

    public void mouseDragged(MouseEvent e) {
		switch(currentGameState) {
			case CLASSIC_GAME:
				classicGame.mouseDragged(e);
				break;
			case VERSUS_GAME:
				versusGame.mouseDragged(e);
			case END_SCREEN:
				endGame.mouseDragged(e);
				break;
			case MAIN_MENU:
				mainMenu.mouseDragged(e);
				break;
			case OPTIONS_MENU:
				optionsMenu.mouseDragged(e);
				break;
			case PAUSE_MENU:
				pauseMenu.mouseDragged(e);
				break;
        }
    }

    public void mouseMoved(MouseEvent e) {
		switch(currentGameState) {
			case CLASSIC_GAME:
				classicGame.mouseMoved(e);
				break;
			case VERSUS_GAME:
				versusGame.mouseMoved(e);
			case END_SCREEN:
				endGame.mouseMoved(e);
				break;
			case MAIN_MENU:
				mainMenu.mouseMoved(e);
				break;
			case OPTIONS_MENU:
				optionsMenu.mouseMoved(e);
				break;
			case PAUSE_MENU:
				pauseMenu.mouseMoved(e);
				break;
        }
	}

	// getters and setters

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public int getTimeLeft() {
		// only applicable in versus mode
		return versusGame.getTimeLeft();
	}

	public GameStateType getGameState() {
		return currentGameState;
	}

	public void changeColorMode(ColorMode colorMode) {
		this.colorMode = colorMode;
	}

	public void changeColorMode() {
		if(colorMode == ColorMode.DARK) {
			colorMode = ColorMode.LIGHT;
		} else {
			colorMode = ColorMode.DARK;
		}
	}

	public void changeGameState(GameStateType newGameState) {
		// change the game state
		previousGameState = currentGameState;
		currentGameState = newGameState;

		// save previous scores
		lastPlayerScore = gamePanel.getPlayerScore();
		lastEnemyScore = gamePanel.getEnemyScore();
		if(lastPlayerScore > highscore) highscore = lastPlayerScore;

		resetGame();
		
		// reset music from options to main menu or prevent music resetting from end game or options to main menu
		if( (currentGameState == GameStateType.MAIN_MENU && previousGameState == GameStateType.PAUSE_MENU) || (currentGameState != GameStateType.MAIN_MENU && previousGameState != GameStateType.OPTIONS_MENU && previousGameState != GameStateType.END_SCREEN) ) {
			changeMusic();
		}
	}

	public void changeGameState() {
		// no argument means go back to previous game state;
		// swap two variable values
		GameStateType temp = currentGameState;
		currentGameState = previousGameState;
		previousGameState = temp;

		if(previousGameState != GameStateType.PAUSE_MENU) {
			// prevents game resetting if going back to game from pause
			resetGame();
			changeMusic();
		}
	}

	private void changeMusic() {
		switch(currentGameState) {
			case CLASSIC_GAME:
			case VERSUS_GAME:
				music.playMusic(BACKGROUND_MUSIC);
				break;
			case END_SCREEN:
				music.playMusic(MAIN_MENU_MUSIC);
				break;
			case MAIN_MENU:
				music.playMusic(MAIN_MENU_MUSIC);
				break;
			case OPTIONS_MENU:
			case PAUSE_MENU:
				break;
		}
	}

	public ColorMode getColorMode() {
		return colorMode;
	}

	public boolean isSoundOn() {
		return soundOn;
	}

	public void toggleSound() {
		soundOn = !soundOn;

		if(!soundOn) {
			music.setVolumeLevel(0f);
			soundEffect.setVolumeLevel(0f);
		} else {
			music.setVolumeLevel(0.8f);
			soundEffect.setVolumeLevel(0.77f);
		}
	}

	private void toggleVolume() {
		if(soundOn) {
			switch(mode) {
				case 0:
					music.setVolumeLevel(0.7f);
					break;
				case 1:
					music.setVolumeLevel(0.8f);
					break;
				case 2:
					music.setVolumeLevel(0.9f);
					break;
			}
		}
	}

	public int getMode() {
		return mode;
	}

	public void increaseMode() {
		mode++;
		mode %= 3;
		toggleVolume();
	}

	public int getPlayerScore() {
		return lastPlayerScore;
	}

	public int getPlayerHighScore() {
		return highscore;
	}

	public int getEnemyScore() {
		return lastEnemyScore;
	}

	public GameStateType getLastGameState() {
		return previousGameState;
	}

	public void playSoundEffect(String filePath) {
		soundEffect.playSoundEffect(filePath);
	}
}