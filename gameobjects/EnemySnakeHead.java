package gameobjects;

import main.GameManager;
import main.Utilities.Methods;

import static main.Utilities.Constants.SnakeConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class EnemySnakeHead extends EnemySnakeBody {

    private boolean collided = false; // check if snake hits something and therefore game over/stunned
    private BufferedImage img, left, right, up, down; // EnemySnakeHead can inherit
    private Direction direction;

    public EnemySnakeHead(int xPos, int yPos, GameManager game) {
        super(xPos, yPos, 0, null, game);
        snakeColor = ENEMY_SNAKE_DEFAULT_COLOR;

        initImg();
    }

    private void initImg() {
        left = Methods.LoadImage("snake/enemy/snake_head_left");
        right = Methods.LoadImage("snake/enemy/snake_head_right");
        up = Methods.LoadImage("snake/enemy/snake_head_up");
        down = Methods.LoadImage("snake/enemy/snake_head_down");
    }
    
    private int[] findClosestFruit() {
        double closestDistance = Double.POSITIVE_INFINITY; // initalise to be largets possible
        int fruitX = 0, fruitY = 0;

        // iterate through fruits to determine nearest fruit by pythagorean distance
        for(Fruit fruit : game.getFruits()) {
            double distance = Math.pow(fruit.getX() - xPos, 2) + Math.pow(fruit.getY() - yPos, 2); // no need to square root yet

            if(distance < closestDistance) {
                closestDistance = distance;
                fruitX = fruit.getX();
                fruitY = fruit.getY();
            }
        }
        int[] out = {fruitX, fruitY};
        return out;
    }

    private void pathfindToFruit() {
        int[] closestFruit = findClosestFruit();
        int fruitX = closestFruit[0];
        int fruitY = closestFruit[1];

        // check x direction first
        if(fruitX != xPos) {
            if(fruitX > xPos) {
                // fruit is on right
                direction = Direction.RIGHT;
            } else {
                // fruit is on left
                direction = Direction.LEFT; 
            }
        } else {
            // check y direction
            if(fruitY > yPos) {
                // fruit is below
                direction = Direction.DOWN;
            } else {
                // fruit is above
                direction = Direction.UP;
            }
        }
    }

    @Override
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
    public void update() {

        // // update previous positions for snakeBody to move to
        previousXPos = xPos;
        previousYPos = yPos;
        
        // check if snake collides with anything, and if so then stop moving
        checkCollisions(); 
        if(collided) return;

        // change direction depending on closest fruit
        pathfindToFruit();

        // move the snake in given direction, wrapping around if applicable
        move();
        checkEdge();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, xPos - 2, yPos - 2, SNAKE_WIDTH, SNAKE_HEIGHT, null);
    }

    // getters and setters

    public boolean isCollided() {
        return collided;
    }
}