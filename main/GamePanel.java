package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

import static main.Utilities.Constants.WindowConstants.*;

public class GamePanel extends JPanel {

	private int userScore = 0;

	private GameManager gameManager;
	private JLabel score;

	public GamePanel(GameManager gameManager) {
		this.gameManager = gameManager; // gamePanel needs access to the objects inside the gameManager

		initScoreBoard();

		setPanelSize(); // set the size of the gamePanel;
		setBackground(Color.gray); // set the background color of the gamePanel
		add(score); // add the score label to the gamePanel
		addKeyListener(new KeyInput(gameManager)); // add key listener to the game panel, and pass this class in to access other classes
		setFocusable(true); // enables key inputs to be received
	}

	private void initScoreBoard() {
		score = new JLabel();
		score.setText("Score: " + String.valueOf(userScore)); // display at top of screen
		score.setForeground(Color.RED);
		score.setFont(new Font("Arial", Font.PLAIN, 50)); // large so can be seen
	}

	private void setPanelSize() {
		Dimension size = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT); // defined in utilities constants
		setPreferredSize(size);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		gameManager.draw(g); // draw the game objects on the JPanel
	}

	// getters and setters
	
	public void updateScore(int score) {
		userScore += score;
		this.score.setText("Score: " + String.valueOf(userScore));
	}
}
