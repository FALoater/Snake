package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import static main.Utilities.Constants.WindowConstants.*;

public class GamePanel extends JPanel {

	private GameManager gameManager;

	public GamePanel(GameManager gameManager) {
		this.gameManager = gameManager; // gamePanel needs access to the objects inside the gameManager
		setPanelSize(); // set the size of the gamePanel;
		addKeyListener(new KeyInput(gameManager)); // add key listener to the game panel, and pass this class in to access other classes
		setFocusable(true); // enables key inputs to be received
	}

	private void setPanelSize() {
		Dimension size = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
		setPreferredSize(size);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		gameManager.draw(g); // draw the game objects on the JPanel
	}
}
