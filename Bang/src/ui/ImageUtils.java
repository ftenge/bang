package ui;

import javax.swing.*;
import java.awt.*;

public class ImageUtils {
    public static ImageIcon loadImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
