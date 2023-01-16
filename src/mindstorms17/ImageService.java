package mindstorms17;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.Color;

class ImageService {

    String name;
    BufferedImage img;
    BufferedImage img_canny;
    ArrayList<Position> positions = new ArrayList<>();

    ImageService() {}

    ImageService(String a) {
        this.name = a;
    }

    void load(BufferedImage a) {
        this.img = a;

    }

    void verify() throws Exception {
        if (img.getWidth() != 150 || img.getHeight() != 150) {
            throw new Exception("Image size invalid. Images needs 150x150 pixels.");
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
         */
        int WHITE = new Color(255, 255, 255).getRGB();
        int BLACK = new Color(20, 20, 20).getRGB(); // or 20,20,20

        int[] looper = { // HOW IT WORKS: check adjacent first and then diagonal to prevent misuse
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
        int HEIGHT = this.img_canny.getHeight();
        int WIDTH = this.img_canny.getWidth();
        boolean endOfPixelsReached = false;
        boolean headSwitchNow = false;
        int arraySizeLimiter = 0;

        int[] current_pos = { 0, 0 };
        int[] current_checking_pos = { 0, 0 };
        int pathLength = 0;

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                // found first node
                if (this.img_canny.getRGB(x, y) == BLACK) {
                    endOfPixelsReached = false;
                    headSwitchNow = true;
                    // found start
                    this.positions.add(new Position(x, y, true));
                    pathLength = 0;

                    current_pos[0] = x;
                    current_pos[1] = y;

                    // the fist one shall be headSwitch=true to move the head down again
                    while (!endOfPixelsReached) { // search until no black neighbors
                        arraySizeLimiter = this.positions.size();
                        pathLength++;
                        // loop over all neighbors

                        for (int i = 0; i < looper.length; i += 2) {
                            current_checking_pos[0] = current_pos[0] + looper[i];
                            current_checking_pos[1] = current_pos[1] + looper[i + 1];

                            if (this.img_canny.getRGB(current_checking_pos[0], current_checking_pos[1]) == BLACK) {
                                //found neighbor
                                this.positions.add(
                                        new Position(current_checking_pos[0], current_checking_pos[1], headSwitchNow));
                                headSwitchNow = false; // first call will set it true
                                this.img_canny.setRGB(current_checking_pos[0], current_checking_pos[1], WHITE);
                                current_pos[0] = current_checking_pos[0];
                                current_pos[1] = current_checking_pos[1];
                                
                                break;
                            }

                        }

                        // end of current "black line" reached
                        if (arraySizeLimiter == this.positions.size()) {
                            endOfPixelsReached = true;
                        }

                    }
                    // if the node is lonely like me, rmeove it
                    if (pathLength == 1) {
                        this.positions.remove(this.positions.size() - 1);
                    }

                }

            }

        }
        // the last element shall be headSwitch=true
        this.positions.add(new Position(
        		this.positions.get(this.positions.size()-1).x,
        		this.positions.get(this.positions.size()-1).y,
        		true));
        //this.positions.add(new Position(current_checking_pos[0], current_checking_pos[1], false));
        //this.positions.add(new Position(current_checking_pos[0], current_checking_pos[1], true));
        //TODO: produces illegal output if last pixel is pathlength=1

    }

    void saveCanny() throws IOException {

        File outputfile2 = new File("src/graphics/" + this.name + "_CANNY.png");
        ImageIO.write(this.img_canny, "png", outputfile2);

    }

    void exportPositions() throws IOException {
        // this function fills a TMP_Positions file for later use by the Main.main()
        // function

        PrintWriter a = new PrintWriter(new FileWriter("src/mindstorms17/TMP_Positions.java"));

        a.write("package mindstorms17; import java.util.ArrayList;public class TMP_Positions{static ArrayList<Position> fetchData(){ArrayList<Position> a = new ArrayList<>();");
        a.println("");
        for (Position b : this.positions) {
            a.println("a.add( new Position(" + b.x + ", " + b.y + ", " + b.headSwitch + ")); ");

        }
        a.write("return a;}} ");
        a.close();
    }
}
