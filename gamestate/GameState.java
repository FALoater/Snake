package gamestate;

import java.awt.Graphics;

import main.GameManager;

public class GameState{
    // superclass for all gamestates
    protected GameManager game;
    protected String title;

    public GameState(GameManager game) {
        // can be accessed in subclasses
        this.game = game;
    }

    // methods to be overriden in subclasses
    public void update() {}   
    public void draw(Graphics g) {} 
    public GameManager getGameManager() {
        return game;
    }
}
