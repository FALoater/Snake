package gamestate;

import static main.Utilities.Constants.ButtonConstants.COLOR_BUTTON_HEIGHT;
import static main.Utilities.Constants.ButtonConstants.OPTIONS_BACK_BUTTON_WIDTH;
import static main.Utilities.Constants.ButtonConstants.OPTIONS_BACK_BUTTON_X;
import static main.Utilities.Constants.ButtonConstants.OPTIONS_MENU_TEXT_OFFSET;
import static main.Utilities.Constants.ButtonConstants.SOUND_BUTTON_HEIGHT;
import static main.Utilities.Constants.ButtonConstants.VOLUME_BUTTON_HEIGHT;
import static main.Utilities.Constants.WindowConstants.WINDOW_HEIGHT;
import static main.Utilities.Constants.WindowConstants.WINDOW_WIDTH;
import static main.Utilities.Constants.ButtonConstants.OPTIONS_LABEL_X;
import static main.Utilities.Constants.ButtonConstants.OPTIONS_MENU_BUTTON_HEIGHT;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import main.GameManager;
import main.Utilities.Methods;
import ui.SettingsButton;

public class PauseMenu extends OptionsMenu {

    private SettingsButton backToGame;

    public PauseMenu(GameManager game) {
        super(game);
        backToGame = new SettingsButton(OPTIONS_BACK_BUTTON_X, 50, OPTIONS_BACK_BUTTON_WIDTH, OPTIONS_MENU_BUTTON_HEIGHT, game, "Back to Game");
        title = "Game paused";
    }

    @Override
    public void draw(Graphics g) {
        // draw transparent background
        g.setColor(new Color(0, 0, 0, 50));
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        // draw title
        g.setColor(Color.decode(Methods.GetCurrentTextColor(game)));
        g.drawString(title, Methods.GetCentralisedTextX(title, g), 90);

        // draw labels
        g.drawString("Sounds", OPTIONS_LABEL_X, SOUND_BUTTON_HEIGHT + OPTIONS_MENU_TEXT_OFFSET);
        g.drawString("Volume", OPTIONS_LABEL_X, VOLUME_BUTTON_HEIGHT + OPTIONS_MENU_TEXT_OFFSET);
        g.drawString("Colour", OPTIONS_LABEL_X, COLOR_BUTTON_HEIGHT + OPTIONS_MENU_TEXT_OFFSET);

        // draw buttons
        sound.draw(g);
        volume.draw(g);
        color.draw(g);
        backToMenu.draw(g);
        backToGame.draw(g);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // keep colour if pressed but not released
        if(backToMenu.isMouseIn(e)) {
            backToMenu.setHighlighted(true);
        } else if(backToGame.isMouseIn(e)) {
            backToGame.setHighlighted(true);
        } else {
            resetHighlight();
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
        } else if(backToGame.isMouseIn(e)) {
            game.changeGameState();
        } else {
            resetHighlight();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(backToMenu.isMouseIn(e)) {
            backToMenu.setHighlighted(true);
        } else if(backToGame.isMouseIn(e)) {
            backToGame.setHighlighted(true);
        } else {
            resetHighlight();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(backToMenu.isMouseIn(e)) {
            backToMenu.setHighlighted(true);
        } else if(backToGame.isMouseIn(e)) {
            backToGame.setHighlighted(true);
        } else {
            resetHighlight();
        }
    }

    private void resetHighlight() {
        backToMenu.setHighlighted(false);
        backToGame.setHighlighted(false);
    }

}
