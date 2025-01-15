package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

	private GameManager gameManager;
	

	public GamePanel(GameManager gameManager) {
		this.gameManager = gameManager; // gamePanel needs access to the objects inside the gameManager
		setPanelSize();
	}

	private void setPanelSize() {
		Dimension size = new Dimension(1200, 800);
		setPreferredSize(size);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		gameManager.render(g);
	}
}
