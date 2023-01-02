package java_testing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import javax.imageio.ImageIO;

public class TravelerService2 {
    // README: I need a mono-color image and will create a travelerList to work with
    // the following system:
    // at start the HEAD is UP
    // then it loops: move to pos, HEAD check, repeat

    static int WHITE = new Color(255, 255, 255).getRGB();
    static int BLACK = new Color(20, 20, 20).getRGB();

    public static void main(String[] args) throws IOException {

        final String fileName = "mock2_CANNY";
        final String fileType = "png"; // PNG is the final file tyoe, JPG has illegal compression

        BufferedImage img = ImageIO.read(new File("graphics/" + fileName + "." + fileType));
        final int WIDTH = img.getWidth();
        final int HEIGHT = img.getHeight();

        ArrayList<Position[]> locations = new ArrayList<>();


        int[] looper = { 0, -1, 1, -1, 1, 0, 1, 1, 0, 1, -1, 1, -1, 0, -1, -1 };


        // System.exit(0);
        int[] extender = { 0, 0 };
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
            	}
            }
        }

}
