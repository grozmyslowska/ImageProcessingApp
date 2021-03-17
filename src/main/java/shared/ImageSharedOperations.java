package shared;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSharedOperations {

    public static BufferedImage loadImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException ex) {
            System.out.println("Error has occured during file reading: " + ex.getMessage());
        }
        return image;
    }

    public static void saveImage(BufferedImage img, String path, String format) {
        try {
            ImageIO.write(img, format, new File(path));
            JOptionPane.showMessageDialog(null, "Image saved!");
        } catch (IOException ex) {
            System.out.println("Error has occured during file writing: " + ex.getMessage());
        }
    }
}
