package gamestate;

import java.awt.Graphics;
import java.util.Arrays;
import java.util.LinkedList;

import main.GameManager;
import main.GridObject;
import gameobject.snake.*;
import gameobject.Direction;
import gameobject.Fruit;

import static main.Utilities.Constants.SnakeConstants.*;
import static main.Utilities.Constants.WindowConstants.*;
import static main.Utilities.Constants.AudioConstants.HIT;
import static main.Utilities.Methods;

public class VersusGame extends PlayingGameState {

    // game objects
    private LinkedList<EnemyBody> enemyBody = new LinkedList<EnemyBody>(); 

    // game variables
	private int enemyRespawnTick, playerRespawnTick;
	private int timeLeftTick;

    public VersusGame(GameManager game) {
        super(game);
		initClasses();
    }

    private void initClasses() {
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

        // generate 5 fruits at random locations
		for(int i=0;i<5;i++) {
			generateFruit(); 
		}
    }

	@Override
    public void fruitEaten(int score, int collisionType) {
		SnakeBody lastBody;
        if(collisionType == 1) {
			// player snake
			for(int i=0;i<score;i++) {
				lastBody = playerBody.get(playerBody.size() - 1); // 'pointer' for new body
				playerBody.add(new SnakeBody(lastBody.getX(), lastBody.getY(), lastBody, this));	// added in linkedlist but not yet spawned as grid could be occupied
			}
			game.getGamePanel().updatePlayerScore(score);
		} else {
			// enemy snake
			for(int i=0;i<score;i++) {
				lastBody = enemyBody.get(enemyBody.size() - 1);
				enemyBody.add(new EnemyBody(lastBody.getX(), lastBody.getY(), lastBody, this));	
			}
			game.getGamePanel().updateEnemyScore(score);
		}
	}

	public void reset() {
		// reset the game on restart

		// reset snakes and body
		playerHead.setPosition(PLAYER_START_X, PLAYER_START_Y);
		playerHead.setCollided(false);
		playerHead.setDirection(Direction.UP);
		resetPlayerBody();

		enemyHead.setPosition(ENEMY_START_X, ENEMY_START_Y);
		enemyHead.setCollided(false);
		enemyHead.setDirection(Direction.UP);
		resetEnemyBody();

		// reset fruits
		fruits.clear();
		
		for(int i=0;i<5;i++) {
			generateFruit(); 
		}

		// reset timer and score
		timeLeftTick = 60 * UPS;
		game.getGamePanel().clearScore();
	}	

    @Override
    public void update() {
		// update game timer
		timeLeftTick--;

		// stop updating if timer reaches 0
		if(timeLeftTick <= 0) game.changeGameState(GameStateType.END_SCREEN);

		// else countdown timer
		game.getGamePanel().updateHUD();

		// only update respawn timer if the enemy is dead
		if(enemyHead.isCollided()) {
			enemyRespawnTick++;

			if(enemyRespawnTick > RESPAWN_TIME) {
				// respawn the snake, reset counter and collided
				enemyRespawnTick = 0;
				enemyHead.setCollided(false);
				
				// get random valid starting position and respawn there
				resetEnemyBody();
				enemyHead.resetPosition(Methods.GenerateRandomValidPosition(grid));
			}
		}

		// do same for player
		if(playerHead.isCollided()) {
			playerRespawnTick++;

			if(playerRespawnTick > RESPAWN_TIME) {
				playerRespawnTick = 0;
				playerHead.setCollided(false);
				
				resetPlayerBody();
				playerHead.resetPosition(Methods.GenerateRandomValidPosition(grid));
			}
		}

		// move snake first before updating any fruit collisions
		playerHead.update();
		enemyHead.update();

		// check if snake heads are collided
		// if this happens despawn both heads
		if(playerHead.getX() == enemyHead.getX() && playerHead.getY() == enemyHead.getY() && !playerHead.isCollided() && !enemyHead.isCollided()) {
			game.playSoundEffect(HIT);
			playerHead.setCollided(true);
			enemyHead.setCollided(true);
		}

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

    @Override
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

		// draw grid last
		drawButtonAndGrid(g);
    }
    
    // getters and setters

	public void resetEnemyBody() {
		// remove all the snake body length
		while(enemyBody.size() > 1) {
			EnemyBody currentBody = enemyBody.removeLast();
			grid[currentBody.getY() / TILE_SIZE][currentBody.getX() / TILE_SIZE] = GridObject.EMPTY;
		}
	}

	@Override
	public EnemyHead getEnemyHead() {
		return enemyHead;
	}

	@Override	
    public LinkedList<EnemyBody> getEnemyBody() {
        return enemyBody;
    }

	public int getTimeLeft() {
		// prevent timer going negative
		return Math.max(timeLeftTick / UPS, 0);
	}
}