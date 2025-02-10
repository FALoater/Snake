package gameobjects;

import java.awt.Color;
import java.awt.Graphics;

import main.GameManager;

import static main.Utilities.Constants.SnakeConstants.*;
import static main.Utilities.Constants.WindowConstants.*;

public class SnakeBody {

    // private as the snake head does not require these attributes
    private boolean spawned; // for inital spawn check
    private int index;

    protected int xPos, yPos;
    protected int previousXPos = xPos, previousYPos = yPos;
    protected Color snakeColor;
    protected GameManager game;
    protected SnakeBody nextBody;
    // all needed, including the SnakeHead

    public SnakeBody(int xPos, int yPos, int index, SnakeBody nextBody, GameManager game) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.index = index;
        this.nextBody = nextBody; 
        this.game = game;

        this.snakeColor = Color.red;
    }

    public void checkEdge() {
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

    public void move() {
        // move to the position of the tile infront
        xPos = nextBody.getPreviousXPos();
        yPos = nextBody.getPreviousYPos();
    }

    public void draw(Graphics g) {
        if(spawned) { // only draw if the body is spawned in
            g.setColor(Color.black);
            g.fillRect(xPos - 2, yPos - 2, SNAKE_WIDTH, SNAKE_HEIGHT); // first fill a black square
            g.setColor(snakeColor); // change colour to red
            g.fillRect(xPos, yPos, SNAKE_WIDTH - 4, SNAKE_HEIGHT - 4); // fill with red a slightly smaller square to allow for black border
        }
    }

    public void update() {
        // check if position of tile is currently occupied
        for(SnakeBody body : game.getBody()) {
            if(body.getX() == xPos && body.getY() == yPos) {
                // check if this element occupies this square
                if(body.getIndex() == index) {
                    // spawn
                    spawned = true;
                    break;
                } else {
                    // no snake body can occupy 2 squares at the same time so end early
                    return;
                }
            }
        }

        previousXPos = xPos;
        previousYPos = yPos;

        move();
        checkEdge();
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

    public int getIndex() {
        return index;
    }
}