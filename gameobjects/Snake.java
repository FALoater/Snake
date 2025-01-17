package gameobjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static main.Utilities.Methods.*;
import static main.Utilities.Constants.SnakeConstants.*;
import static main.Utilities.Constants.WindowConstants.*;

public class Snake{

    private boolean collided = false; // check if snake hits something and therefore game over/stunned
    private int xPos, yPos;
    
    private BufferedImage snakeImg;
    private Orientation direction = Orientation.RIGHT;

    enum Orientation { // easier to use rather than integers to encode direction
        UP,
        RIGHT,
        LEFT,
        DOWN
    }
    
    public Snake(int xPos, int yPos) {
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
    }

    public void draw(Graphics g) {
        g.drawImage(snakeImg, xPos, yPos, SNAKE_WIDTH, SNAKE_HEIGHT, null);
        //          image,     x,     y,  image width, image height,  observer
    }
}