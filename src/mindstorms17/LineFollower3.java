package mindstorms17;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;
import javax.imageio.ImageIO;

import java_testing.Position;

public class LineFollower3 {

    // this class requires a CANNY or LINECOMPLETER file ! Do not use a COMPRESSED
    // file with me
    int WHITE = new Color(255, 255, 255).getRGB();
    int BLACK = new Color(20, 20, 20).getRGB(); // or 20,20,20
    double sizeFactor = 1;

    int WIDTH;
    int HEIGHT;
    BufferedImage img;

    ArrayList<Position> locations = new ArrayList<>();

    int[] adjacentNodesIndex = { 0, -1, 1, 0, 0, 1, -1, 0 };
    int debugger = 0;

    LineFollower3(BufferedImage a){
        this.img = a;
        this.HEIGHT = a.getHeight();
        this.WIDTH = a.getWidth();
    }

    ArrayList<Position> getLocationList() {

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                if (img.getRGB(x, y) == BLACK) {
                    // STARTING linefollower
                    // System.out.println(x + ", " + y+ "+ starting");
                    int startX = x;
                    int startY = y;
                    locations.add(new Position(x, y, true));
                    img.setRGB(x, y, WHITE);
                    debugger = 0;

                    while (debugger < 2) {

                        for (int i = 0; i < adjacentNodesIndex.length; i += 2) {
                            if (img.getRGB(x + adjacentNodesIndex[i], y + adjacentNodesIndex[i + 1]) == BLACK) {
                                img.setRGB(x + adjacentNodesIndex[i], y + adjacentNodesIndex[i + 1], WHITE);
                                locations.add(
                                        new Position(x + adjacentNodesIndex[i], y + adjacentNodesIndex[i + 1], false));
                                // System.out.println((x+adjacentNodesIndex[i]) + ", " +
                                // (y+adjacentNodesIndex[i+1]));
                                x += adjacentNodesIndex[i];
                                y += adjacentNodesIndex[i + 1];
                                debugger = 0;
                            }
                        }
                        debugger++;

                    }
                    locations.add(new Position(startX, startY, true));

                }
            }
        }

        // printPositionArray2(locations);
        // System.exit(0);

        // reverse connecting adjacent nodes
        // alle Ecken/Kanten werden gefiltert
        for (int i = 1; i < locations.size() - 1; i++) {

            // wenn der Vorgänger und Nachfolger nur um 1 unterscheidet in dieselbe
            // Richtung,
            // dann brauche ich den Mittelteil nicht mehr

            // locationsMerged.add(new Position(locations.get(i).x, locations.get(i).y,
            // true));

            int preX = Math.abs(locations.get(i).x - locations.get(i - 1).x);
            int preY = Math.abs(locations.get(i).y - locations.get(i - 1).y);

            int sucX = Math.abs(locations.get(i + 1).x - locations.get(i).x);
            int sucY = Math.abs(locations.get(i + 1).y - locations.get(i).y);
            // System.out.println(tmpFactor1 +", " + tmpFactor2);

            if (    (preX == 1 && preY == 0 && sucX == 1 && sucY == 0) ||
                    (preX == 0 && preY == 1 && sucX == 0 && sucY == 1)) {
                locations.get(i).fixer = true;// locationsMerged.add(new Position(locations.get(i+1).x, locations.get(i+1).y,              // false));
                //System.out.println(i+ " ping");
            }
        }
        Iterator<Position> iter = locations.iterator();

        while (iter.hasNext()) {
            Position str = iter.next();

            if (str.fixer)
                iter.remove();
        }

        return locations;
    }

}
