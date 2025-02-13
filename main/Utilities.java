package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import java.util.LinkedList;
import java.util.Random;

public class Utilities {

    public static Random rand = new Random();

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
            
            public static final String APPLE_IMG = "fruits/apple";
            public static final String ORANGE_IMG = "fruits/orange";

        }

        public static class ButtonConstants {
            // button sizes and image sources
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

        public static int[] GenerateRandomValidPosition(GameManager game) {
            GridObject[][] grid = game.getGrid();
            
            // get all grid values that are empty, linkedlist as size can vary
            LinkedList<String> validPos = new LinkedList<String>();

            // iterate through grid and find valid positions to append
            for(int y=0;y<grid.length;y++) {
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
    }
}
