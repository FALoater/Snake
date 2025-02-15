package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.GameManager;
import main.Utilities.Methods;

public abstract class Button {
    // cannot be instantiated directly, superclass for all buttons

    // attributes to be inherited
    protected boolean highlighted;
    protected int xPos, yPos, width, height;

    protected Color fontColor, bgColor, highlightColor;
    protected String name;

    protected BufferedImage img; // some buttons will have images
    protected Rectangle bounds;
    protected GameManager game;

    public Button(int xPos, int yPos, int width, int height, GameManager game, String name) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.game = game;
        this.name = name;

        bounds = new Rectangle(xPos, yPos, width, height);
    }

    public void draw(Graphics g) {
        Methods.GetButtonColor(game, this);

        // draw border
        g.setColor(Color.black);   
        g.drawRoundRect(xPos - 1, yPos - 1, width + 1, height + 1, 20, 20);

        // fill button
        if(highlighted) {
            g.setColor(highlightColor);
        } else {
            g.setColor(bgColor);
        }
        
        g.fillRoundRect(xPos, yPos, width, height, 20, 20);

        // draw name
        g.setColor(fontColor);
        g.drawString(name, Methods.GetCentralisedTextX(name, g), yPos + 50);
    }

    public boolean isMouseIn(MouseEvent e) {
        // check whether the mouse is inside the button
        return bounds.contains(e.getX(), e.getY());
    }

    // getters and setters

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public void setHighlightColor(Color highlightColor) {
        this.highlightColor = highlightColor;
    }
}
