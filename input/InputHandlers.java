package input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface InputHandlers {
    // contains input-related methods that all the gamestates will require
    public void mousePressed(MouseEvent e);
    public void mouseReleased(MouseEvent e);
    public void mouseMoved(MouseEvent e);
    public void mouseDragged(MouseEvent e);
    public void keyPressed(KeyEvent e);
}