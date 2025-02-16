package ui;

import java.awt.Color;
import java.awt.Graphics;

import main.GameManager;
import main.Utilities.Methods;

import static main.Utilities.Constants.ButtonConstants.CIRCLE_BUTTON_RADIUS;
import static main.Utilities.Constants.ButtonConstants.OPTION_BUTTON_FONT;

public class PauseButton extends MenuButton {

    public PauseButton(int xPos, int yPos, int width, int height, GameManager game, String name) {
        super(xPos, yPos, width, height, game, name);
    }
    
    @Override
    public void draw(Graphics g) {
        Methods.GetButtonColor(game, this);
        // draw border
        g.setColor(Color.black);
        g.drawOval(xPos - 1, yPos - 1, width + 2, height + 2);

        // fill colour
        g.setColor(bgColor);
        g.fillOval(xPos, yPos, CIRCLE_BUTTON_RADIUS, CIRCLE_BUTTON_RADIUS);

        // change font size, draw
        g.setFont(OPTION_BUTTON_FONT);
        g.setColor(fontColor);
        g.drawString(name, Methods.GetCentralisedButtonTextX(name, g, xPos, width), yPos + 45);
    }
}
