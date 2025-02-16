package ui;

import java.awt.Color;
import java.awt.Graphics;

import main.GameManager;
import main.Utilities.Methods;

import static main.Utilities.Constants.ButtonConstants.OPTION_BUTTON_FONT;

public class ColorButton extends MenuButton {

    public ColorButton(int xPos, int yPos, int width, int height, GameManager game) {
        super(xPos, yPos, width, height, game, "Dark");
        // use different colour scheme
        fontColor = Color.white;
        bgColor = Color.black;
    }

    @Override
    public void draw(Graphics g) {
        setColor();
        // draw border
        g.setColor(Color.black);
        g.drawOval(xPos - 1, yPos - 1, width + 2, height + 2);

        // fill colour
        g.setColor(bgColor);
        g.fillOval(xPos, yPos, width, height);

        // change font size, draw
        g.setFont(OPTION_BUTTON_FONT);
        g.setColor(fontColor);
        g.drawString(name, Methods.GetCentralisedButtonTextX(name, g, xPos, width), yPos + 45);
    }

    private void setColor() {
        // switch the colour mode
        switch(game.getColorMode()) {
            case DARK:
                fontColor = Color.white;
                bgColor = Color.black;
                name = "Dark";                
                break;
            case LIGHT:
                name = "Light";
                fontColor = Color.black;
                bgColor = Color.white;
                break;
        }
    }

    public void changeColor() {
        game.changeColorMode();
    }
}
