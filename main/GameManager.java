package main;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import gameobjects.Fruit;
import gameobjects.FruitType;
import gameobjects.SnakeBody;
import gameobjects.SnakeHead;

import static main.Utilities.Constants.WindowConstants.*;

public class GameManager implements Runnable{

	// UI objects
	private GamePanel gamePanel;
	private LinkedList<Fruit> fruits = new LinkedList<Fruit>();
	private LinkedList<SnakeBody> body = new LinkedList<SnakeBody>();
	private Random rand = new Random();
    private Thread gameThread;

	// game objects
	private SnakeHead playerSnake;

    public GameManager() {

        initGameClasses();
		initGameLoop();
    }
	
    private void initGameClasses() {
		playerSnake = new SnakeHead(TILE_SIZE * 10, TILE_SIZE * 15, this);		
		body.add(playerSnake);

		gamePanel = new GamePanel(this);
        new GameWindow(gamePanel);

		for(int i=0;i<3;i++) {
			generateFruit(); // generate 3 fruits at random locations
		}
    }

    private void initGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
	}

	public void generateFruit() {
		int xPos = rand.nextInt(TILES_IN_WIDTH) * TILE_SIZE;
		int yPos = rand.nextInt(TILES_IN_HEIGHT) * TILE_SIZE; // generate random position for fruit

		int fruitType = rand.nextInt(6); // generate random fruit type

		if(fruitType < 3) { // make apples more common
			fruits.add(new Fruit(xPos, yPos, FruitType.APPLE, this));
		} else {
			fruits.add(new Fruit(xPos, yPos, FruitType.ORANGE, this));
		}
	}
	
	public void fruitEaten(int score) {
		// update score and increase the length of the snake
		SnakeBody lastBody = body.get(body.size() - 1);
		body.add(new SnakeBody(lastBody.getX(), lastBody.getY(), lastBody));
		gamePanel.updateScore(score);
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

		for(int i=0;i<body.size();i++) {
			body.get(i).draw(g);
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
				generateFruit();
			} else {
				currentFruit.update();
			}
		}

		for(int i=1;i<body.size();i++) {
			body.get(i).update();
		}
    }

	// getters and setters

	public SnakeHead getPlayerSnake() {
		return playerSnake;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}
}