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

        switch(type) {
            case FruitType.APPLE:
                pointValue = 1;
                color = Color.red;
                break;
            case FruitType.ORANGE:
                pointValue = 2;
                color = Color.orange;
        }
    }

    public void checkCollision() {
        Snake snake = game.getPlayerSnake();
        
        int snakeX = snake.getX();
        int snakeY = snake.getY();

        if(snakeX == xPos && snakeY == yPos) {
            color = Color.black;
            xPos = 2000;
            yPos = 2000;
            game.getGamePanel().updateScore(pointValue);
        }
    }
 
    public void update() {
        checkCollision();
    }

    public void draw(Graphics g) {
        g.setColor(color);

        if(!deleteFlag) {
            switch(type) {
                case FruitType.APPLE:
                    g.fillOval(xPos, yPos, APPLE_RADIUS, APPLE_RADIUS);
                    break;
                case FruitType.ORANGE:
                    g.fillOval(xPos, yPos, ORANGE_RADIUS, ORANGE_RADIUS);
                    break;
            }
        }
    }
}