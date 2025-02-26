package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import main.GameManager;
import main.Utilities.Methods;

import static main.Utilities.SCALE;
import static main.Utilities.Constants.ButtonConstants.OPTIONS_MENU_TEXT_OFFSET;

public class MenuButton {
    // superclass for all buttons

    // attributes to be inherited
    protected boolean highlighted = false; // mouse hover highlight
    protected int xPos, yPos, width, height;

    protected Color fontColor, bgColor, highlightColor; // changes depending on the colour mode
    protected GameManager game;
    protected Rectangle bounds; // can check whether the mouse is inside
    protected String name;

    public MenuButton(int xPos, int yPos, int width, int height, GameManager game, String name) {
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
        g.drawRoundRect(xPos - 1, yPos - 1, width + 1, height + 1, 10 * SCALE, 10 * SCALE);

        // fill button
        if(highlighted) {
            g.setColor(highlightColor);
        } else {
            g.setColor(bgColor);
        }
        
        g.fillRoundRect(xPos, yPos, width, height, 10 * SCALE, 10 * SCALE);

        // draw name
        g.setColor(fontColor);
        g.drawString(name, Methods.GetCentralisedTextX(name, g), yPos + OPTIONS_MENU_TEXT_OFFSET);
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
