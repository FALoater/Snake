package gameobjects.snake;

import java.awt.Color;
import java.awt.Graphics;

import main.GameManager;
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
    protected GameManager game;
    protected SnakeBody nextBody;

    public SnakeBody(int xPos, int yPos, int index, SnakeBody nextBody, GameManager game) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.nextBody = nextBody; 
        this.game = game;
        playerHead = game.getPlayerSnakeHead(); // removes need to pass in as parameter
    }

    protected void checkEdge() {
        // loop snake back around if it goes through the sides of the game
        if(yPos + SNAKE_HEIGHT > WINDOW_HEIGHT) {
            yPos = 0;
        } else if (yPos < 0) {
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

    protected void checkCollisions() {
        // check if position of tile is currently occupied
        if(game.getObjectAtGridPos(xPos, yPos) == GridObject.EMPTY) {
            spawned = true;
            game.setGrid(xPos, yPos, GridObject.PLAYER_BODY);
        }
    }

    public void draw(Graphics g) {
        // only draw if snake body is spawned in and the snake head is active
        if(spawned && !playerHead.isCollided()) { 
            g.setColor(Color.black);
            g.fillRect(xPos - 2, yPos - 2, SNAKE_WIDTH, SNAKE_HEIGHT); // first fill a black square
            g.setColor(snakeColor); // change colour to red
            g.fillRect(xPos, yPos, SNAKE_WIDTH - 4, SNAKE_HEIGHT - 4); // fill with red a slightly smaller square to allow for black border
        }
    }

    public void update() {
        // unspawn if the player head is stunned
        if(game.getPlayerSnakeHead().isCollided()) {
            spawned = false;
        }

        checkCollisions();

        // can update if spawned
        if(spawned) {
            previousXPos = xPos;
            previousYPos = yPos;
    
            game.setGrid(xPos, yPos, GridObject.EMPTY);
            move(); // 'move' snake body to the position of the body infront
            game.setGrid(xPos, yPos, GridObject.PLAYER_BODY);
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
}