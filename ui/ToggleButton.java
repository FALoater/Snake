package ui;

import main.GameManager;
import main.Utilities.Methods;

import static main.Utilities.Constants.ButtonConstants.OPTIONS_MENU_BUTTON_RADIUS;
import static main.Utilities.Constants.ButtonConstants.OPTION_BUTTON_FONT;

import java.awt.Color;
import java.awt.Graphics;

public class ToggleButton extends MenuButton{

    private boolean soundOn = true;

    public ToggleButton(int xPos, int yPos, int width, int height, GameManager game, String name) {
        super(xPos, yPos, width, height, game, name);
        bgColor = Color.green;
        fontColor = Color.black;
    }

    @Override
    public void draw(Graphics g) {
        setColor();
        // draw border
        g.setColor(Color.black);
        g.drawOval(xPos - 1, yPos - 1, width + 2, height + 2);

        // fill colour
        g.setColor(bgColor);
        g.fillOval(xPos, yPos, OPTIONS_MENU_BUTTON_RADIUS, OPTIONS_MENU_BUTTON_RADIUS);

        // change font size, draw
        g.setFont(OPTION_BUTTON_FONT);
        g.setColor(fontColor);
        g.drawString(name, Methods.GetCentralisedButtonTextX(name, g, xPos, width), yPos + 45);
    }

    private void setColor() {
        // change colour depending on soundOn
        if(soundOn) {
            bgColor = Color.green;
            name = "On";
        } else {
            bgColor = Color.red;
            name = "Off";
        }
    }

    public void toggleSound() {
        soundOn = !soundOn;
    }
}
