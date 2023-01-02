package java_testing;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import javax.imageio.ImageIO;

public class LineFollower {

    //this class requires a CANNY or LINECOMPLETER file ! Do not use a COMPRESSED file with me
    static int WHITE = new Color(255, 255, 255).getRGB();
    static int BLACK = new Color(20, 20, 20).getRGB(); // or 20,20,20
    

    public static void main(String[] args) throws IOException {
        final String fileName = "mock2_CANNY";
        final String fileType = "png"; // PNG is the final file type, JPG has illegal compression

        BufferedImage img = ImageIO.read(new File("graphics/" + fileName + "." + fileType));
        final int WIDTH = img.getWidth();
        final int HEIGHT = img.getHeight();
        int[] looper = {
            0, -1,
            1, 0,
            0, 1,
            -1, 0,
            -1,-1,
            -1,1,
            1,1,
            1,-1
        };

        int[] extender = {0,0};

        ArrayList<Position> locations = new ArrayList<>();

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {


                //TODO: only move the head up, if there is no more in looper
                if(img.getRGB(x, y) == BLACK){
                    //STARTING linefollower

                    locations.add(new Position(x, y, false));


                    int startX = x;
                    int startY = y;
                    int debugger = 0;
                    for (int i = 0; i < looper.length; i+=2) {
                        //found first connection, will return for road curve
                        if(img.getRGB(x + looper[i], y + looper[i+1]) == BLACK){
                            extender[0] = looper[i];
                            extender[1] = looper[i+1];
                            //we found an extender, lets use it

                            
                            while (img.getRGB(startX, startY) == BLACK && debugger < 20) { // this algorithm does not allow
                                                                                        // intersection. each node is either
                                // a start, end or deleted
                                img.setRGB(startX, startY, WHITE); // remove me from memory
                                startX += extender[0];
                                startY += extender[1];
                                //System.out.println(extender[0] + ", " + extender[1]);
                                debugger++;
                                // System.out.println("debugger: " + debugger);
                            }
                            img.setRGB(startX -= extender[0], startY -= extender[1], BLACK);
                            locations.add(new Position(startX -= extender[0], startY -= extender[1], true ));
                            startX = startX -= extender[0]; startY =  startY -= extender[1];
                            break;
                        }
                       
                        
                    }
                    printPositionArray(locations);
                    write_file(img, fileName);
                    //System.exit(0);
                    
                }

            }
        }
        printPositionArray(locations);
    }
    static void printPositionArray(ArrayList<Position> a){

        for (int i = 0; i < a.size(); i++) {

            System.out.println(a.get(i).x + ", " + a.get(i).y + " | " + a.get(i).headSwitch);
            
        }



    }
    static void write_file(BufferedImage x, String name) throws IOException {
        File outputfile2 = new File("graphics/" + name + "_LINEFOLLOWER.png");
        ImageIO.write(x, "png", outputfile2);

    }
}
