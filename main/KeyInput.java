package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gameobjects.Orientation;
import gameobjects.Snake;

public class KeyInput implements KeyListener {

    private GameManager game;

    public KeyInput(GameManager game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int pressedKey = e.getKeyCode();
        Snake snake = game.getPlayerSnake();

        switch(pressedKey) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                if(snake.getDirection() != Orientation.DOWN) snake.setDirection(Orientation.UP);
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                if(snake.getDirection() != Orientation.RIGHT) snake.setDirection(Orientation.LEFT);
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                if(snake.getDirection() != Orientation.UP) snake.setDirection(Orientation.DOWN);
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                if(snake.getDirection() != Orientation.LEFT) snake.setDirection(Orientation.RIGHT);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int pressedKey = e.getKeyCode();

        switch(pressedKey) {
            default:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }
}
