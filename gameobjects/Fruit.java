package gameobjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameobjects.snake.EnemyHead;
import gameobjects.snake.SnakeHead;
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

        // set point value and color based on fruit type, switch case is easier to read
        switch(type) { 
            case APPLE:
                pointValue = 1;
                break;
            case ORANGE:
                pointValue = 3;
        }

        // load appropriate fruit sprite
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
        // get both x and y of both snakes
        SnakeHead playerSnake = game.getPlayerSnakeHead();
        EnemyHead enemySnake = game.getEnemySnakeHead();
        
        int playerSnakeX = playerSnake.getX();
        int playerSnakeY = playerSnake.getY();
        int enemySnakeX = enemySnake.getX();
        int enemySnakeY = enemySnake.getY();

        if(!deleteFlag) {
            // compare x and y positions since this always refers to the top left corner
            if(playerSnakeX == xPos && playerSnakeY == yPos) { 
                game.fruitEaten(pointValue, 1); 
                deleteFlag = true;
                // increase score and send flag for fruit to be deleted
            } else if(enemySnakeX == xPos && enemySnakeY == yPos) {
                game.fruitEaten(pointValue, 2); 
                deleteFlag = true;
            }
        }
    }
 
    public void update() {
        // fruit does not move, only listens for collisions with the snake
        checkCollision(); 
    }

    public void draw(Graphics g) {
        // only draw if not marked for deletion
        if(!deleteFlag) g.drawImage(img, xPos, yPos, FRUIT_RADIUS, FRUIT_RADIUS, null);
    }

    // getters and setters

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }
}