package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Utilities {

    public static final int SCALE = 2; // scale of the game window, changes proportionally

    public static class Constants {

    // class contains constants for the game
    // gives numbers more meaningful identifiers

        public static class WindowConstants {
            public static final int TILE_SIZE = 20 * SCALE;
            public static final int TILES_IN_WIDTH = 22;
            public static final int TILES_IN_HEIGHT = 18;
            public static final int WINDOW_WIDTH = TILES_IN_WIDTH * TILE_SIZE;
            public static final int WINDOW_HEIGHT = 18 * TILE_SIZE;
            public static final int FPS = 60;
            public static final int UPS = 6; // controls speed of snake
        }

        public static class SnakeConstants {
            public static final int SNAKE_WIDTH = WindowConstants.TILE_SIZE;
            public static final int SNAKE_HEIGHT = WindowConstants.TILE_SIZE;
            public static final int PLAYER_START_X = 10 * WindowConstants.TILE_SIZE;
            public static final int PLAYER_START_Y =  15 * WindowConstants.TILE_SIZE;
            public static final int ENEMY_START_X = 10 * WindowConstants.TILE_SIZE;
            public static final int ENEMY_START_Y = 10 * WindowConstants.TILE_SIZE;

            public static final Color PLAYER_SNAKE_DEFAULT_COLOR = Color.red;
            public static final Color ENEMY_SNAKE_DEFAULT_COLOR = Color.green;
        }

        public static class FruitConstants {
            public static final int FRUIT_RADIUS = 15 * SCALE;
            // make smaller than snake to identify easier
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
    }
}
