package gameobjects;

import java.awt.Color;
import java.awt.Graphics;

import main.GameManager;

import static main.Utilities.Constants.SnakeConstants.*;
import static main.Utilities.Constants.WindowConstants.*;

public class Snake{

    private boolean collided = false; // check if snake hits something and therefore game over/stunned

    private int xPos, yPos;

    private Color snakeColor = Color.green;
    private GameManager game;
    private Orientation direction = Orientation.UP;
    
    public Snake(int xPos, int yPos, GameManager game) {
        this.game = game;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void update() {
        // move the snake position in its direction
        switch(direction) {
            case Orientation.UP:
                yPos -= SNAKE_HEIGHT;
                break;
            case Orientation.DOWN:
                yPos += SNAKE_HEIGHT;
                break;
            case Orientation.LEFT:
                xPos -= SNAKE_WIDTH;
                break;
            case Orientation.RIGHT:
                xPos += SNAKE_WIDTH;
                break;
        }

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

    public void draw(Graphics g) {
        g.setColor(snakeColor); // can change later
        g.fillRect(xPos, yPos, SNAKE_WIDTH, SNAKE_HEIGHT);
    }

    // GETTERS AND SETTERS
    public void setDirection(Orientation direction) {
        this.direction = direction;
    }

    public Orientation getDirection() {
        return direction;
    }

    public void setColor(Color color) {
        this.snakeColor = color;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }
}