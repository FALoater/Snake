package gameobjects;

import java.awt.image.BufferedImage;

public class Snake{

    private boolean collided = false;
    private int xPos, yPos;
    
    private BufferedImage img;

    enum Orientation { // easier to use rather than integers to encode direction
        UP,
        RIGHT,
        LEFT,
        DOWN
    }
    
    public Snake(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }
}