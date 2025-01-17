package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gameobjects.Orientation;

public class KeyInput implements KeyListener {

    private GameManager game;

    public KeyInput(GameManager game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int pressedKey = e.getKeyCode();

        switch(pressedKey) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                game.getPlayerSnake().setDirection(Orientation.UP);
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                game.getPlayerSnake().setDirection(Orientation.LEFT);
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                game.getPlayerSnake().setDirection(Orientation.DOWN);
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                game.getPlayerSnake().setDirection(Orientation.RIGHT);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        return;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }
}
