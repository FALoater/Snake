package ui;

import java.awt.image.BufferedImage;

import main.GameManager;

public abstract class Button {
    // cannot be instantiated directly, superclass for all buttons

    // attributes to be inherited
    protected boolean pressed;
    protected int xPos, yPos, width, height;

    protected BufferedImage img; // some buttons will have images;
    protected GameManager game;

    public Button(int xPos, int yPos, int width, int height, GameManager game) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.game = game;
    }

}
