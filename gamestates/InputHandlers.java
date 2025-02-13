package gamestates;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface InputHandlers {
    // contains methods that all the gamestates will require
    public void mouseClicked(MouseEvent e);
    public void mousePressed(MouseEvent e);
    public void mouseReleased(MouseEvent e);
    public void mouseMoved(MouseEvent e);
    public void keyPressed(KeyEvent e);
    public void keyReleased(KeyEvent e);
}
