package ui;

import java.awt.Color;
import java.awt.Graphics;

import main.GameManager;
import main.Utilities.Methods;

import static main.Utilities.Constants.ButtonConstants.CIRCLE_BUTTON_RADIUS;
import static main.Utilities.Constants.ButtonConstants.OPTION_BUTTON_FONT;

public class VolumeButton extends MenuButton {

    public VolumeButton(int xPos, int yPos, int width, int height, GameManager game) {
        super(xPos, yPos, width, height, game, "");
        setColor();
        bgColor = Color.orange;
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
        g.fillOval(xPos, yPos, CIRCLE_BUTTON_RADIUS, CIRCLE_BUTTON_RADIUS);

        // change font size, draw
        g.setFont(OPTION_BUTTON_FONT);
        g.setColor(fontColor);
        g.drawString(name, Methods.GetCentralisedButtonTextX(name, g, xPos, width), yPos + 45);
    }

    private void setColor() {
        // change colour depending on mode
        switch(game.getMode()) {
            case 0:
                bgColor = Color.green;
                name = "L";
                break;
            case 1:
                bgColor = Color.orange;
                name = "M";
                break;
            case 2:
                bgColor = Color.red;
                name = "H";
                break;
        }
    }

    public void increaseMode() {
        game.increaseMode();
    }
}
