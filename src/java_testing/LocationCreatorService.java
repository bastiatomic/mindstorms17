package java_testing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import javax.imageio.ImageIO;

public class LocationCreatorService {
    // README: I need a mono-color image and will create a travelerList to work with
    // the following system:
    // at start the HEAD is UP
    // then it loops: move to pos, HEAD check, repeat

    static int WHITE = new Color(255, 255, 255).getRGB();
    static int BLACK = new Color(20, 20, 20).getRGB(); // or 20,20,20

    public static void main(String[] args) throws IOException {

        final String fileName = "mock2_CANNY_COMPRESSED";
        final String fileType = "png"; // PNG is the final file tyoe, JPG has illegal compression

        BufferedImage img = ImageIO.read(new File("graphics/" + fileName + "." + fileType));
        final int WIDTH = img.getWidth();
        final int HEIGHT = img.getHeight();

        ArrayList<int[]> locations = new ArrayList<>();
        ArrayList<int[]> travelerList = new ArrayList<>();
        ArrayList<Position> locations2 = new ArrayList<>();
        boolean printerReady = true;

        //ONLY THE ADJACENT PIXELS
        int[] looper = {
        		0, -1,
        		1, 0,
        		0, 1,
        		-1, 0,};

        int GLOBAL_CANVAS_SIZE = 150;

        int sizeFactor = GLOBAL_CANVAS_SIZE / HEIGHT * -1;

        if (!printerReady) {
            sizeFactor = 1;
        }

        // System.exit(0);
        int[] extender = { 0, 0 };
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                if (img.getRGB(x, y) == BLACK) {
                    // TODO implement CORNERS, new int[]{0,0} to stay on the spot and move again

                    locations.add(new int[] { x, y }); // new "start" node starts here

                    // now its time to calculate the end node on a diagonal, vertical or horizontal

                    // get the direction arrow
                    for (int i = 0; i < looper.length - 1; i += 2) {

                        if (img.getRGB(x + looper[i], y + looper[i + 1]) == BLACK) {
                            extender[0] = looper[i];
                            extender[1] = looper[i + 1];
                            // System.out.println(extender[0] + " " + extender[1]);
                            break;
                        }
                    }
                    // we found the next trail of black pixels, lets follow them

                    // it could be connected to the latest pixel (intersection), lets find out
                    int startX = x;
                    int startY = y;
                    int debugger = 0;
                    while (img.getRGB(startX, startY) == BLACK && debugger < 20) { // this algorithm does not allow
                                                                                   // intersection. each node is either
                        // a start, end or deleted
                        img.setRGB(startX, startY, WHITE); // delete the pixel from memory
                        startX += extender[0];
                        startY += extender[1];
                        debugger++;
                        // System.out.println("debugger: " + debugger);
                    }
                    // if there are no more pixels on this 'line'
                    // locations2.add(new Position(startX -= extender[0], startY -= extender[1],
                    // true));
                    locations.add(new int[] { startX -= extender[0], startY -= extender[1] });

                }

            }
        }
        // write_file(img, fileName);
        System.out.println("locations.add(new int[]{" + locations.get(0)[0] + ", " + locations.get(0)[1] + "});");
        for (int i = 1; i < locations.size() - 1; i++) {
            // System.out.println(locations.get(i)[0] + " | "+ locations.get(i)[1]);
            // System.out.println("locations.add(new int[]{" + (locations.get(i)[0] -
            // locations.get(i - 1)[0]) * sizeFactor
            // + ", " + (locations.get(i)[1] - locations.get(i - 1)[1]) * sizeFactor +
            // "});");
            int tmp1 = (locations.get(i)[0] - locations.get(i - 1)[0]) * sizeFactor;
            int tmp2 = (locations.get(i)[1] - locations.get(i - 1)[1]) * sizeFactor;
            travelerList.add(new int[] {
                    tmp1, tmp2
            });
            locations2.add(new Position(
                    tmp1, tmp2,
                    true));
        }

        System.out.println("// ADD ME TO MAIN.JAVA");
        // cornerService

        travelerList.add(0, new int[] { locations.get(0)[0], locations.get(0)[1] });
        locations2.add(0, new Position(locations.get(0)[0] * sizeFactor, locations.get(0)[1] * sizeFactor,
                true));

        System.out.println("STARTING MERGING ZEROS");
        //TODO: still broken, will kill connected nodes
        int borderline = travelerList.size();
        for (int i = 0; i < travelerList.size() - 1; i++) {

            int tmpFactor1 = Math.abs(locations2.get(i).x - locations2.get(i + 1).x);
            int tmpFactor2 = Math.abs(locations2.get(i).y - locations2.get(i + 1).y);

            if ( tmpFactor1  == 0 ^ tmpFactor2 == 0) {
                System.out.println("ping");
                locations2.set(i + 1, new Position(locations2.get(i + 1).x, locations2.get(i + 1).y, false));
                // NEVER EVER LOOP OVER AN ARRAY AND INCREASE ITS SIZE, this is the fix
                i++;
            }

        }
        

        System.out.println("PRINT ARRAY");
        for (int i = 0; i < travelerList.size(); i++) {
            // System.out.println(travelerList.get(i)[0] + ", " + travelerList.get(i)[1]);

        }
        System.out.println("PRINT POSITION ARRAY");
        for (int i = 0; i < locations2.size(); i++) {

            System.out.println("locations.add(new int[]{"+locations2.get(i).x+ ", " + locations2.get(i).y+"});");

            /*
            System.out.println(
                    "locations.add(new Position(" +
                            locations2.get(i).x +
                            ", " +
                            locations2.get(i).y +
                            ", " +
                            locations2.get(i).headSwitch + "));");*/

        }

    }

    static void write_file(BufferedImage x, String name) throws IOException {
        File outputfile2 = new File("graphics/" + name + "_LINEFOLLOWER.png");
        ImageIO.write(x, "png", outputfile2);

    }

}
