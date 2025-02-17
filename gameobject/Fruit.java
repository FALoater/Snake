package gameobject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameobject.snake.EnemyHead;
import gameobject.snake.SnakeHead;
import gamestate.GameStateType;
import gamestate.PlayingGameState;

import static main.Utilities.Constants.AudioConstants.EAT;
import static main.Utilities.Constants.FruitConstants.*;
import static main.Utilities.Methods;

public class Fruit {
    private boolean deleteFlag = false; // signals whether the fruit has been eaten
    private int xPos, yPos, pointValue;

    private BufferedImage img;
    private FruitType type;
    private PlayingGameState gameState;

    public Fruit(int xPos, int yPos, FruitType type, PlayingGameState gameState) {
        this.gameState = gameState;
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
                img = Methods.LoadImage(APPLE_IMG);
                break;
            case ORANGE:
                img = Methods.LoadImage(ORANGE_IMG);
                break;
        }
    }

    public void checkPlayerCollision() {
        SnakeHead playerSnake = gameState.getPlayerHead();

        int playerSnakeX = playerSnake.getX();
        int playerSnakeY = playerSnake.getY();

        if(!deleteFlag && playerSnakeX == xPos && playerSnakeY == yPos) {
            gameState.getGame().playSoundEffect(EAT);
            gameState.fruitEaten(pointValue, 1); 
            deleteFlag = true;
        }
    }

    public void checkEnemyCollision() {
        EnemyHead enemySnake = gameState.getEnemyHead();
        int enemySnakeX = enemySnake.getX();
        int enemySnakeY = enemySnake.getY();

        // increase score and send flag for fruit to be deleted
        if(enemySnakeX == xPos && enemySnakeY == yPos) {
            gameState.fruitEaten(pointValue, 2); 
            deleteFlag = true;
        }
    }
 
    public void update() {
        // fruit does not move, only listens for collisions with the snake
        checkPlayerCollision();
        if(gameState.getGameManager().getGameState() == GameStateType.VERSUS_GAME) checkEnemyCollision();
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