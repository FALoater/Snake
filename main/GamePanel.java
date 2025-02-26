package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import input.*;
import main.Utilities.Methods;

import static main.Utilities.Constants.SnakeConstants.DEATH_POINT_DEDUCTION;
import static main.Utilities.Constants.WindowConstants.*;

public class GamePanel extends JPanel {

	private int playerScore = 0, enemyScore = 0;
	
	private GameManager game;
	private JLabel hud;
	private String hudColor;

	public GamePanel(GameManager game) {
		this.game = game; // gamePanel needs access to the objects inside the game
		MouseInput mouseInput = new MouseInput(game);
		hudColor = Methods.GetCurrentTextColor(game);

		initHUD();
		setPanelSize(); // set the size of the gamePanel;
		setBackground(Methods.GetBgColor(game)); // set the background color of the gamePanel
		add(hud); // add the score and tiemr to the top of the screen
		addKeyListener(new KeyInput(game)); // add key listener to the game panel, and pass this class in to access other classes
		addMouseListener(mouseInput); // listens to mouse presses
		addMouseMotionListener(mouseInput); // listens to mouse movement
		setFocusable(true); // enables key inputs to be received
	}

	private void initHUD() {
		// create a JLabel for top of screen to display score and timer
		hud = new JLabel();
		hud.setForeground(Color.decode(hudColor));
		hud.setFont(DEFAULT_FONT);
		updateHUD();
	}

	private void setPanelSize() {
		// set screen size as pre determined
		Dimension size = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
		setPreferredSize(size);
	}

	public void update() {
		updateBg();
		updateHUD();
	}

	private void updateBg() {
		setBackground(Methods.GetBgColor(game));
	}

	public void updateHUD() {
		hudColor = Methods.GetCurrentTextColor(game);
		// update the JLabel after attributes have been modified
		switch(game.getGameState()) {
			case PAUSE_MENU:
				break;
			case CLASSIC_GAME:
				hud.setText("<html><nobr><font color='" + hudColor + "'>Score: " + playerScore);
				break;
			case VERSUS_GAME:
				hud.setText("<html><nobr><font color='" + hudColor + "'>Score: </font><font color='#00ff00'>" + playerScore + "</font><font color='" + hudColor + "'> - </font><font color='ff0000'>" + enemyScore + "</font>&nbsp;&nbsp;&nbsp;&nbsp;<font color='" + hudColor + "'>Time left: " + game.getTimeLeft() + "</font></nobr></html>");
				break;
			case END_SCREEN:
			case MAIN_MENU:
			case OPTIONS_MENU:
				hud.setText("");
				break;
		}
	}

	public void paintComponent(Graphics g) {
		// draw the game objects on the JPanel
		super.paintComponent(g);
		game.draw(g); 
	}

	// getters and setters
	
	public void updatePlayerScore(int score) {
		// add score and update highscore if relevant
		playerScore += score;

		updateHUD();
	}

	public void updateEnemyScore(int score) {
		enemyScore += score;
		updateHUD();
	}

	public void resetPlayerScore() {
		// subtract user score by 20, prevent from going below 0
		playerScore = Math.max(playerScore -= DEATH_POINT_DEDUCTION, 0);
		updateHUD();
	}

	public void resetEnemyScore() {
		enemyScore = Math.max(enemyScore - DEATH_POINT_DEDUCTION, 0);
		updateHUD();
	}
	
	public int getPlayerScore() {
		return playerScore;
	}

	public int getEnemyScore() {
		return enemyScore;
	}

	public void clearScore() {
		playerScore = 0;
		enemyScore = 0;
	}
}
