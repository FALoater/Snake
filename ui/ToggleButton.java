package ui;

import main.GameManager;
import main.Utilities.Methods;

import static main.Utilities.Constants.ButtonConstants.OPTION_BUTTON_FONT;

import java.awt.Color;
import java.awt.Graphics;

public class ToggleButton extends MenuButton{

    public ToggleButton(int xPos, int yPos, int width, int height, GameManager game) {
        super(xPos, yPos, width, height, game, "");
        bgColor = Color.green;
        fontColor = Color.black;
        setColor();
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
        // change colour depending on soundOn
        if(game.isSoundOn()) {
            bgColor = Color.green;
            name = "On";
        } else {
            bgColor = Color.red;
            name = "Off";
        }
    }

    public void toggleSound() {
        game.toggleSound();
    }
}
