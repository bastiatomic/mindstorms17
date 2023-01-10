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
        int RED = new Color(255, 0, 0).getRGB();
        int[] looper8_round = { // TODO: check adjacent first and then diagonal to prevent misuse
                -1, 0, // left
                -1, -1, // topleft
                0, -1, // top
                1, -1, // topright
                1, 0, // right
                1, 1, // bottomright
                0, 1, // bottom
                -1, 1 // bottomleft
        };
        int[] looper8_adjacentFirst = { // TODO: check adjacent first and then diagonal to prevent misuse
                0, -1, // top
                1, 0, // right
                0, 1, // bottom
                -1, 0, // left
                -1, -1, // topleft
                1, -1, // topright
                1, 1, // bottomright
                -1, 1 // bottomleft
        };

        int[] looper = looper8_adjacentFirst;
        // static int[] looper4 ={0, -1,1, 0,0, 1, -1, 0};

        // I NEED A CANNY IMAGE
        BufferedImage img = this.img_canny;

        int HEIGHT = img.getHeight();
        int WIDTH = img.getWidth();
        //ArrayList<Position> positionList = new ArrayList<>();
        boolean noEndFound = true;
        boolean endOfPixelsReached = false;
        int i = 0;
        boolean headSwitchNow = false;
        int arraySizeLimiter = 0;

        int[] current_pos = { 0, 0 };
        int[] current_checking_pos = { 0, 0 };

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                // found first node
                if (img.getRGB(x, y) == BLACK) {
                    noEndFound = true;
                    endOfPixelsReached = false;
                    headSwitchNow = true;
                    // System.out.println("found start at: " + x+", "+y);
                    this.positions.add(new Position(x, y, true));

                    //replace the head before this object
                    

                    System.out.println("a new beginning?");
                    //img.setRGB(x, y, WHITE); //TODO: do not remove the start to allow end-connecting
                    current_pos[0] = x;
                    current_pos[1] = y;

                    //the fist one shall be headSwitch=true to move the head down again
                   while(!endOfPixelsReached) { // search until no black neighbors
                        arraySizeLimiter = this.positions.size();
                        // loop over all neighbors
                        for (i = 0; i < looper.length; i += 2) {
                            current_checking_pos[0] = current_pos[0] + looper[i];
                            current_checking_pos[1] = current_pos[1] + looper[i + 1];

                            if (img.getRGB(current_checking_pos[0], current_checking_pos[1]) == BLACK) {
                                this.positions.add(new Position(current_checking_pos[0], current_checking_pos[1], headSwitchNow));
                                headSwitchNow = false; // first call will set it true
                                img.setRGB(current_checking_pos[0], current_checking_pos[1], WHITE);
                                current_pos[0] = current_checking_pos[0];
                                current_pos[1] = current_checking_pos[1];
                                break;

                            }
                            
                        }
                        //System.out.println(positionList.size());
                        if(arraySizeLimiter == this.positions.size()){
                            endOfPixelsReached = true;
                            //break; //exchange with while loop
                        }

                       /*  if(i == 14){//if i'm here and i == 14 (len-2); i checked the last looper and found no neighbor
                            positionList.add(new Position(current_checking_pos[0], current_checking_pos[1], true, false));
                            noEndFound = false;*/
                        //}
                        //TODO: the first shall be headSwitch=True
                        //TODO: the last element is a duplicate and the headSwitch=True by last one
                        //TODO: check if size of positionList is different from last call
                        //technically no more pixels.
                        //positionList.add(new Position(current_checking_pos[0], current_checking_pos[1], true, false));
                        //noEndFound = false; // this will continue the WIDTH-HEIGHT-loop

                    }

                }

            }

        }
        //the last element shall be headSwitch=true
        this.positions.add(new Position(current_checking_pos[0], current_checking_pos[1], false));  
        this.positions.add(new Position(current_checking_pos[0], current_checking_pos[1], true));                  

    }

    void exportPositions() throws IOException {

        PrintWriter f0 = new PrintWriter(new FileWriter("output2.txt"));

        // fix stuff
        for (Position b : this.positions) {

            // f0.println("["+b.x + ", " + b.y + ", " + b.headSwitch+"], ");
            f0.println("img1.positions.add( new Position(" + b.x + ", " + b.y + ", " + b.headSwitch + ")); ");
            //f0.println("posList.append( (" + b.x + ", " + b.y +") )");

        }
        f0.close();
    }

}
