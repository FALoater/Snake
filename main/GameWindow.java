package main;

import javax.swing.JFrame;

public class GameWindow {
	
	private JFrame jframe;

	public GameWindow(GamePanel gamePanel) {

		jframe = new JFrame();

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close on exit
		jframe.setTitle("Snake");
		jframe.add(gamePanel); // add the game
		jframe.setResizable(false);
		jframe.pack(); // set the size of the window to the size of the gamePanel
		jframe.setLocationRelativeTo(null); // centre the window
		jframe.setVisible(true);
	}
}