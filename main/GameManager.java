package main;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import gameobjects.EnemySnakeBody;
import gameobjects.EnemySnakeHead;
import gameobjects.Fruit;
import gameobjects.FruitType;
import gameobjects.SnakeBody;
import gameobjects.SnakeHead;

import static main.Utilities.Constants.WindowConstants.*;

public class GameManager implements Runnable{

	// UI objects
	private GamePanel gamePanel;
	private LinkedList<Fruit> fruits = new LinkedList<Fruit>();
    private LinkedList<EnemySnakeBody> enemyBody = new LinkedList<EnemySnakeBody>();
	private LinkedList<SnakeBody> playerBody = new LinkedList<SnakeBody>();

	private Random rand = new Random();
    private Thread gameThread;

	// game objects
    private EnemySnakeHead enemySnakeHead;
	private SnakeHead playerSnakeHead;

    public GameManager() {
		// init classes and game loop
        initGameClasses();
		initGameLoop();
    }
	
    private void initGameClasses() {
        // create player snake head
		playerSnakeHead = new SnakeHead(TILE_SIZE * 10, TILE_SIZE * 15, this);		
		playerBody.add(playerSnakeHead); 

        // create enemy snake head
        enemySnakeHead = new EnemySnakeHead(TILE_SIZE * 10, TILE_SIZE * 10, this);
        enemyBody.add(enemySnakeHead);

        // create game window
		gamePanel = new GamePanel(this);
        new GameWindow(gamePanel); 

        // generate 3 fruits at random locations
		for(int i=0;i<3;i++) {
			generateFruit(); 
		}
    }

    private void initGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
	}

	public void generateFruit() {
		// generate random position for fruit
		boolean validPosition = false;
		int xPos = 0, yPos = 0;

		while(!validPosition) {
			xPos = rand.nextInt(TILES_IN_WIDTH) * TILE_SIZE;
			yPos = rand.nextInt(TILES_IN_HEIGHT) * TILE_SIZE; 
	
			// check that fruit does not spawn on already occupied tile
			for(SnakeBody body : playerBody) {
				if(body.getX() != xPos && body.getY() != yPos) {
					validPosition = true;
				}
			}	
		}

		// generate random fruit type
		int fruitType = rand.nextInt(6); 

		if(fruitType <= 4) { // make apples more common
			fruits.add(new Fruit(xPos, yPos, FruitType.APPLE, this));
		} else {
			fruits.add(new Fruit(xPos, yPos, FruitType.ORANGE, this));
		}
	}
	
	public void fruitEaten(int score, int collisionType) {
		// update score and increase the length of the snake
		// collision type has 1 for player and 2 for enemy
		SnakeBody lastBody;
		if(collisionType == 1) {
			// player snake
			for(int i=0;i<score;i++) {
				lastBody = playerBody.get(playerBody.size() - 1);
				playerBody.add(new SnakeBody(lastBody.getX(), lastBody.getY(), playerBody.size() - 1, lastBody, this));	
			}
			gamePanel.updateScore(score);
		} else {
			// enemy snake
			for(int i=0;i<score;i++) {
				lastBody = enemyBody.get(enemyBody.size() - 1);
				enemyBody.add(new EnemySnakeBody(lastBody.getX(), lastBody.getY(), enemyBody.size() - 1, lastBody, this));	
			}
		}
	}

	@Override
	public void run() { // will automatically be executed so no need to call
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
		// playerBody should be above fruits so draw fruits first
		for(int i=0;i<fruits.size();i++) {
			fruits.get(i).draw(g);
		}

		for(int i=0;i<playerBody.size();i++) {
			playerBody.get(i).draw(g);
		}

		for(int i=0;i<enemyBody.size();i++) {
			enemyBody.get(i).draw(g);
		}
		
        enemySnakeHead.draw(g);
		playerSnakeHead.draw(g);
    }

    public void update() {
		// move snake first before updating any fruit collisions
		enemySnakeHead.update();
		playerSnakeHead.update();

		if(playerSnakeHead.isCollided()) return;

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

		for(int i=1;i<playerBody.size();i++) {
			playerBody.get(i).update();
		}

		for(int i=1;i<enemyBody.size();i++) {
			enemyBody.get(i).update();
		}
    }

	// getters and setters

	public SnakeHead getPlayerSnakeHead() {
		return playerSnakeHead;
	}

	public EnemySnakeHead getEnemySnakeHead() {
		return enemySnakeHead;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public LinkedList<SnakeBody> getPlayerBody() {
		return playerBody;
	}

	public LinkedList<EnemySnakeBody> getEnemyBody() {
		return enemyBody;
	}

	public LinkedList<Fruit> getFruits() {
		return fruits;
	}
}