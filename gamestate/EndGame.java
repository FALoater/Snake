package gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import input.InputHandlers;
import main.GameManager;
import main.Utilities.Methods;
import ui.EndGameButton;

import static main.Utilities.Constants.ButtonConstants.END_GAME_BUTTON_RADIUS;
import static main.Utilities.SCALE;
import static main.Utilities.Constants.ButtonConstants.END_GAME_BUTTON_HEIGHT;

public class EndGame extends GameState implements InputHandlers {

    private Color titleColor = Color.red;
    private EndGameButton playAgain, quit, backToMenu;
    private String currentScore, highscore;

    public EndGame(GameManager game) {
        super(game);

        // init buttons
        playAgain = new EndGameButton(100 * SCALE, END_GAME_BUTTON_HEIGHT, END_GAME_BUTTON_RADIUS, END_GAME_BUTTON_RADIUS, game, "Play again");
        quit = new EndGameButton((int)(187.5 * SCALE), END_GAME_BUTTON_HEIGHT, END_GAME_BUTTON_RADIUS, END_GAME_BUTTON_RADIUS, game, "Quit");
        backToMenu = new EndGameButton(275 * SCALE, END_GAME_BUTTON_HEIGHT, END_GAME_BUTTON_RADIUS, END_GAME_BUTTON_RADIUS, game, "Back to menu");

        title = "GAME OVER!";
    }

    @Override
    public void draw(Graphics g) {
        currentScore = "Your score: " + game.getPlayerScore();
        highscore = "Highscore: " + game.getPlayerHighScore();

        if(game.getLastGameState() == GameStateType.VERSUS_GAME) {
            if(game.getPlayerScore() > game.getEnemyScore()) {
                title = "YOU WON!";
                titleColor = Color.green;
            } else if(game.getPlayerScore() < game.getEnemyScore()) {
                title = "YOU LOST!";
                titleColor = Color.red;
            } else {
                title = "TIE!";
                titleColor = Color.orange;
            }
        } else {
            title = "GAME OVER!";
        }

        // draw title
        g.setColor(titleColor);
        g.setFont(new Font("Arial", Font.BOLD, 35 * SCALE));
        g.drawString(title, Methods.GetCentralisedTextX(title, g), 100 * SCALE);

        // draw score display
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.PLAIN, 20 * SCALE));
        g.drawString(currentScore, Methods.GetCentralisedTextX(currentScore, g), 150 * SCALE);
        g.drawString(highscore, Methods.GetCentralisedTextX(highscore, g), 175 * SCALE);

        // draw buttons
        playAgain.draw(g);
        quit.draw(g);
        backToMenu.draw(g);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(playAgain.isMouseIn(e)) {
            playAgain.setHighlighted(true);
        } else if(quit.isMouseIn(e)) {
            quit.setHighlighted(true);
        } else if(backToMenu.isMouseIn(e)) {
            backToMenu.setHighlighted(true);
        } else {
            resetButtons();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(playAgain.isMouseIn(e)) {
            game.changeGameState();
        } else if(quit.isMouseIn(e)) {
            System.exit(0);
        } else if(backToMenu.isMouseIn(e)) {
            game.changeGameState(GameStateType.MAIN_MENU);
        } else {
            resetButtons();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(playAgain.isMouseIn(e)) {
            playAgain.setHighlighted(true);
        } else if(quit.isMouseIn(e)) {
            quit.setHighlighted(true);
        } else if(backToMenu.isMouseIn(e)) {
            backToMenu.setHighlighted(true);
        } else {
            resetButtons();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(playAgain.isMouseIn(e)) {
            playAgain.setHighlighted(true);
        } else if(quit.isMouseIn(e)) {
            quit.setHighlighted(true);
        } else if(backToMenu.isMouseIn(e)) {
            backToMenu.setHighlighted(true);
        } else {
            resetButtons();
        }
    }

    private void resetButtons() {
        playAgain.setHighlighted(false);
        quit.setHighlighted(false);
        backToMenu.setHighlighted(false);
    }

    // unused

    @Override
    public void keyPressed(KeyEvent e) {
        return;
    }
    
}
