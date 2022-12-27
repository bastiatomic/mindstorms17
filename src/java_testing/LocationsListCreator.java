package java_testing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import javax.imageio.ImageIO;

public class LocationsListCreator {
    //i'm the linefollower module

    static int WHITE = new Color(255,255,255).getRGB();
    static int BLACK = new Color(20,20,20).getRGB();
    public static void main(String[] args) throws IOException {
        
        final String fileName = "mock";
        final String fileType = "png"; //PNG is the final file tyoe, JPG has illegal compression

        BufferedImage img = ImageIO.read(new File("graphics/"+fileName+"."+fileType));
        final int WIDTH = img.getWidth();
        final int HEIGHT = img.getHeight();

        ArrayList<int[]> locations = new ArrayList<>();

        int[] looper = {0,-1, 1,-1, 1,0, 1,1, 0,1, -1, 1, -1,0, -1,-1};

        //System.exit(0);
        int[] extender = {0,0};
        for (int y = 0; y <HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                if(img.getRGB(x,y) == BLACK){

                    locations.add(new int[]{x,y}); // new "start" node starts here

                    // now its time to calculate the end node on a diagonal, vertical or horizontal

                    //get the direction arrow
                    for (int i = 0; i < looper.length-1; i+=2) {

                        if(img.getRGB(x+looper[i],y+looper[i+1]) == BLACK){
                            extender[0] = looper[i];
                            extender[1] = looper[i+1];
                            //System.out.println(extender[0] + " " + extender[1]);
                            break;
                        }
                    }
                    int startX = x;
                    int startY = y;
                    int debugger = 0;
                    while(img.getRGB(startX, startY) == BLACK && debugger <20){ //this algorithm does not allow intersection. each node is either 
                                                                //a start, end or deleted
                        img.setRGB(startX, startY, WHITE); // delete the pixel from memory
                        startX += extender[0]; startY += extender[1];
                        debugger++;
                        //System.out.println("debugger: " + debugger);
                    }
                    //if there are no more pixels on this 'line'
                    locations.add(new int[]{startX -= extender[0], startY -= extender[1]});
                    
                }
            }
        }
        write_file(img, fileName);

        for (int i = 0; i < locations.size()-1; i++) {
           //System.out.println(locations.get(i)[0] + " | "+ locations.get(i)[1]);
           System.out.println("locations.add(new int[]{"+locations.get(i)[0]+", "+ locations.get(i)[1]+"});");   
        }
        System.out.println("// ADD ME TO MAIN.JAVA");
        
    }
    static void write_file(BufferedImage x, String name) throws IOException{
        File outputfile2 = new File("graphics/"+name+"_LINEFOLLOWER.png");
        ImageIO.write(x, "png", outputfile2);

    }
    
}
