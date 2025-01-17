package gameobjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.GameManager;

import static main.Utilities.Methods.*;
import static main.Utilities.Constants.SnakeConstants.*;
import static main.Utilities.Constants.WindowConstants.*;

public class Snake{

    private boolean collided = false; // check if snake hits something and therefore game over/stunned
    private int xPos, yPos;
    
    private BufferedImage snakeImg;
    private GameManager game;
    private Orientation direction = Orientation.UP;
    
    public Snake(int xPos, int yPos, GameManager game) {
        this.game = game;
        this.xPos = xPos;
        this.yPos = yPos;

        loadImg(); // load the snake image on instantiation
    }

    public void loadImg() {
        snakeImg = LoadImage("snake/snake_body");
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
        } else if (yPos - SNAKE_HEIGHT < 0) {
            yPos = WINDOW_HEIGHT;
        }

        if(xPos + SNAKE_WIDTH > WINDOW_WIDTH) {
            xPos = 0;
        } else if (xPos - SNAKE_WIDTH < 0) {
            xPos = WINDOW_WIDTH;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(snakeImg, xPos, yPos, SNAKE_WIDTH, SNAKE_HEIGHT, null);
        //          image,     x,     y,  image width, image height,  observer
    }

    public void setDirection(Orientation direction) {
        this.direction = direction;
    }
}