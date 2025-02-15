package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.GameManager;

public class MouseInput implements MouseListener, MouseMotionListener{

    private GameManager game;

    public MouseInput(GameManager game) {
        this.game = game;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        game.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        game.mouseReleased(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        game.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        game.mouseMoved(e);
    }

    // unused

    @Override
    public void mouseEntered(MouseEvent e) {
        return;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        return;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        return;
    }    
}
