package main;

import java.awt.Graphics;

public class GameManager implements Runnable{

    // private GameWindow gameWindow;
	private GamePanel gamePanel;
    private Thread gameThread;

    private final int FPS = 60;
	private final int UPS = 200;

    public GameManager() {
		
		gamePanel = new GamePanel(this);
        new GameWindow(gamePanel);

		initGameLoop();
    }

    private void initGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS;
		double timePerUpdate = 1000000000.0 / UPS;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;

			}
		}

	}

    public void draw(Graphics g) {
    // draw the graphics for every game state

    }

    public void update() {
    // update the game objects every frame
    }

}
