package gameobjects;

import java.awt.Color;
import java.awt.Graphics;

import static main.Utilities.Constants.SnakeConstants.*;
import static main.Utilities.Constants.WindowConstants.*;

public class SnakeBody {

    protected int xPos, yPos;
    protected int previousXPos = xPos, previousYPos = yPos;
    protected Color snakeColor;
    protected SnakeBody nextBody;
    // all needed, including the SnakeHead

    public SnakeBody(int xPos, int yPos, SnakeBody nextBody) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.nextBody = nextBody; 

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
        g.setColor(snakeColor); // can change later
        g.fillRect(xPos, yPos, SNAKE_WIDTH, SNAKE_HEIGHT);
    }

    public void update() {
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
}