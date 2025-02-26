package gameobject.snake;

import java.awt.Color;
import java.awt.Graphics;

import gamestate.VersusGame;
import main.GridObject;

import static main.Utilities.Constants.SnakeConstants.*;

public class EnemyBody extends SnakeBody {

    private EnemyHead enemyHead;

    public EnemyBody(int xPos, int yPos, SnakeBody nextBody, VersusGame versusGame) {
        super(xPos, yPos, nextBody, versusGame);

        // change snake colour to red
        snakeColor = ENEMY_SNAKE_DEFAULT_COLOR;
        enemyHead = versusGame.getEnemyHead();
    }

    public void draw(Graphics g) {
        if(spawned && !enemyHead.isCollided()) { 
            // only draw if the body is spawned in
            // draw outline
            g.setColor(Color.black);
            g.fillRect(xPos - 2, yPos - 2, SNAKE_WIDTH, SNAKE_HEIGHT); 

            // fill with red a slightly smaller red square 
            g.setColor(snakeColor);
            g.fillRect(xPos, yPos, SNAKE_WIDTH - 4, SNAKE_HEIGHT - 4); 
        }
    }

    @Override
    public void update() {
        // despawn if enemy head is stunned
        if(enemyHead.isCollided()) {
            spawned = false;
        }

        // update position on grid
        updatePosition();

        if(spawned) {
            previousXPos = xPos;
            previousYPos = yPos;
    
            gameState.setGrid(xPos, yPos, GridObject.EMPTY);
            move(); // 'move' snake body to the position of the body infront
            gameState.setGrid(xPos, yPos, GridObject.ENEMY_BODY);
        }
    }

}
