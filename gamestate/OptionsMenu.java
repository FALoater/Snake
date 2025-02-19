package gamestate;

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

import static main.Utilities.SCALE;
import static main.Utilities.Constants.ButtonConstants.*;

public class OptionsMenu extends GameState implements InputHandlers{

    protected ColorButton color;
    protected SettingsButton backToMenu;
    protected ToggleButton sound;
    protected VolumeButton volume;

    public OptionsMenu(GameManager game) {
        super(game);

        // init buttons
        sound = new ToggleButton(OPTIONS_BUTTON_X, SOUND_BUTTON_HEIGHT, CIRCLE_BUTTON_RADIUS, CIRCLE_BUTTON_RADIUS, game);
        volume = new VolumeButton(OPTIONS_BUTTON_X, VOLUME_BUTTON_HEIGHT, CIRCLE_BUTTON_RADIUS, CIRCLE_BUTTON_RADIUS, game);
        color = new ColorButton(OPTIONS_BUTTON_X, COLOR_BUTTON_HEIGHT, CIRCLE_BUTTON_RADIUS, CIRCLE_BUTTON_RADIUS, game);
        backToMenu = new SettingsButton(OPTIONS_BACK_BUTTON_X, (int)(312.5 * SCALE), OPTIONS_BACK_BUTTON_WIDTH, OPTIONS_MENU_BUTTON_HEIGHT, game, "Back to Menu");

        title = "Settings";
    }

    public void draw(Graphics g) {
        // draw title
        g.setColor(Color.decode(Methods.GetCurrentTextColor(game)));
        g.drawString(title, Methods.GetCentralisedTextX(title, g), 45 * SCALE);

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
            color.changeColor();
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