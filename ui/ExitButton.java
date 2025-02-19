package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.GameManager;
import main.Utilities.Methods;

import static main.Utilities.Constants.ButtonConstants.EXIT_BUTTON_IMG;

public class ExitButton extends MenuButton {

    private BufferedImage img;

    public ExitButton(int xPos, int yPos, int width, int height, GameManager game, String name) {
        super(xPos, yPos, width, height, game, name);
        img = Methods.LoadImage(EXIT_BUTTON_IMG);
    }

    public void drawExitButton(Graphics g) {
        g.drawImage(img, xPos, yPos, width, height, null);
    }
}
