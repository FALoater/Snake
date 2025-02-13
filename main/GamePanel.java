package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

import static main.Utilities.Constants.SnakeConstants.DEATH_POINT_DEDUCTION;
import static main.Utilities.Constants.WindowConstants.*;

public class GamePanel extends JPanel {

	private int userScore = 0, enemyScore = 0;

	private GameManager game;
	private JLabel hud;

	public GamePanel(GameManager game) {
		this.game = game; // gamePanel needs access to the objects inside the game
		initHUD();
		setPanelSize(); // set the size of the gamePanel;
		setBackground(Color.gray); // set the background color of the gamePanel
		add(hud); // add the score and tiemr to the top of the screen
		addKeyListener(new KeyInput(game)); // add key listener to the game panel, and pass this class in to access other classes
		setFocusable(true); // enables key inputs to be received
	}

	private void initHUD() {
		// create a JLabel for top of screen to display score and timer
		hud = new JLabel();
		hud.setForeground(Color.white);
		hud.setFont(new Font("Arial", Font.PLAIN,40));
		updateHUD();
	}

	private void setPanelSize() {
		// set screen size as pre determined
		Dimension size = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
		setPreferredSize(size);
	}

	public void updateHUD() {
		// update the JLabel after attributes have been modified
		hud.setText("<html><nobr>Score: <font color='#00ff00'>" + userScore + "</font> - <font color='ff0000'>" + enemyScore + "</font>&nbsp;&nbsp;&nbsp;&nbsp;Time left: " + game.getTimeLeft() + "</p></nobr></html>");
	}

	public void paintComponent(Graphics g) {
		// draw the game objects on the JPanel
		super.paintComponent(g);
		game.draw(g); 
	}

	// getters and setters
	
	public void updatePlayerScore(int score) {
		userScore += score;
		updateHUD();
	}

	public void updateEnemyScore(int score) {
		enemyScore += score;
		updateHUD();
	}

	public void resetPlayerScore() {
		// subtract user score by 20, prevent from going below 0
		userScore = Math.max(userScore -= DEATH_POINT_DEDUCTION, 0);
		updateHUD();
	}

	public void resetEnemyScore() {
		enemyScore = Math.max(enemyScore - DEATH_POINT_DEDUCTION, 0);
		updateHUD();
	}
}
