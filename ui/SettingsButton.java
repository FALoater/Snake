package ui;

import java.awt.Color;
import java.awt.Graphics;

import main.GameManager;
import main.Utilities.Methods;

import static main.Utilities.Constants.WindowConstants.DEFAULT_FONT;

public class SettingsButton extends MenuButton {

    public SettingsButton(int xPos, int yPos, int width, int height, GameManager game, String name) {
        super(xPos, yPos, width, height, game, name);
    }
    
    @Override
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
        g.setFont(DEFAULT_FONT);
        g.setColor(fontColor);
        g.drawString(name, Methods.GetCentralisedButtonTextX(name, g, xPos, width), yPos + 45);
    }
}
