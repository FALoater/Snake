package main;

import java.awt.Graphics;

public class GameManager{

    private GameWindow gameWindow;
    private GamePanel gamePanel;

    public GameManager() {
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
    }

    public void render(Graphics g) {
    // draw the graphics for every game state

    }

    public void update() {
    // update the game objects every frame
    }
}
