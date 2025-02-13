package gameobjects.snake;

import main.GameManager;
import main.GridObject;

import static main.Utilities.Constants.SnakeConstants.*;
import static main.Utilities.Constants.WindowConstants.*;
import static main.Utilities.Methods;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import gameobjects.Direction;
import gameobjects.Fruit;

public class EnemyHead extends EnemyBody {

    private boolean collided = false; // check if snake hits something and therefore game over/stunned
    private BufferedImage img, left, right, up, down; // EnemySnakeHead can inherit
    private Direction direction = Direction.UP;
    private Random rand = new Random();

    public EnemyHead(int xPos, int yPos, GameManager game) {
        super(xPos, yPos, 0, null, game);
        snakeColor = ENEMY_SNAKE_DEFAULT_COLOR;

        initImg();
    }

    private void initImg() {
        left = Methods.LoadImage(ENEMY_HEAD_LEFT_IMG);
        right = Methods.LoadImage(ENEMY_HEAD_RIGHT_IMG);
        up = Methods.LoadImage(ENEMY_HEAD_UP_IMG);
        down = Methods.LoadImage(ENEMY_HEAD_DOWN_IMG);
    }

    private boolean checkSafe(GridObject object) {
        return object == GridObject.EMPTY || object == GridObject.FRUIT;
    }

    private void pathfindToFruit() {

        // find position of closest fruit
        double closestDistance = Double.POSITIVE_INFINITY; // initalise to be largest possible
        int fruitX = 0, fruitY = 0;

        // iterate through fruits to determine nearest fruit by pythagorean distance
        for(Fruit fruit : game.getFruits()) {
            double distance = Methods.SquaredDistanceBetween(xPos, yPos, fruit.getX(), fruit.getY());// no need to square root as this will be the same

            // minimising function for distance
            if(distance < closestDistance) {
                closestDistance = distance;
                fruitX = fruit.getX();
                fruitY = fruit.getY();
            }
        }

        // calculate which directions are possible
        LinkedList<Direction> possibleDirections = new LinkedList<Direction>();

        // store distance of each direction as a hashmap to act as a heuristic
        HashMap<Double, Direction> distances = new HashMap<Double, Direction>();

        // calculate index, adjusting for edges of the screen
        int upY = Math.floorMod(yPos - TILE_SIZE, WINDOW_HEIGHT);
        int downY = Math.floorMod(yPos + TILE_SIZE, WINDOW_HEIGHT);
        int leftX = Math.floorMod(xPos - TILE_SIZE, WINDOW_WIDTH);
        int rightX = Math.floorMod(xPos + TILE_SIZE, WINDOW_WIDTH);

        // find objects

        GridObject upObject = game.getObjectAtGridPos(xPos, upY);
        GridObject downObject = game.getObjectAtGridPos(xPos, downY);
        GridObject leftObject = game.getObjectAtGridPos(leftX, yPos);
        GridObject rightObject = game.getObjectAtGridPos(rightX, yPos);

        // switch case for directions, ensuring no 180 degree turns
        switch(direction) {
            case DOWN:
                if(checkSafe(leftObject)) {
                    possibleDirections.add(Direction.LEFT);
                    distances.put(Methods.SquaredDistanceBetween(fruitX, fruitY, leftX, yPos), Direction.LEFT);
                }

                if(checkSafe(rightObject)) {
                    possibleDirections.add(Direction.RIGHT);
                    distances.put(Methods.SquaredDistanceBetween(fruitX, fruitY, rightX, yPos), Direction.RIGHT);
                }

                if(checkSafe(downObject)) {
                    possibleDirections.add(Direction.DOWN);
                    distances.put(Methods.SquaredDistanceBetween(fruitX, fruitY, xPos, downY), Direction.DOWN);
                }
                break;
            case LEFT:
                if(checkSafe(leftObject)) {
                    possibleDirections.add(Direction.LEFT);
                    distances.put(Methods.SquaredDistanceBetween(fruitX, fruitY, leftX, yPos), Direction.LEFT);
                }

                if(checkSafe(downObject)) {
                    possibleDirections.add(Direction.DOWN);
                    distances.put(Methods.SquaredDistanceBetween(fruitX, fruitY, xPos, downY), Direction.DOWN);
                }

                if(checkSafe(upObject)) {
                    possibleDirections.add(Direction.UP);
                    distances.put(Methods.SquaredDistanceBetween(fruitX, fruitY, xPos, upY), Direction.UP);
                }
                break;
            case RIGHT:
                if(checkSafe(rightObject)) {
                    possibleDirections.add(Direction.RIGHT);
                    distances.put(Methods.SquaredDistanceBetween(fruitX, fruitY, rightX, yPos), Direction.RIGHT);
                }

                if(checkSafe(downObject)) {
                    possibleDirections.add(Direction.DOWN);
                    distances.put(Methods.SquaredDistanceBetween(fruitX, fruitY, xPos, downY), Direction.DOWN);
                }

                if(checkSafe(upObject)) {
                    possibleDirections.add(Direction.UP);
                    distances.put(Methods.SquaredDistanceBetween(fruitX, fruitY, xPos, upY), Direction.UP);
                }
                break;
            case UP:
                if(checkSafe(leftObject)) {
                    possibleDirections.add(Direction.LEFT);
                    distances.put(Methods.SquaredDistanceBetween(fruitX, fruitY, leftX, yPos), Direction.LEFT);
                }

                if(checkSafe(rightObject)) {
                    possibleDirections.add(Direction.RIGHT);
                    distances.put(Methods.SquaredDistanceBetween(fruitX, fruitY, rightX, yPos), Direction.RIGHT);
                }

                if(checkSafe(upObject)) {
                    possibleDirections.add(Direction.UP);
                    distances.put(Methods.SquaredDistanceBetween(fruitX, fruitY, xPos, upY), Direction.UP);
                }
                break;
        }

        // find best possible direction to take
        Direction bestDirection;

        // check x direction first
        if(fruitX != xPos) {
            if(fruitX > xPos) {
                // fruit is on right
                bestDirection = Direction.RIGHT;
            } else {
                // fruit is on left
                bestDirection = Direction.LEFT; 
            }
        } else {
            // check y direction
            if(fruitY > yPos) {
                // fruit is below
                bestDirection = Direction.DOWN;
            } else {
                // fruit is above
                bestDirection = Direction.UP;
            }
        }

        // if the snake is trapped it has no where to go so continue to move in original direction
        if(possibleDirections.size() == 0) {
            return;
        }

        //finally choose direction
        // if can turn to best possible, then do so
        if(possibleDirections.contains(bestDirection)) {
            direction = bestDirection;
        } else {
            // calculate best distance from positions
            // get array of keys and find lowest value
            double minimumValue = Double.POSITIVE_INFINITY;

            for(double distance : distances.keySet()) {
                if(distance < minimumValue) {
                    minimumValue = distance;
                }
            }
            
            // get direction with this minimum value
            direction = distances.get(minimumValue);
        }

        if(direction == null) { // if snake is trapped and has nowhere to
            direction = possibleDirections.get(rand.nextInt(possibleDirections.size()));
        }

    }

    private void resetPosition() {
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
        game.resetEnemyBody();
        resetPosition();
    }

    @Override
    protected void checkCollisions() {
        GridObject object = game.getObjectAtGridPos(xPos, yPos);

        if(object != GridObject.EMPTY && object != GridObject.FRUIT && object != GridObject.ENEMY_HEAD) {
            // make sure collided when player snake is still active
            if(!collided) {
                game.setGrid(xPos, yPos, GridObject.EMPTY);
                collided = true;
                game.resetEnemyScore();
            }
        }
    }

    @Override
    protected void move() {
        game.setGrid(xPos, yPos, GridObject.EMPTY);

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

        checkEdge();
    }

    @Override
    public void update() {

        // // update previous positions for snakeBody to move to
        previousXPos = xPos;
        previousYPos = yPos;

        // check if snake collides with anything, and if so then stop moving
        checkCollisions(); 
        if(collided) return;
        game.setGrid(xPos, yPos, GridObject.ENEMY_HEAD);

        // change direction depending on closest fruit
        pathfindToFruit();

        // move the snake in given direction, wrapping around if applicable
        move();
    }

    @Override
    public void draw(Graphics g) {
        if(!collided) g.drawImage(img, xPos - 2, yPos - 2, SNAKE_WIDTH, SNAKE_HEIGHT, null);
    }

    // getters and setterss

    public boolean isCollided() {
        return collided;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }
}