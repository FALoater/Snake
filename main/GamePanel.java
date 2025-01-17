package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import static main.Utilities.Constants.WindowConstants.*;

public class GamePanel extends JPanel {

	private GameManager gameManager;

	public GamePanel(GameManager gameManager) {
		this.gameManager = gameManager; // gamePanel needs access to the objects inside the gameManager
		setPanelSize();
	}

	private void setPanelSize() {
		Dimension size = new Dimension(WINDOW_HEIGHT, WINDOW_WIDTH);
		setPreferredSize(size);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		gameManager.draw(g);
	}
}
