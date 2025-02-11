package gameobjects;
import main.GameManager;
import main.Utilities.Methods;

import static main.Utilities.Constants.SnakeConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class SnakeHead extends SnakeBody{ // inherit as attributes will be the same

    private boolean collided = false; // check if snake hits something and therefore game over/stunned
    private BufferedImage img, left, right, up, down; // EnemySnakeHead can inherit
    private Direction direction;

    public SnakeHead(int xPos, int yPos, GameManager game) {
        super(xPos, yPos, 0, null, game);
        this.direction = Direction.UP;

        initImg();
    }

    protected void initImg() {
        left = Methods.LoadImage("snake/player/snake_head_left");
        right = Methods.LoadImage("snake/player/snake_head_right");
        up = Methods.LoadImage("snake/player/snake_head_up");
        down = Methods.LoadImage("snake/player/snake_head_down");
    }

    protected void checkCollisions() {
        for(int i=1;i<game.getPlayerBody().size();i++) {
            // start at index 1 as snake head is head of linked list
            SnakeBody currentBody = game.getPlayerBody().get(i);

            if(currentBody.getX() == xPos && currentBody.getY() == yPos) {
                if(currentBody.isSpawned()) { // can only collide if the body is 'spawned' in
                    // collided with body so stop game
                    collided = true;
                    return;
                }
            }
        }
    }

    @Override
    protected void move() {
        // move the snake position in its direction
        switch(direction) {
            case UP:
                yPos -= SNAKE_HEIGHT;
                img = up;
                break;
            case DOWN:
                yPos += SNAKE_HEIGHT;
                img = down;
                break;
            case LEFT:
                xPos -= SNAKE_WIDTH;
                img = left;
                break;
            case RIGHT:
                xPos += SNAKE_WIDTH;
                img = right;
                break;
        }
    }
    
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, xPos - 2, yPos - 2, SNAKE_WIDTH, SNAKE_HEIGHT, null);
    }

    @Override
    public void update() {
        // update previous positions for snakeBody to move to
        previousXPos = xPos;
        previousYPos = yPos;
        
        // check if snake collides with anything, and if so then stop moving
        checkCollisions(); 
        if(collided) return;

        // move the snake in given direction, wrapping around if applicable
        move();
        checkEdge();
    }

    // getters and setters
    
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isCollided() {
        return collided;
    }
}
