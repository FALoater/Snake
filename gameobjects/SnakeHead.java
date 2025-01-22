package gameobjects;
import main.GameManager;

import static main.Utilities.Constants.SnakeConstants.*;

public class SnakeHead extends SnakeBody{ // inherit as attributes will be the same

    private boolean collided = false; // check if snake hits something and therefore game over/stunned

    private GameManager game;
    protected Orientation direction;
    
    public SnakeHead(int xPos, int yPos, GameManager game) {
        super(xPos, yPos, null);
        this.game = game;
        this.direction = Orientation.UP;
    }

    @Override
    public void move() {
        // move the snake position in its direction
        // save these values for the body behind to move to

        switch(direction) {
            case UP:
                yPos -= SNAKE_HEIGHT;
                break;
            case DOWN:
                yPos += SNAKE_HEIGHT;
                break;
            case LEFT:
                xPos -= SNAKE_WIDTH;
                break;
            case RIGHT:
                xPos += SNAKE_WIDTH;
                break;
        }
    }
    // draw already defined in parent class

    // getters and setters
    
    public void setDirection(Orientation direction) {
        this.direction = direction;
    }

    public Orientation getDirection() {
        return direction;
    }
}
