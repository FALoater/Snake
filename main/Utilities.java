package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import ui.MenuButton;

import java.util.LinkedList;
import java.util.Random;

public class Utilities {
    
    private static Random rand = new Random();

    public static final int SCALE = 2; // scale of the game window, changes proportionally

    public static class Constants {

    // class contains constants for the game
    // gives numbers more meaningful identifiers

        public static class WindowConstants {
            // game window sizes and refresh rates
            public static final int TILE_SIZE = 20 * SCALE;
            public static final int TILES_IN_WIDTH = 22;
            public static final int TILES_IN_HEIGHT = 18;
            public static final int WINDOW_WIDTH = TILES_IN_WIDTH * TILE_SIZE; // 880
            public static final int WINDOW_HEIGHT = TILES_IN_HEIGHT * TILE_SIZE; // 720
            public static final int FPS = 60;
            public static final int UPS = 6; // controls speed of snake

            public static final Color BG_LIGHT_MODE = new Color(230, 255, 255);
            public static final Color BG_DARK_MODE = Color.gray;
            public static final String TEXT_LIGHT_MODE = "#420edf";
            public static final String TEXT_DARK_MODE = "#6ccbc1";

            public static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN,40);
        }

        public static class SnakeConstants {
            // snake sizes, image sources, respawn time and colours
            public static final int SNAKE_WIDTH = WindowConstants.TILE_SIZE;
            public static final int SNAKE_HEIGHT = WindowConstants.TILE_SIZE;

            public static final int PLAYER_START_X = 12 * WindowConstants.TILE_SIZE;
            public static final int PLAYER_START_Y = 9 * WindowConstants.TILE_SIZE;
            public static final int ENEMY_START_X = 10 * WindowConstants.TILE_SIZE;
            public static final int ENEMY_START_Y = 9 * WindowConstants.TILE_SIZE;

            public static final int RESPAWN_TIME = WindowConstants.UPS * 4; // 4 second respawn time
            public static final int DEATH_POINT_DEDUCTION = 10;

            public static final Color PLAYER_SNAKE_DEFAULT_COLOR = Color.green;
            public static final Color ENEMY_SNAKE_DEFAULT_COLOR = Color.red;

            public static final String PLAYER_HEAD_LEFT_IMG = "snake/player/snake_head_left";
            public static final String PLAYER_HEAD_RIGHT_IMG = "snake/player/snake_head_right";
            public static final String PLAYER_HEAD_UP_IMG = "snake/player/snake_head_up";
            public static final String PLAYER_HEAD_DOWN_IMG = "snake/player/snake_head_down";

            public static final String ENEMY_HEAD_LEFT_IMG = "snake/enemy/snake_head_left";
            public static final String ENEMY_HEAD_RIGHT_IMG = "snake/enemy/snake_head_right";
            public static final String ENEMY_HEAD_UP_IMG = "snake/enemy/snake_head_up";
            public static final String ENEMY_HEAD_DOWN_IMG = "snake/enemy/snake_head_down";
        }

        public static class FruitConstants {
            // fruit sizes and image sources
            public static final int FRUIT_RADIUS = 15 * SCALE;
            
            public static final String APPLE_IMG = "fruit/apple";
            public static final String ORANGE_IMG = "fruit/orange";
        }

        public static class ButtonConstants {
            // button sizes, colours and image sources
            public static final int CIRCLE_BUTTON_RADIUS = 75;
            public static final int MAIN_MENU_BUTTON_WIDTH = 300;
            public static final int MAIN_MENU_BUTTON_HEIGHT = 75;
            public static final int MENU_X = (WindowConstants.WINDOW_WIDTH - MAIN_MENU_BUTTON_WIDTH) / 2;
            public static final String EXIT_BUTTON_IMG = "button/exit";

            public static final int OPTIONS_MENU_BUTTON_HEIGHT = 60;
            public static final int OPTIONS_MENU_TEXT_OFFSET = 50;
            public static final int OPTIONS_LABEL_X = 250;
            public static final int OPTIONS_BUTTON_X = 500;
            public static final int OPTIONS_BACK_BUTTON_X = 0;
            public static final int OPTIONS_BACK_BUTTON_WIDTH = 300;
            public static final Font OPTION_BUTTON_FONT = new Font("Arial", Font.PLAIN,20);

            public static final int END_GAME_BUTTON_HEIGHT = 500;
            public static final int END_GAME_BUTTON_RADIUS = 125;

            public static final int COLOR_BUTTON_HEIGHT = 450;
            public static final int SOUND_BUTTON_HEIGHT = 150;
            public static final int VOLUME_BUTTON_HEIGHT = 300;

            public static final Color LIGHT_TEXT_COLOR = new Color(15,158,213);
            public static final Color LIGHT_BG_COLOR = new Color(232,232,232);
            public static final Color LIGHT_HIGHLIGHT_COLOR = new Color(217, 217, 217);
            public static final Color DARK_TEXT_COLOR = new Color(235, 248, 253);
            public static final Color DARK_BG_COLOR = new Color(57, 130, 137);
            public static final Color DARK_HIGHLIGHT_COLOR = new Color(54, 123, 131);
        }

        public static class AudioConstants {
            // file paths

            // sfx
            public static String EAT = "audio/eat";
            public static String HIT = "audio/hit";

            // music
            public static String MAIN_MENU_MUSIC = "audio/menu_theme";
            public static String BACKGROUND_MUSIC = "audio/playing_theme";
        }
    }

    public static class Methods {

    // general purpose methods relevant for every class

        public static BufferedImage LoadImage(String filePath) {
            // method for loading image resources
            BufferedImage img = null; // set to null initially
            InputStream imageFile = Methods.class.getResourceAsStream("/assets/" + filePath + ".png");

            try {
                img = ImageIO.read(imageFile);
            } catch (IOException error) {
                error.printStackTrace(); // throws error if the file does not exist
            } finally {
                try {
                    imageFile.close();
                } catch (IOException error) {
                    error.printStackTrace();
                }
            }

            return img; // return loaded image
        }

        public static double SquaredDistanceBetween(int thisX, int thisY, int otherX, int otherY) {
            // pythagorean distance squared
            return Math.pow(thisX - otherX, 2) + Math.pow(thisY - otherY, 2);
        }

        public static int[] GenerateRandomValidPosition(GridObject[][] grid) {
            
            // get all grid values that are empty, linkedlist as size can vary
            LinkedList<String> validPos = new LinkedList<String>();

            // iterate through grid and find valid positions to append
            for(int y=2;y<grid.length;y++) {
                for(int x=0;x<grid[0].length;x++) {
                    if(grid[y][x] == GridObject.EMPTY) {
                         // check position is not covered by a snake or fruit
                        validPos.add(x + " " + y);
                    }
                }
            }

            // pick random
            String posString = validPos.get(rand.nextInt(validPos.size()));
            String[] positionTemp = posString.split(" ");
            int[] position = {Integer.valueOf(positionTemp[0]), Integer.valueOf(positionTemp[1])};
            return position;
        }

        public static int GetCentralisedTextX(String text, Graphics g) {
            // put text in the middle
            return GetCentralisedButtonTextX(text, g, 0, Constants.WindowConstants.WINDOW_WIDTH);
        }

        public static int GetCentralisedButtonTextX(String text, Graphics g, int xPos, int width) {
            int textLength = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
            return (2 * xPos + width - textLength) / 2;
        }

        public static String GetCurrentTextColor(GameManager game) {
            // return correct text colour for mode
            switch(game.getColorMode()) {
                case DARK:
                    return Constants.WindowConstants.TEXT_DARK_MODE;
                case LIGHT:
                    return Constants.WindowConstants.TEXT_LIGHT_MODE;
                default:
                    return null;
            }
        }

        public static void GetButtonColor(GameManager game, MenuButton button) {
            // change button design if dark mode
            // order is text, bg, highlight

            switch(game.getColorMode()) {
                case DARK:
                    button.setFontColor(Constants.ButtonConstants.DARK_TEXT_COLOR);
                    button.setBgColor(Constants.ButtonConstants.DARK_BG_COLOR);
                    button.setHighlightColor(Constants.ButtonConstants.DARK_HIGHLIGHT_COLOR);
                    break;
                case LIGHT:
                    button.setFontColor(Constants.ButtonConstants.LIGHT_TEXT_COLOR);
                    button.setBgColor(Constants.ButtonConstants.LIGHT_BG_COLOR);
                    button.setHighlightColor(Constants.ButtonConstants.LIGHT_HIGHLIGHT_COLOR);
                    break;
            }
        }

        public static Color GetBgColor(GameManager game) {
            switch(game.getColorMode()) {
                case DARK:
                    return Constants.WindowConstants.BG_DARK_MODE;
                case LIGHT:
                    return Constants.WindowConstants.BG_LIGHT_MODE;
                default:
                    return null;
            }
        } 
    }
}
