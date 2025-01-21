package main;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

import gameobjects.Snake;

import static main.Utilities.Constants.WindowConstants.*;
import static main.Utilities.Methods.*;

public class GameManager implements Runnable{

	// UI objects
	private GamePanel gamePanel;
    private Thread gameThread;

	// game objects
	private Snake playerSnake;
	
	private BufferedImage bg;

    public GameManager() {
		bg = LoadImage("bg/snak");

        initGameClasses();
		initGameLoop();
    }
	
    private void initGameClasses() {
		playerSnake = new Snake(TILE_SIZE * 10, TILE_SIZE * 15, this);		

		gamePanel = new GamePanel(this);
        new GameWindow(gamePanel);
    }

    private void initGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS;
		double timePerUpdate = 1000000000.0 / UPS; // time in nanoseconds for precision

		long timeOfLastUpdate = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long timeOfLastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - timeOfLastUpdate) / timePerUpdate;
			deltaF += (currentTime - timeOfLastUpdate) / timePerFrame;
			timeOfLastUpdate = currentTime;

			if (deltaU >= 1) { // update the game if the time has passed
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) { // refresh the game after the objects have been updated
				gamePanel.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - timeOfLastCheck >= 1000) {
				timeOfLastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}

    public void draw(Graphics g) {
		g.drawImage(bg, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);

        playerSnake.draw(g);
    }

    public void update() {
        playerSnake.update();
    }

	public Snake getPlayerSnake() {
		return playerSnake;
	}
}
