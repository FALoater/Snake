package gamestate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import input.InputHandlers;
import main.GameManager;
import main.Utilities.Methods;
import ui.MenuButton;
import ui.ExitButton;

import static main.Utilities.Constants.ButtonConstants.*;

public class MainMenu extends GameState implements InputHandlers{

    private MenuButton classic, versus, settings;
    private ExitButton exit;

    public MainMenu(GameManager game) {
        super(game);

        // create buttons on main menu
        classic = new MenuButton(MENU_X, 300, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT, game, "Classic");
        versus = new MenuButton(MENU_X, 400, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT, game, "vs AI");
        settings = new MenuButton(MENU_X, 500, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT, game, "Settings");
        exit = new ExitButton(75, 505, 64, 64, game, "");
        title = "Snake";
    }

    private void resetButtons() {
        classic.setHighlighted(false);
        versus.setHighlighted(false);
        settings.setHighlighted(false);
    }

    @Override
    public void draw(Graphics g) {
        // draw title
        g.setColor(Color.decode(Methods.GetCurrentTextColor(game)));
        g.drawString(title, Methods.GetCentralisedTextX(title, g), 200);
        
        // draw buttons
        classic.draw(g);
        versus.draw(g);
        settings.draw(g);
        exit.drawExitButton(g);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // keep colour if pressed but not released
        if(classic.isMouseIn(e)) {
            classic.setHighlighted(true);
        } else if(versus.isMouseIn(e)) {
            versus.setHighlighted(true);
        } else if (settings.isMouseIn(e)) {
            settings.setHighlighted(true);
        } else {
            // else reset values
            resetButtons();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // activate button function if released inside a button
        if(classic.isMouseIn(e)) {
            game.changeGameState(GameStateType.CLASSIC_GAME);
        } else if(versus.isMouseIn(e)) {
            game.changeGameState(GameStateType.VERSUS_GAME);
        } else if (settings.isMouseIn(e)) {
            game.changeGameState(GameStateType.OPTIONS_MENU);
        } else if (exit.isMouseIn(e)) {
            // exit the game
            System.exit(0);
        }

        // reset all values 
        resetButtons();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // slightly make darker if mouse hovers over
        if(classic.isMouseIn(e)) {
            classic.setHighlighted(true);
        } else if(versus.isMouseIn(e)) {
            versus.setHighlighted(true);
        } else if (settings.isMouseIn(e)) {
            settings.setHighlighted(true);
        } else {
            // else reset values
            resetButtons();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // if mouse is dragged out of button, stop highlighting
        if(classic.isMouseIn(e)) {
            classic.setHighlighted(true);
        } else if(versus.isMouseIn(e)) {
            versus.setHighlighted(true);
        } else if (settings.isMouseIn(e)) {
            settings.setHighlighted(true);
        } else {
            // else reset values
            resetButtons();
        }
	}

    // unused

    @Override
    public void keyPressed(KeyEvent e) {
        return;
    }

}