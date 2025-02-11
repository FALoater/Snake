package gameobjects;

import static main.Utilities.Constants.SnakeConstants.ENEMY_SNAKE_DEFAULT_COLOR;

import main.GameManager;

public class EnemySnakeBody extends SnakeBody {

    public EnemySnakeBody(int xPos, int yPos, int index, SnakeBody nextBody, GameManager game) {
        super(xPos, yPos, index, nextBody, game);
        snakeColor = ENEMY_SNAKE_DEFAULT_COLOR;
    }

    @Override
    protected void checkCollisions() {
        // check if position of tile is currently occupied
        for(SnakeBody body : game.getEnemyBody()) {
            if(body.getX() == xPos && body.getY() == yPos) {
                if(body.getIndex() != index) {
                    // no snake body can occupy 2 squares at the same time so end early
                    return;
                } else {
                    // spawn
                    spawned = true;
                    break;
                }
            }
        }
    }
}
