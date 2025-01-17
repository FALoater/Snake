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

        if(pressedKey == KeyEvent.VK_W) {
            game.getPlayerSnake().setDirection(Orientation.UP);
        } else if(pressedKey == KeyEvent.VK_A) {
            game.getPlayerSnake().setDirection(Orientation.LEFT);
        } else if(pressedKey == KeyEvent.VK_S) {
            game.getPlayerSnake().setDirection(Orientation.DOWN);
        } else if(pressedKey == KeyEvent.VK_D) {
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
