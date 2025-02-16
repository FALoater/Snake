package gamestate;

import static main.Utilities.Constants.ButtonConstants.COLOR_BUTTON_HEIGHT;
import static main.Utilities.Constants.ButtonConstants.OPTIONS_BUTTON_X;
import static main.Utilities.Constants.ButtonConstants.SOUND_BUTTON_HEIGHT;
import static main.Utilities.Constants.ButtonConstants.VOLUME_BUTTON_HEIGHT;
import static main.Utilities.Constants.ButtonConstants.OPTIONS_LABEL_X;
import static main.Utilities.Constants.ButtonConstants.OPTIONS_MENU_BUTTON_HEIGHT;
import static main.Utilities.Constants.ButtonConstants.OPTIONS_MENU_BUTTON_RADIUS;
import static main.Utilities.Constants.ButtonConstants.OPTIONS_MENU_TEXT_OFFSET;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import input.InputHandlers;
import main.GameManager;
import main.Utilities.Methods;
import ui.ColorButton;
import ui.SettingsButton;
import ui.ToggleButton;
import ui.VolumeButton;

public class OptionsMenu extends GameState implements InputHandlers{

    private ColorButton color;
    private SettingsButton backToMenu;
    private ToggleButton sound;
    private VolumeButton volume;

    public OptionsMenu(GameManager game) {
        super(game);

        // init buttons
        sound = new ToggleButton(OPTIONS_BUTTON_X, SOUND_BUTTON_HEIGHT, OPTIONS_MENU_BUTTON_RADIUS, OPTIONS_MENU_BUTTON_RADIUS, game, "On");
        volume = new VolumeButton(OPTIONS_BUTTON_X, VOLUME_BUTTON_HEIGHT, OPTIONS_MENU_BUTTON_RADIUS, OPTIONS_MENU_BUTTON_RADIUS, game, "M");
        color = new ColorButton(OPTIONS_BUTTON_X, COLOR_BUTTON_HEIGHT, OPTIONS_MENU_BUTTON_RADIUS, OPTIONS_MENU_BUTTON_RADIUS, game, "Dark");
        backToMenu = new SettingsButton(50, 625, 300, OPTIONS_MENU_BUTTON_HEIGHT, game, "Back to Menu");
    }

    public void draw(Graphics g) {
        // draw title
        g.setColor(Color.decode(Methods.GetCurrentTextColor(game)));
        g.drawString("Settings", Methods.GetCentralisedTextX("Settings", g), 90);

        // draw labels
        g.drawString("Sounds", OPTIONS_LABEL_X, SOUND_BUTTON_HEIGHT + OPTIONS_MENU_TEXT_OFFSET);
        g.drawString("Volume", OPTIONS_LABEL_X, VOLUME_BUTTON_HEIGHT + OPTIONS_MENU_TEXT_OFFSET);
        g.drawString("Colour", OPTIONS_LABEL_X, COLOR_BUTTON_HEIGHT + OPTIONS_MENU_TEXT_OFFSET);

        // draw buttons
        sound.draw(g);
        volume.draw(g);
        color.draw(g);
        backToMenu.draw(g);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // keep colour if pressed but not released
        if(backToMenu.isMouseIn(e)) {
            backToMenu.setHighlighted(true);
        } else {
            backToMenu.setHighlighted(false);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(backToMenu.isMouseIn(e)) {
            game.changeGameState(GameStateType.MAIN_MENU);
        } else if(color.isMouseIn(e)) {
            color.changeColorMode();
        } else if(volume.isMouseIn(e)) {
            volume.increaseMode();
        } else if(sound.isMouseIn(e)) {
            sound.toggleSound();
        } else {
            backToMenu.setHighlighted(false);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(backToMenu.isMouseIn(e)) {
            backToMenu.setHighlighted(true);
        } else {
            backToMenu.setHighlighted(false);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(backToMenu.isMouseIn(e)) {
            backToMenu.setHighlighted(true);
        } else {
            backToMenu.setHighlighted(false);
        }
    }

    // unused

    @Override
    public void keyPressed(KeyEvent e) {
        return;
    }   
}