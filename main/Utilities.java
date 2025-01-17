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
            public static final int WINDOW_HEIGHT = 1200;
            public static final int WINDOW_WIDTH = 800;
        }

        public static class SnakeConstants {
            public static final int SNAKE_HEIGHT = 60;
            public static final int SNAKE_WIDTH = 60;
        }
    }

    public static class Methods {

    // general purpose methods

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