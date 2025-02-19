package gamestate;

import java.awt.Graphics;
import java.util.Arrays;

import main.GameManager;
import main.GridObject;
import gameobject.Fruit;
import gameobject.snake.SnakeBody;
import gameobject.snake.SnakeHead;

import static main.Utilities.Constants.SnakeConstants.*;
import static main.Utilities.Constants.WindowConstants.*;

public class ClassicGame extends PlayingGameState {
    // for singleplayer mode

    public ClassicGame(GameManager game) {
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

        // add snake heads to grid
		grid[PLAYER_START_Y / TILE_SIZE][PLAYER_START_X / TILE_SIZE] = GridObject.PLAYER_HEAD;

        // generate 5 fruits at random locations
		for(int i=0;i<5;i++) {
			generateFruit(); 
		}
    }

	public void reset() {
		// reset snakes and body
		playerHead.setPosition(PLAYER_START_X, PLAYER_START_Y);
		playerHead.setCollided(false);
		resetPlayerBody();

		// reset score
		game.getGamePanel().clearScore();

		// reset fruits
		fruits.clear();

		for(int i=0;i<5;i++) {
			generateFruit(); 
		}
	}	
    
    @Override
    public void update() {
		// move snake first before updating any fruit collisions
		// stop updating game entirely if player is collided, only in classic mode
		if(playerHead.isCollided()) return;
		playerHead.update();

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
    }

	@Override
	public void fruitEaten(int score, int collisionType) {
		game.getGamePanel().updatePlayerScore(score);
		for(int i=0;i<score;i++) {
			SnakeBody lastBody = playerBody.get(playerBody.size() - 1); // 'pointer' for new body
			playerBody.add(new SnakeBody(lastBody.getX(), lastBody.getY(), lastBody, this));	// added in linkedlist but not yet spawned as grid could be occupied
		}
	}

    @Override
    public void draw(Graphics g) {
		// draw grid first
		drawButtonAndGrid(g);

        // playerBody should be above fruits so draw fruits first
		for(int i=0;i<fruits.size();i++) {
			fruits.get(i).draw(g);
		}

		// does not matter which snake is drawn first as they never overlap
		for(int i=0;i<playerBody.size();i++) {
			playerBody.get(i).draw(g);
		}

		// draw the snake head
		playerHead.draw(g);
    }
}