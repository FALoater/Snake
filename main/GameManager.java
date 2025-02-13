package main;

import java.awt.Graphics;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import gameobjects.Fruit;
import gameobjects.FruitType;
import gameobjects.snake.EnemyBody;
import gameobjects.snake.EnemyHead;
import gameobjects.snake.SnakeBody;
import gameobjects.snake.SnakeHead;

import static main.Utilities.Constants.SnakeConstants.*;
import static main.Utilities.Constants.WindowConstants.*;
import static main.Utilities.Methods;

public class GameManager implements Runnable{

	// UI objects
	private GamePanel gamePanel;
	private LinkedList<Fruit> fruits = new LinkedList<Fruit>();
    private LinkedList<EnemyBody> enemyBody = new LinkedList<EnemyBody>();
	private LinkedList<SnakeBody> playerBody = new LinkedList<SnakeBody>();

	private Random rand = new Random();
    private Thread gameThread;

	// game objects
    private EnemyHead enemyHead;
	private SnakeHead playerHead;
	private GridObject[][] grid;

	// game variables
	private int enemyRespawnTick, playerRespawnTick;
	private int timeLeftTick = 60 * UPS;

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
		playerHead = new SnakeHead(PLAYER_START_X, PLAYER_START_Y, this);		
		playerBody.add(playerHead); 

        // create enemy snake head
        enemyHead = new EnemyHead(ENEMY_START_X, ENEMY_START_Y, this);
        enemyBody.add(enemyHead);

		// add snake heads to grid
		grid[PLAYER_START_Y / TILE_SIZE][PLAYER_START_X / TILE_SIZE] = GridObject.PLAYER_HEAD;
		grid[ENEMY_START_Y / TILE_SIZE][ENEMY_START_X / TILE_SIZE] = GridObject.ENEMY_HEAD;

        // create game window
		gamePanel = new GamePanel(this);
        new GameWindow(gamePanel); 

        // generate 5 fruits at random locations
		for(int i=0;i<5;i++) {
			generateFruit(); 
		}
    }

    private void initGameLoop() {
		// start the whole game loop
        gameThread = new Thread(this);
        gameThread.start();
	}

	public void generateFruit() {
		// generate random position for fruit
		int[] newPosition = Methods.GenerateRandomValidPosition(this);
		int xPos = newPosition[0];
		int yPos = newPosition[1];

		// update position in grid
		grid[yPos][xPos] = GridObject.FRUIT;

		// generate random fruit type
		int fruitType = rand.nextInt(6); 

		// make apples spawn more commonly
		if(fruitType <= 3) {
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
				playerBody.add(new SnakeBody(lastBody.getX(), lastBody.getY(), lastBody, this));	// added in linkedlist but not yet spawned as grid could be occupied
			}
			gamePanel.updatePlayerScore(score);
		} else {
			// enemy snake
			for(int i=0;i<score;i++) {
				lastBody = enemyBody.get(enemyBody.size() - 1);
				enemyBody.add(new EnemyBody(lastBody.getX(), lastBody.getY(), lastBody, this));	
			}
			gamePanel.updateEnemyScore(score);
		}
	}

	@Override
	public void run() { // will automatically be executed so no need to call

		// time in nanoseconds for precision
		double timePerFrame = 1000000000.0 / FPS;
		double timePerUpdate = 1000000000.0 / UPS; 

		long timeOfLastUpdate = System.nanoTime();

		// count refreshes per second
		int fps = 0;
		int ups = 0;
		long timeOfLastCheck = System.currentTimeMillis();

		// number of refreshes required since last check
		double numUpdateRefresh = 0;
		double numFrameRefresh = 0;

		// start game loop
		while (true) {
			long currentTime = System.nanoTime();

			numUpdateRefresh += (currentTime - timeOfLastUpdate) / timePerUpdate;
			numFrameRefresh += (currentTime - timeOfLastUpdate) / timePerFrame;
			timeOfLastUpdate = currentTime;

			if (numUpdateRefresh >= 1) { 
				// update the game
				update();
				ups++;
				numUpdateRefresh--;
			}

			if (numFrameRefresh >= 1) { 
				// refresh the game after the objects have been updated
				gamePanel.repaint();
				fps++;
				numFrameRefresh--;
			}

			// print refreshes per second and reset counters
			if (System.currentTimeMillis() - timeOfLastCheck >= 1000) {
				timeOfLastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + fps + " | UPS: " + ups);
				fps = 0;
				ups = 0;
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
		
		// draw the snake heads
        enemyHead.draw(g);
		playerHead.draw(g);
	}

    public void update() {
		// update game timer
		timeLeftTick--;

		// stop updating if timer reaches 0
		if(timeLeftTick <= 0) return;

		// else countdown timer
		gamePanel.updateHUD();

		// only update respawn timer if the enemy is dead
		if(enemyHead.isCollided()) {
			enemyRespawnTick++;

			if(enemyRespawnTick > RESPAWN_TIME) {
				// respawn the snake
				enemyRespawnTick = 0;
				enemyHead.setCollided(false);
				enemyHead.resetSnake();
			}
		}

		// do same for player
		if(playerHead.isCollided()) {
			playerRespawnTick++;

			if(playerRespawnTick > RESPAWN_TIME) {
				playerRespawnTick = 0;
				playerHead.setCollided(false);
				playerHead.resetSnake();
			}
		}

		// move snake first before updating any fruit collisions
		// stop updating game entirely if player is collided, only in classic mode
		//if(playerHead.isCollided()) return;
		playerHead.update();
		enemyHead.update();


		// update all the fruits
		for(int i=0;i<fruits.size();i++) {
			Fruit currentFruit = fruits.get(i);

			// remove fruit if it has been eaten
			if(currentFruit.isDeleteFlag()) {
				grid[currentFruit.getY() / TILE_SIZE][currentFruit.getX() / TILE_SIZE] = GridObject.EMPTY;
				fruits.remove(i);
				i--; // decrement i to avoid out of bounds error
				generateFruit(); // generate new fruit at random position
			} else {
				// listen for collisions if fruit has not yet been interacted with
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

	public SnakeHead getPlayerHead() {
		return playerHead;
	}

	public EnemyHead getEnemyHead() {
		return enemyHead;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public LinkedList<SnakeBody> getPlayerBody() {
		return playerBody;
	}

	public LinkedList<EnemyBody> getEnemyBody() {
		return enemyBody;
	}

	public void resetPlayerScore() {
		gamePanel.resetPlayerScore();
	}

	public void resetEnemyScore() {
		gamePanel.resetEnemyScore();
	}

	public void resetEnemyBody() {
		// remove all the snake body length
		while(enemyBody.size() > 1) {
			EnemyBody currentBody = enemyBody.removeLast();
			grid[currentBody.getY() / TILE_SIZE][currentBody.getX() / TILE_SIZE] = GridObject.EMPTY;
		}
	}

	public void resetPlayerBody() {
		// remove all the snake body length
		while(playerBody.size() > 1) {
			SnakeBody currentBody = playerBody.removeLast();
			grid[currentBody.getY() / TILE_SIZE][currentBody.getX() / TILE_SIZE] = GridObject.EMPTY;
		}
	}

	public LinkedList<Fruit> getFruits() {
		return fruits;
	}

	public GridObject[][] getGrid() {
		return grid;
	}

	public void setGrid(int xPos, int yPos, GridObject object) {
		// standardise for index positions
		grid[yPos / TILE_SIZE][xPos / TILE_SIZE] = object;
	}

	public GridObject getObjectAtGridPos(int xPos, int yPos) {
		return grid[yPos / TILE_SIZE][xPos / TILE_SIZE];
	}

	public int getTimeLeft() {
		return timeLeftTick / UPS;
	}
}