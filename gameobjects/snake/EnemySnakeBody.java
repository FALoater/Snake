package gameobjects.snake;

import static main.Utilities.Constants.SnakeConstants.ENEMY_SNAKE_DEFAULT_COLOR;

import main.GameManager;

public class EnemySnakeBody extends SnakeBody {

    public EnemySnakeBody(int xPos, int yPos, int index, SnakeBody nextBody, GameManager game) {
        super(xPos, yPos, index, nextBody, game);
        snakeColor = ENEMY_SNAKE_DEFAULT_COLOR;
    }
}
