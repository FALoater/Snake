package main;

import java.awt.Graphics;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import gameobjects.Fruit;
import gameobjects.FruitType;
import gameobjects.snake.EnemySnakeBody;
import gameobjects.snake.EnemySnakeHead;
import gameobjects.snake.SnakeBody;
import gameobjects.snake.SnakeHead;

import static main.Utilities.Constants.SnakeConstants.ENEMY_START_X;
import static main.Utilities.Constants.SnakeConstants.ENEMY_START_Y;
import static main.Utilities.Constants.SnakeConstants.PLAYER_START_X;
import static main.Utilities.Constants.SnakeConstants.PLAYER_START_Y;
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
	private GridObject[][] grid;
	private SnakeHead playerSnakeHead;

    public GameManager() {
		// init classes and game loop
        initGameClasses();
		initGameLoop();
    }
	
    private void initGameClasses() {
		// implement grid, y then x
		grid = new GridObject[TILES_IN_HEIGHT][TILES_IN_WIDTH];
		
		// populate 2D array with empty squares
		for(GridObject[] row : grid) {
			Arrays.fill(row, GridObject.EMPTY);
		}

        // create player snake head
		playerSnakeHead = new SnakeHead(PLAYER_START_X, PLAYER_START_Y, this);		
		playerBody.add(playerSnakeHead); 

        // create enemy snake head
        enemySnakeHead = new EnemySnakeHead(ENEMY_START_X, ENEMY_START_Y, this);
        enemyBody.add(enemySnakeHead);

		// add snake heads to grid
		grid[PLAYER_START_Y / TILE_SIZE][PLAYER_START_X / TILE_SIZE] = GridObject.PLAYER_HEAD;
		grid[ENEMY_START_Y / TILE_SIZE][ENEMY_START_X / TILE_SIZE] = GridObject.ENEMY_HEAD;

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

		// check position is not covered by a snake or fruit
		while(!validPosition) {
			xPos = rand.nextInt(TILES_IN_WIDTH);
			yPos = rand.nextInt(TILES_IN_HEIGHT);

			if(grid[yPos][xPos] == GridObject.EMPTY) {
				validPosition = true;
				grid[yPos][xPos] = GridObject.FRUIT;
			}
		}

		// generate random fruit type
		int fruitType = rand.nextInt(6); 

		if(fruitType <= 4) { // make apples more common
			fruits.add(new Fruit(xPos * TILE_SIZE, yPos * TILE_SIZE, FruitType.APPLE, this));
		} else {
			fruits.add(new Fruit(xPos * TILE_SIZE, yPos * TILE_SIZE, FruitType.ORANGE, this));
		}
	}
	
	public void fruitEaten(int score, int collisionType) {
		// update score and increase the length of the snake
		// collision type has 1 for player and 2 for enemy
		SnakeBody lastBody;
		if(collisionType == 1) {
			// player snake
			for(int i=0;i<score;i++) {
				lastBody = playerBody.get(playerBody.size() - 1); // 'pointer' for new body
				playerBody.add(new SnakeBody(lastBody.getX(), lastBody.getY(), playerBody.size() - 1, lastBody, this));	// added in linkedlist but not yet spawned as grid could be occupied
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

		// does not matter which snake is drawn first as they never overlap
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
		// stop updating game entirely if player is collided
		if(playerSnakeHead.isCollided()) return;

		enemySnakeHead.update();
		playerSnakeHead.update();

		// update all the fruits
		for(int i=0;i<fruits.size();i++) {
			Fruit currentFruit = fruits.get(i);

			// remove fruit if it has been eaten
			if(currentFruit.isDeleteFlag()) {
				grid[currentFruit.getY() / TILE_SIZE][currentFruit.getX() / TILE_SIZE] = GridObject.EMPTY;
				fruits.remove(i);
				i--; // decrement i to avoid out of bounds error
				generateFruit();
			} else {
				currentFruit.update();
			}
		}

		// update snake body last
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

	public void setGrid(int xPos, int yPos, GridObject object) {
		grid[yPos / TILE_SIZE][xPos / TILE_SIZE] = object;
	}

	public GridObject getObjectAtGridPos(int xPos, int yPos) {
		return grid[yPos / TILE_SIZE][xPos / TILE_SIZE];
	}
}