package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gameobjects.Orientation;
import gameobjects.Snake;

public class KeyInput implements KeyListener {

    private GameManager game;

    public KeyInput(GameManager game) {
        this.game = game; // gamePanel needs access to the objects inside the gameManager
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int pressedKey = e.getKeyCode();
        Snake snake = game.getPlayerSnake();

        switch(pressedKey) { // enables more keybinds to be added easily
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                if(snake.getDirection() != Orientation.DOWN) snake.setDirection(Orientation.UP); // prevents 180 degree turns
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

    // good practice to override these methods even if they are not used
    @Override
    public void keyReleased(KeyEvent e) {
        return;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }
}
