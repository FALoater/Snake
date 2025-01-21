package main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Utilities {

    public static class Constants {

    // class contains constants for the game
    // gives numbers more meaningful identifiers

        public static class WindowConstants {
            public static final int TILE_SIZE = 60;
            public static final int WINDOW_WIDTH = 22 * TILE_SIZE;
            public static final int WINDOW_HEIGHT = 18 * TILE_SIZE;
            public static final int FPS = 60;
            public static final int UPS = 6;
        }

        public static class SnakeConstants {
            public static final int SNAKE_WIDTH = 60;
            public static final int SNAKE_HEIGHT = 60;
        }

        public static class FruitConstants {
            public static final int APPLE_RADIUS = 45;
            public static final int ORANGE_RADIUS = 45;
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