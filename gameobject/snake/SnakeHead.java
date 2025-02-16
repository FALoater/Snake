package gameobject.snake;
import main.GridObject;
import main.Utilities.Methods;

import static main.Utilities.Constants.SnakeConstants.*;
import static main.Utilities.Constants.WindowConstants.TILE_SIZE;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameobject.Direction;
import gamestate.GameStateType;
import gamestate.PlayingGameState;

public class SnakeHead extends SnakeBody{
    // inherited attributes from superclass

    private boolean collided = false; // check if snake hits something and therefore game over/stunned
    private BufferedImage img, left, right, up, down;
    private Direction direction;

    public SnakeHead(int xPos, int yPos, PlayingGameState gameState) {
        super(xPos, yPos, null, gameState);
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
        GridObject object = gameState.getObjectAtGridPos(xPos, yPos);

        if(object != GridObject.EMPTY && object != GridObject.FRUIT && object != GridObject.PLAYER_HEAD && !collided) {
            // collided with snake body
            gameState.setGrid(xPos, yPos, GridObject.EMPTY);
            collided = true;
            
            // gamemode check
            if(gameState.getGameManager().getGameState() == GameStateType.VERSUS_GAME) {
                gameState.resetPlayerScore();
            } else {
                gameState.getGame().changeGameState(GameStateType.END_SCREEN);
            }
        }
    }

    @Override
    protected void move() {
        // move the snake position in its direction
        gameState.setGrid(xPos, yPos, GridObject.EMPTY);

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
        gameState.setGrid(xPos, yPos, GridObject.PLAYER_HEAD);

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

    public void resetPosition(int[] position) {
        previousXPos = xPos;
        previousYPos = yPos;
        // scale index positions to coordinates
        xPos = position[0] * TILE_SIZE;
        yPos = position[1] * TILE_SIZE;
    }
}
