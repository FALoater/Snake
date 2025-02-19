package gameobject.snake;

import java.awt.Color;
import java.awt.Graphics;

import gamestate.PlayingGameState;
import main.GridObject;

import static main.Utilities.Constants.SnakeConstants.*;
import static main.Utilities.Constants.WindowConstants.*;

public class SnakeBody {
    
    private SnakeHead playerHead;

    // protected so enemy snake can also access
    protected boolean spawned; // inital spawn check to ensure body does not spawn on top of head
    protected int xPos, yPos;
    protected int previousXPos = xPos, previousYPos = yPos;
    
    protected Color snakeColor = PLAYER_SNAKE_DEFAULT_COLOR; // default color is green
    protected PlayingGameState gameState;
    protected SnakeBody nextBody;

    public SnakeBody(int xPos, int yPos, SnakeBody nextBody, PlayingGameState gameState) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.nextBody = nextBody; 
        this.gameState = gameState;
        playerHead = gameState.getPlayerHead();
    }

    protected void checkEdge() {
        // loop snake back around if it goes through the sides of the game
        if(yPos + SNAKE_HEIGHT > WINDOW_HEIGHT) {
            yPos = TILE_SIZE * 2;
        } else if (yPos < TILE_SIZE * 2) {
            yPos = WINDOW_HEIGHT - SNAKE_WIDTH;
            // place one early as position of square is bottom left
        }

        if(xPos + SNAKE_WIDTH > WINDOW_WIDTH) {
            xPos = 0;
        } else if (xPos < 0) {
            xPos = WINDOW_WIDTH - SNAKE_WIDTH;
        }
    }

    protected void move() {
        // move to the position of the tile infront
        xPos = nextBody.getPreviousXPos();
        yPos = nextBody.getPreviousYPos();

        checkEdge();
    }

    protected void updatePosition() {
        // check if position of tile is currently occupied
        if(gameState.getObjectAtGridPos(xPos, yPos) == GridObject.EMPTY) {
            spawned = true;
            gameState.setGrid(xPos, yPos, GridObject.PLAYER_BODY);
        }
    }

    public void draw(Graphics g) {
        // only draw if snake body is spawned in and the snake head is active
        if(spawned && !playerHead.isCollided()) { 
            g.setColor(Color.black);
            g.drawRect(xPos - 1, yPos - 1, SNAKE_WIDTH + 1, SNAKE_HEIGHT + 1); // first fill a black square
            g.setColor(snakeColor); // change colour to red
            g.fillRect(xPos, yPos, SNAKE_WIDTH, SNAKE_HEIGHT); // fill with red a slightly smaller square to allow for black border
        }
    }

    public void update() {
        // unspawn if the player head is stunned
        if(gameState.getPlayerHead().isCollided()) {
            spawned = false;
        }

        updatePosition();

        // can update if spawned
        if(spawned) {
            previousXPos = xPos;
            previousYPos = yPos;
            
            // update grid values
            gameState.setGrid(xPos, yPos, GridObject.EMPTY);
            move(); // 'move' snake body to the position of the body infront
            gameState.setGrid(xPos, yPos, GridObject.PLAYER_BODY);
        }
    }

    // getters and setters

    public void setColor(Color color) {
        this.snakeColor = color;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public int getPreviousXPos() {
        return previousXPos;
    }

    public int getPreviousYPos() {
        return previousYPos;
    }

    public boolean isSpawned() {
        return spawned;
    }

    public void setPosition(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }
}