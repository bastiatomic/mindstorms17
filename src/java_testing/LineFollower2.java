package java_testing;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import javax.imageio.ImageIO;

public class LineFollower2 {

    //this class requires a CANNY or LINECOMPLETER file ! Do not use a COMPRESSED file with me
    static int WHITE = new Color(255, 255, 255).getRGB();
    static int BLACK = new Color(20,20,20).getRGB(); // or 20,20,20
    static double sizeFactor = 1;
    

    public static void main(String[] args) throws IOException {
        final String fileName = "apple_logo";
        final String fileType = "png"; // PNG is the final file type, JPG has illegal compression

        BufferedImage img = ImageIO.read(new File("src/graphics/" + fileName + "." + fileType));
        final int WIDTH = img.getWidth();
        final int HEIGHT = img.getHeight();

        ArrayList<Position> locations = new ArrayList<>();
        ArrayList<Position> locationsMerged = new ArrayList<>();
        ArrayList<Position> locationsVector = new ArrayList<>();

        int[] adjacentNodesIndex  = {0,-1,    1,0,    0,1, -1,0};
        int debugger = 0;

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                if(img.getRGB(x, y) == BLACK){
                    //STARTING linefollower
                    //System.out.println(x + ", " + y+ "+ starting");
                    int startX = x; int startY = y;
                    locations.add(new Position(x, y, true));
                    img.setRGB(x, y, WHITE);
                    debugger = 0;

   
                    while (debugger < 2){

                        for (int i = 0; i < adjacentNodesIndex.length; i+=2) {
                            if(img.getRGB(x+adjacentNodesIndex[i], y+adjacentNodesIndex[i+1]) == BLACK){
                                img.setRGB(x+adjacentNodesIndex[i], y+adjacentNodesIndex[i+1], WHITE);
                                locations.add(new Position(x+adjacentNodesIndex[i], y+adjacentNodesIndex[i+1], false));
                                //System.out.println((x+adjacentNodesIndex[i]) + ", " + (y+adjacentNodesIndex[i+1]));
                            x += adjacentNodesIndex[i]; y += adjacentNodesIndex[i+1];
                            debugger = 0;
                            }
                        }
                        debugger ++;

                    }
                    locations.add(new Position(startX, startY, true));
   
                }
            }
        }

        //printPositionArray2(locations);
        //System.exit(0);

        //reverse connecting adjacent nodes
        //alle Ecken/Kanten werden gefiltert
        for (int i = 1; i < locations.size()-1; i++) {

            //wenn der Vorgänger und Nachfolger nur um 1 unterscheidet in dieselbe Richtung, 
            //dann brauche ich den Mittelteil nicht mehr

            //locationsMerged.add(new Position(locations.get(i).x, locations.get(i).y, true));

            int preX = Math.abs(locations.get(i).x - locations.get(i -1).x);
            int preY = Math.abs(locations.get(i).y - locations.get(i -1).y);

            int sucX = Math.abs(locations.get(i+1).x - locations.get(i).x);
            int sucY = Math.abs(locations.get(i+1).y - locations.get(i).y);
            //System.out.println(tmpFactor1 +", " + tmpFactor2);

            if( (preX == 1 && preY == 0 && sucX == 0 && sucY == 1) || 
                (preX == 0 && preY == 1 && sucX == 1 && sucY == 0)  
                ){
                locations.remove(i);//locationsMerged.add(new Position(locations.get(i+1).x, locations.get(i+1).y, false));    
                //System.out.println("ping");
            }
        }

        printPositionArray2(locations);
        System.exit(0);

        //reverse build vector
        int size = locationsMerged.size();
        locationsVector.add(new Position(locations.get(0).x, locations.get(0).y, true));

        for (int i = 0; i < size-1; i++) {
            System.out.println(i);
            int x1 = locationsMerged.get(size-i-2).x - locationsMerged.get(size-i-1).x;
            int y1 = locationsMerged.get(size-i-2).y - locationsMerged.get(size-i-1).y;
            locationsVector.add(new Position(x1, y1, false));
            
        }
     
        //printPositionArray(locationsMerged);
        printPositionArray2(locationsVector);
        write_file(img, fileName);

    }
    static void printPositionArray2(ArrayList<Position> a){

        for (Position b: a) {

            //System.out.println("locations.add(new Position("+(b.x*sizeFactor) + ", " +(b.y*sizeFactor) + ", " + b.headSwitch+"));");
            System.out.println("["+(b.x*sizeFactor) + ", " +(b.y*sizeFactor) + ", " + b.headSwitch+"));");
           
        }
        System.out.println("PRINTED ARRAY OF SIZE: " + a.size());



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
