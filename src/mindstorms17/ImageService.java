package mindstorms17;

import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import lejos.hardware.lcd.LCD;

import java.awt.Color;

public class ImageService {

    BufferedImage img;
    BufferedImage img_canny;
    ArrayList<Position> positions;

    ImageService() {
        this.positions = new ArrayList<>(); // empty constructor
    }

    void load(BufferedImage a) {
        this.img = a;

    }

    void verify() {
        if (img.getWidth() > 150 || img.getHeight() > 150) {
            LCD.drawString("img invalid", 0, 0);
            System.exit(-1);
        }
    }

    void canny() {

        Canny detector = new Canny();
        BufferedImage convertedImg = new BufferedImage(this.img.getWidth(), this.img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        convertedImg.getGraphics().drawImage(this.img, 0, 0, null);
        detector.setSourceImage(convertedImg);
        detector.process();
        this.img_canny = detector.getEdgesImage();

    }

    void positionCreator() {
        /*
         * Flow:
         * get a list of all black pixels
         * reduce adjacent pixels in 4-directional (x-1, y+1 etc)
         * connect the remaining nodes in all 8 directions
         */
        int WHITE = new Color(255, 255, 255).getRGB();
        int BLACK = new Color(20, 20, 20).getRGB(); // or 20,20,20
 
        int[] looper = { // TODO: check adjacent first and then diagonal to prevent misuse
                0, -1, // top
                1, 0, // right
                0, 1, // bottom
                -1, 0, // left
              -1, -1, // topleft
                1, -1, // topright
                1, 1, // bottomright
                -1, 1 // bottomleft
        };

        // I NEED A CANNY IMAGE
        BufferedImage img = this.img_canny; //TODO: extract me
        int HEIGHT = img.getHeight();
        int WIDTH = img.getWidth();
        boolean endOfPixelsReached = false;
        int i = 0;
        boolean headSwitchNow = false;
        int arraySizeLimiter = 0;

        int[] current_pos = { 0, 0 };
        int[] current_checking_pos = { 0, 0 };
        int pathLength = 0;

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                // found first node
                if (img.getRGB(x, y) == BLACK) {
                    endOfPixelsReached = false;
                    headSwitchNow = true;
                    // System.out.println("found start at: " + x+", "+y);
                    this.positions.add(new Position(x, y, true));
                    //System.out.println("found start");
                    pathLength = 0;

                    current_pos[0] = x;
                    current_pos[1] = y;

                    // the fist one shall be headSwitch=true to move the head down again
                    while (!endOfPixelsReached) { // search until no black neighbors
                        arraySizeLimiter = this.positions.size();
                        pathLength++;
                        // loop over all neighbors

                        for (i = 0; i < looper.length; i += 2) {
                            current_checking_pos[0] = current_pos[0] + looper[i];
                            current_checking_pos[1] = current_pos[1] + looper[i + 1];

                            if (img.getRGB(current_checking_pos[0], current_checking_pos[1]) == BLACK) {
                                this.positions.add(
                                        new Position(current_checking_pos[0], current_checking_pos[1], headSwitchNow));
                                headSwitchNow = false; // first call will set it true
                                img.setRGB(current_checking_pos[0], current_checking_pos[1], WHITE);
                                current_pos[0] = current_checking_pos[0];
                                current_pos[1] = current_checking_pos[1];
                                //System.out.println("found neighbor");
                                break;
                            }

                        }

                        // end of current "black line" reached
                        if (arraySizeLimiter == this.positions.size()) {
                            endOfPixelsReached = true;
                        }

                    }
                    //if the node is lonely like me, rmeove it
                    if (pathLength == 1) {
                        this.positions.remove(this.positions.size()-1);
                        //System.out.println("found lonely pixel");


                    }

                }

            }

        }
        // the last element shall be headSwitch=true
        this.positions.add(new Position(current_checking_pos[0], current_checking_pos[1], false));
        this.positions.add(new Position(current_checking_pos[0], current_checking_pos[1], true));

    }

    void exportPositions() throws IOException {
        //this function fills a TMP_Positions file for later use by the Main.main() function

        PrintWriter a = new PrintWriter(new FileWriter("src/mindstorms17/TMP_Positions.java"));
        PrintWriter c = new PrintWriter(new FileWriter("src/mindstorms17/PYTHON_Positions.txt"));

        a.write( "package mindstorms17; import java.util.ArrayList;public class TMP_Positions{static ArrayList<Position> write(){ArrayList<Position> a = new ArrayList<>();");
        System.out.println(this.positions.size());
        a.println("");
        String PYTHON_BOOLEAN = "";
        for (Position b : this.positions) {
            a.println("a.add( new Position(" + b.x + ", " + b.y + ", " + b.headSwitch + ")); ");

            if(b.headSwitch){
                PYTHON_BOOLEAN = "True";
            } else{
                PYTHON_BOOLEAN = "False";
            }

            c.println("[" + b.x + ", " + b.y + ", " + PYTHON_BOOLEAN + "], ");

        }
        a.write("return a;}} ");
        a.close();
        c.close();
    }
}
