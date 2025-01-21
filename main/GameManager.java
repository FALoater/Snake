package main;

import java.awt.Graphics;
import java.util.LinkedList;

import gameobjects.Fruit;
import gameobjects.FruitType;
import gameobjects.Snake;

import static main.Utilities.Constants.WindowConstants.*;

public class GameManager implements Runnable{

	// UI objects
	private GamePanel gamePanel;
	private LinkedList<Fruit> fruits = new LinkedList<>();
    private Thread gameThread;

	// game objects
	private Snake playerSnake;

    public GameManager() {

        initGameClasses();
		initGameLoop();
    }
	
    private void initGameClasses() {
		playerSnake = new Snake(TILE_SIZE * 10, TILE_SIZE * 15, this);		
		fruits.add(new Fruit(TILE_SIZE * 5, TILE_SIZE * 7, FruitType.APPLE, this));
		fruits.add(new Fruit(TILE_SIZE * 7, TILE_SIZE * 10, FruitType.ORANGE, this));

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
		for(int i=0;i<fruits.size();i++) {
			fruits.get(i).draw(g);
		}
		
		playerSnake.draw(g);
    }

    public void update() {
		playerSnake.update();
		
		for(int i=0;i<fruits.size();i++) {
			Fruit currentFruit = fruits.get(i);

			if(currentFruit.isDeleteFlag()) { // if the fruit has been eaten, remove it from the linked list
				fruits.remove(i);
				i--; // decrement i to avoid out of bounds error
			} else {
				currentFruit.update();
			}
		}
    }

	// getters and setters

	public Snake getPlayerSnake() {
		return playerSnake;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}
}
