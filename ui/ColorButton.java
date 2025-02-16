package ui;

import java.awt.Color;
import java.awt.Graphics;

import main.ColorMode;
import main.GameManager;
import main.Utilities.Methods;

import static main.Utilities.Constants.ButtonConstants.OPTIONS_MENU_BUTTON_RADIUS;
import static main.Utilities.Constants.ButtonConstants.OPTION_BUTTON_FONT;

public class ColorButton extends MenuButton {

    public ColorButton(int xPos, int yPos, int width, int height, GameManager game, String name) {
        super(xPos, yPos, width, height, game, name);

        // use different colour scheme
        fontColor = Color.white;
        bgColor = Color.black;
    }

    @Override
    public void draw(Graphics g) {
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

    public void changeColorMode() {
        // switch the colour mode
        switch(game.getColorMode()) {
            case LIGHT:
                game.changeColorMode(ColorMode.DARK);
                fontColor = Color.white;
                bgColor = Color.black;
                name = "Dark";
                break;
            case DARK:
                game.changeColorMode(ColorMode.LIGHT);
                fontColor = Color.black;
                bgColor = Color.white;
                name = "Light";
        }
    }
}
