package gameobjects.snake;

import java.awt.Color;
import java.awt.Graphics;

import main.GameManager;
import main.GridObject;

import static main.Utilities.Constants.SnakeConstants.*;

public class EnemyBody extends SnakeBody {

    private EnemyHead enemySnakeHead;

    public EnemyBody(int xPos, int yPos, int index, SnakeBody nextBody, GameManager game) {
        super(xPos, yPos, index, nextBody, game);

        // change snake colour to red
        snakeColor = ENEMY_SNAKE_DEFAULT_COLOR;
        enemySnakeHead = game.getEnemySnakeHead();
    }

    public void draw(Graphics g) {
        if(spawned && !enemySnakeHead.isCollided()) { // only draw if the body is spawned in
            g.setColor(Color.black);
            g.fillRect(xPos - 2, yPos - 2, SNAKE_WIDTH, SNAKE_HEIGHT); // first fill a black square
            g.setColor(snakeColor); // change colour to red
            g.fillRect(xPos, yPos, SNAKE_WIDTH - 4, SNAKE_HEIGHT - 4); // fill with red a slightly smaller square to allow for black border
        }
    }

    @Override
        public void update() {
        if(game.getEnemySnakeHead().isCollided()) {
            spawned = false;
        }

        checkCollisions();

        if(spawned) {
            previousXPos = xPos;
            previousYPos = yPos;
    
            game.setGrid(xPos, yPos, GridObject.EMPTY);
            move(); // 'move' snake body to the position of the body infront
            game.setGrid(xPos, yPos, GridObject.ENEMY_BODY);
        }
    }

}
