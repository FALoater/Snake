package gameobjects;

import java.awt.Color;
import java.awt.Graphics;

import main.GameManager;

import static main.Utilities.Constants.FruitConstants.*;

public class Fruit {
    private boolean deleteFlag = false;
    private int xPos, yPos, pointValue;

    private Color color;
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
                color = Color.red;
                break;
            case ORANGE:
                pointValue = 2;
                color = Color.orange;
        }
    }

    public void checkCollision() {
        SnakeHead snake = game.getPlayerSnake();
        
        int snakeX = snake.getX();
        int snakeY = snake.getY();

        if(snakeX == xPos && snakeY == yPos) { // compare x and y since this is always done from top left corner
            color = Color.black;
            game.fruitEaten(pointValue);
            deleteFlag = true;
        }
    }
 
    public void update() {
        checkCollision();
    }

    public void draw(Graphics g) {
        g.setColor(color);

        if(!deleteFlag) { // draw fruits as circle to distinguish from snake
            switch(type) {
                case APPLE:
                    g.fillOval(xPos, yPos, APPLE_RADIUS, APPLE_RADIUS);
                    break;
                case ORANGE:
                    g.fillOval(xPos, yPos, ORANGE_RADIUS, ORANGE_RADIUS);
                    break;
            }
        }
    }

    // getters and setters

    public boolean isDeleteFlag() {
        return deleteFlag;
    }
}