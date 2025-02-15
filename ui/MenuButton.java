package ui;

import static main.Utilities.Constants.ButtonConstants.EXIT_BUTTON_IMG;

import java.awt.Graphics;

import main.GameManager;
import main.Utilities.Methods;

public class MenuButton extends Button {

    public MenuButton(int xPos, int yPos, int width, int height, GameManager game, String name) {
        super(xPos, yPos, width, height, game, name);
        img = Methods.LoadImage(EXIT_BUTTON_IMG);
    }

    public void drawExitButton(Graphics g) {
        g.drawImage(img, xPos, yPos, null);
    }
    
}
