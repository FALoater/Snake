package gameobjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.GameManager;

import static main.Utilities.Constants.FruitConstants.*;
import static main.Utilities.Methods;

public class Fruit {
    private boolean deleteFlag = false;
    private int xPos, yPos, pointValue;

    private BufferedImage img;
    private FruitType type;
    private GameManager game;

    public Fruit(int xPos, int yPos, FruitType type, GameManager game) {
        this.game = game;
        this.xPos = xPos;
        this.yPos = yPos;
        this.type = type;

        switch(type) { // set point value and color based on fruit type
            case APPLE:
                pointValue = 1;
                break;
            case ORANGE:
                pointValue = 3;
        }

        loadImg();
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
        }
    }
 
    public void update() {
        checkCollision();
    }

    public void draw(Graphics g) {
        if(!deleteFlag) g.drawImage(img, xPos, yPos, FRUIT_RADIUS, FRUIT_RADIUS, null);
    }

    // getters and setters

    public boolean isDeleteFlag() {
        return deleteFlag;
    }
}