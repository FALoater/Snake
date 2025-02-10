package gameobjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.GameManager;

import static main.Utilities.Constants.FruitConstants.*;
import static main.Utilities.Methods;

public class Fruit {
    private boolean deleteFlag = false; // signals whether the fruit has been eaten
    private int xPos, yPos, pointValue;

    private BufferedImage img;
    private FruitType type;
    private GameManager game;

    public Fruit(int xPos, int yPos, FruitType type, GameManager game) {
        this.game = game;
        this.xPos = xPos;
        this.yPos = yPos;
        this.type = type;

        switch(type) { // set point value and color based on fruit type, switch case is easier to read
            case APPLE:
                pointValue = 1;
                break;
            case ORANGE:
                pointValue = 3;
        }

        loadImg(); // load appropriate fruit sprite
    }

    private void loadImg() {
        switch(type) {
            case APPLE:
                img = Methods.LoadImage("fruits/apple");
                break;
            case ORANGE:
                img = Methods.LoadImage("fruits/orange");
                break;
        }
    }

    public void checkCollision() {
        SnakeHead snake = game.getPlayerSnake();
        
        int snakeX = snake.getX();
        int snakeY = snake.getY();

        if(snakeX == xPos && snakeY == yPos) { // compare x and y since this is always done from top left corner
            game.fruitEaten(pointValue); 
            deleteFlag = true;
            // increase score and send flag for fruit to be deleted
        }
    }
 
    public void update() {
        checkCollision(); // fruit does not move, only listens for collisions with the snake
    }

    public void draw(Graphics g) {
        if(!deleteFlag) g.drawImage(img, xPos, yPos, FRUIT_RADIUS, FRUIT_RADIUS, null);
    }

    // getters and setters

    public boolean isDeleteFlag() {
        return deleteFlag;
    }
}