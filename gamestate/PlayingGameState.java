package gamestate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Random;

import gameobject.Direction;
import gameobject.Fruit;
import gameobject.FruitType;
import gameobject.snake.EnemyBody;
import gameobject.snake.EnemyHead;
import gameobject.snake.SnakeBody;
import gameobject.snake.SnakeHead;
import input.InputHandlers;
import main.GameManager;
import main.GridObject;
import main.Utilities.Methods;
import ui.PauseButton;

import static main.Utilities.Constants.ButtonConstants.CIRCLE_BUTTON_RADIUS;
import static main.Utilities.Constants.WindowConstants.TILE_SIZE;
import static main.Utilities.Constants.WindowConstants.WINDOW_HEIGHT;
import static main.Utilities.Constants.WindowConstants.WINDOW_WIDTH;

public abstract class PlayingGameState extends GameState implements InputHandlers{
    // superclass for classic and versus game

    // game objects
    protected EnemyHead enemyHead;
    protected GridObject[][] grid;
    protected LinkedList<Fruit> fruits = new LinkedList<Fruit>();
    protected LinkedList<SnakeBody> playerBody = new LinkedList<SnakeBody>();
    protected Random rand = new Random();
    protected SnakeHead playerHead;

    // ui
    protected PauseButton pause;

    public PlayingGameState(GameManager game) {
        super(game);
        pause = new PauseButton(0, 0, CIRCLE_BUTTON_RADIUS, CIRCLE_BUTTON_RADIUS, game, "Pause");
    }

    protected void drawButtonAndGrid(Graphics g) {
        // draw lines to give a grid effect
        g.setColor(Color.decode(Methods.GetCurrentTextColor(game)));
        // horizontal lines
        for(int y=TILE_SIZE*2;y<WINDOW_HEIGHT;y+=TILE_SIZE) {
            g.drawLine(0, y - 2, WINDOW_WIDTH, y - 2);
        }

        // vertical lines
        for(int x=0;x<WINDOW_WIDTH;x+=TILE_SIZE) {
            g.drawLine(x - 2, TILE_SIZE * 2, x - 2, WINDOW_HEIGHT);
        }

        // draw button
        pause.draw(g);
    }

    protected void generateFruit() {
		// generate random position for fruit
		int[] newPosition = Methods.GenerateRandomValidPosition(grid);
		int xPos = newPosition[0];
		int yPos = newPosition[1];

		// update position in grid
		grid[yPos][xPos] = GridObject.FRUIT;

		// generate random fruit type
		int fruitType = rand.nextInt(6); 

		// make apples spawn more commonly
		if(fruitType <= 3) {
			fruits.add(new Fruit(xPos * TILE_SIZE, yPos * TILE_SIZE, FruitType.APPLE, this));
		} else {
			fruits.add(new Fruit(xPos * TILE_SIZE, yPos * TILE_SIZE, FruitType.ORANGE, this));
		}
	}

    public void fruitEaten(int score, int collisionType) {}
    
    // keyboard inputs
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                if(playerHead.getDirection() != Direction.DOWN) playerHead.setDirection(Direction.UP); // prevents 180 degree turns
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                if(playerHead.getDirection() != Direction.RIGHT) playerHead.setDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                if(playerHead.getDirection() != Direction.UP) playerHead.setDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                if(playerHead.getDirection() != Direction.LEFT) playerHead.setDirection(Direction.RIGHT);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(pause.isMouseIn(e)) {
            pause.setHighlighted(true);
        } else {
            pause.setHighlighted(false);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(pause.isMouseIn(e)) {
            game.changeGameState(GameStateType.PAUSE_MENU);
        } else {
            pause.setHighlighted(false);
        }
    }

    
    @Override
    public void mouseMoved(MouseEvent e) {
        if(pause.isMouseIn(e)) {
            pause.setHighlighted(true);
        } else {
            pause.setHighlighted(false);
        }
	}

    @Override
    public void mouseDragged(MouseEvent e) {
        if(pause.isMouseIn(e)) {
            pause.setHighlighted(true);
        } else {
            pause.setHighlighted(false);
        }
    }

    // getters and setters
    public GameManager getGame() {
        return game;
    }

	public void resetEnemyScore() {
		game.getGamePanel().resetEnemyScore();
	}

	public void resetPlayerScore() {
		game.getGamePanel().resetPlayerScore();
	}

    public SnakeHead getPlayerHead() {
        return playerHead;
    }

    public EnemyHead getEnemyHead() {
        return enemyHead;
    }

    public void resetPlayerBody() {
		// remove all the snake body length
		while(playerBody.size() > 1) {
			SnakeBody currentBody = playerBody.removeLast();
			grid[currentBody.getY() / TILE_SIZE][currentBody.getX() / TILE_SIZE] = GridObject.EMPTY;
		}
	}

	public void setGrid(int xPos, int yPos, GridObject object) {
		// standardise for index positions
		grid[yPos / TILE_SIZE][xPos / TILE_SIZE] = object;
	}

	public GridObject getObjectAtGridPos(int xPos, int yPos) {
		return grid[yPos / TILE_SIZE][xPos / TILE_SIZE];
	}

    public LinkedList<Fruit> getFruits() {
		return fruits;
    }

    public LinkedList<SnakeBody> getPlayerBody() {
        return playerBody;
    }

    public LinkedList<EnemyBody> getEnemyBody() {
        return null;
    }
}