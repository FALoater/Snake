package gameobjects.snake;
import main.GameManager;
import main.GridObject;
import main.Utilities.Methods;

import static main.Utilities.Constants.SnakeConstants.*;
import static main.Utilities.Constants.WindowConstants.TILE_SIZE;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameobjects.Direction;

public class SnakeHead extends SnakeBody{ // inherit as attributes will be the same

    private boolean collided = false; // check if snake hits something and therefore game over/stunned
    private BufferedImage img, left, right, up, down;
    private Direction direction;

    public SnakeHead(int xPos, int yPos, GameManager game) {
        super(xPos, yPos, null, game);
        direction = Direction.UP;
        initImg();
    }

    protected void initImg() {
        left = Methods.LoadImage(PLAYER_HEAD_LEFT_IMG);
        right = Methods.LoadImage(PLAYER_HEAD_RIGHT_IMG);
        up = Methods.LoadImage(PLAYER_HEAD_UP_IMG);
        down = Methods.LoadImage(PLAYER_HEAD_DOWN_IMG);
    }

    protected void checkCollisions() {
        // get object at current position
        GridObject object = game.getObjectAtGridPos(xPos, yPos);

        if(object != GridObject.EMPTY && object != GridObject.FRUIT && object != GridObject.PLAYER_HEAD && !collided) {
            // collided with snake body
            game.setGrid(xPos, yPos, GridObject.EMPTY);
            collided = true;
            game.resetPlayerScore();
        }
    }

    private void resetPosition() {
        // update previous positions so snake body can move
        previousXPos = xPos;
        previousYPos = yPos;

        // get random valid starting position and respawn there
        int[] newPosition = Methods.GenerateRandomValidPosition(game);

        // scale index positions to coordinates
        xPos = newPosition[0] * TILE_SIZE;
        yPos = newPosition[1] * TILE_SIZE;
    }

    public void resetSnake() {
        // reset snake after it has respawned
        game.resetPlayerBody();
        resetPosition();
    }

    @Override
    protected void move() {
        // move the snake position in its direction
        game.setGrid(xPos, yPos, GridObject.EMPTY);

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

        // check if wrapping around the corner of the screen
        checkEdge();
    }
    
    @Override
    public void draw(Graphics g) {
        // only draw if not stunned
        if(!collided) g.drawImage(img, xPos - 2, yPos - 2, SNAKE_WIDTH, SNAKE_HEIGHT, null);
    }

    @Override
    public void update() {
        // update previous positions for snakeBody to move to
        previousXPos = xPos;
        previousYPos = yPos;

        // check if snake collides with anything, and if so then stop moving
        checkCollisions(); 
        if(collided) return;
        game.setGrid(xPos, yPos, GridObject.PLAYER_HEAD);

        // move the snake in given direction, wrapping around if applicable
        move();
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

    public void setCollided(boolean collided) {
        this.collided = collided;
    }
}
