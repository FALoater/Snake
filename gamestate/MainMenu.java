package gamestate;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import main.GameManager;

public class MainMenu extends GameState implements InputHandlers {

    public MainMenu(GameManager game) {
        super(game);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        g.drawRect(100, 100, 100, 100);
    }

    @Override
    public void mouseClicked(MouseEvent e) {};

    @Override
    public void mousePressed(MouseEvent e) {};
    
    @Override
    public void mouseReleased(MouseEvent e) {};
    
    @Override
    public void mouseMoved(MouseEvent e) {};
    
    @Override
    public void keyPressed(KeyEvent e) {};
    
    @Override
    public void keyReleased(KeyEvent e) {};
}