package java_testing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import javax.imageio.ImageIO;

public class b {
    //make a lineBuilder

    static int WHITE = new Color(255,255,255).getRGB();
    static int BLACK = new Color(20,20,20).getRGB();
    public static void main(String[] args) throws IOException {
        
        final String fileName = "hzd_kappa_CANNY_COMPRESSED";
        final String fileType = "png";

        BufferedImage img = ImageIO.read(new File("graphics/"+fileName+"."+fileType));
        final int WIDTH = img.getWidth();
        final int HEIGHT = img.getHeight();

        int[] looper = {0,-1, 1,-1, 1,0, 1,1, 0,1, -1, 1, -1,0, -1,-1};

        for (int i = 0; i < looper.length*2; i+=2) {
               System.out.println(looper[i]+ " " + looper[i+1]);         
        }

        System.exit(0);
        for (int y = 0; y <HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                if(img.getRGB(x,y) == BLACK){

                    //start module
                    for (int i = 0; i < looper.length*2; i++) {
                        
                    }

                }
            }
        }
        
    }
    
}
