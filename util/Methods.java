package util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Methods {

    public static BufferedImage loadImage(String filePath) {
        // method for loading image resources
        BufferedImage img = null; // set to null initially
        InputStream imageFile = Methods.class.getResourceAsStream("/assets/" + filePath);

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