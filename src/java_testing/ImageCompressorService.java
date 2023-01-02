package java_testing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import javax.imageio.ImageIO;

/*@author Bastian Hesse
What do we see here?
- remove unecessary pixels
- the head will search the first pixel, then drive in a 90 or 45 degree move to the next. if they are connected (around), 
the head will not go up, if it wont find an adjacent pixel, the head will go up and continue its search to the next
psoition in the list. it will always follow the locations_list and move the head adaptingly.
ATTENTION: after this service no pixel will have a adjacent (NESW) neighbor, respect this!
*/

public class ImageCompressorService {
    static int WHITE = new Color(255,255,255).getRGB();
    static int BLACK = new Color(20,20,20).getRGB();
    public static void main(String[] args) throws IOException {
        
        final String fileName = "drone_CANNY";
        final String fileType = "png";

        BufferedImage img = ImageIO.read(new File("graphics/"+fileName+"."+fileType));
        final int WIDTH = img.getWidth();
        final int HEIGHT = img.getHeight();



        //img.getRGB(pointer[0], pointer[1])

        for (int y = 0; y <HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                if(img.getRGB(x,y) == BLACK){
                    try{

                        if(img.getRGB(x-1, y-1) == BLACK){
                            img.setRGB(x-1, y, WHITE);
                            img.setRGB(x, y-1, WHITE);
                        }
                        if(img.getRGB(x+1, y+1) == BLACK){
                            img.setRGB(x+1, y, WHITE);
                            img.setRGB(x, y+1, WHITE);
                        }
                        if(img.getRGB(x+1, y-1) == BLACK){
                            img.setRGB(x+1, y, WHITE);
                            img.setRGB(x, y-1, WHITE);
                        }
                        if(img.getRGB(x-1, y+1) == BLACK){
                            img.setRGB(x-1, y, WHITE);
                            img.setRGB(x, y+1, WHITE);
                        }

                    } catch (IndexOutOfBoundsException e){}



                }

            }     
        }

        write_file(img, fileName);

        //generateCoordList(img);

    }
    static void write_file(BufferedImage x, String name) throws IOException{
        File outputfile2 = new File("graphics/"+name+"_COMPRESSED.png");
        ImageIO.write(x, "png", outputfile2);

    }
    static void generateCoordList(BufferedImage img){
        ArrayList<int[]> locations = new ArrayList<>();
        int HEIGHT = img.getHeight();
        int WIDTH = img.getHeight();

        for (int y = 0; y <HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                if(img.getRGB(x,y) == BLACK){
                    
                    if (locations.size()>0){
                        int a = locations.get(locations.size()-1)[0];
                        int b = locations.get(locations.size()-1)[1];
                        locations.add(new int[]{x-a,y-b});
                    } else {
                        locations.add(new int[]{x,y});
                    }

                    
                }
            }
        }

        print(locations);

    }
    
    static void print(ArrayList<int[]> a){
        for (int i = 0; i < a.size()-1; i++) {
            System.out.println(a.get(i)[0]+", " + a.get(i)[1]);
        }
    }
}
