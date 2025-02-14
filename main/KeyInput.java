package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

    private GameManager game;

    public KeyInput(GameManager game) {
        this.game = game; // gamePanel needs access to the objects inside the gameManager
    }

    @Override
    public void keyPressed(KeyEvent e) {
        game.keyPressed(e);
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
