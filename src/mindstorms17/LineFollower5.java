package mindstorms17;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.awt.Color;
import javax.imageio.ImageIO;

import java_testing.Position;

public class LineFollower5 {
    static int WHITE = new Color(255, 255, 255).getRGB();
    static int BLACK = new Color(20,20,20).getRGB(); // or 20,20,20
    static int RED = new Color(255,0,0).getRGB();
    static int[] looper8_round = { //TODO: check adjacent first and then diagonal to prevent misuse
        -1, 0, //left
        -1, -1, //topleft
        0, -1, //top
        1, -1, //topright
        1,0, //right
        1,1, //bottomright
        0,1, //bottom
        -1,1 //bottomleft
    };
    static int[] looper8_adjacentFirst = { //TODO: check adjacent first and then diagonal to prevent misuse
        0, -1, //top
        1,0, //right
        0,1, //bottom
        -1, 0, //left
        -1, -1, //topleft
        1, -1, //topright
        1,1, //bottomright
        -1,1 //bottomleft
    };

    static int[] looper = looper8_adjacentFirst;
    //static int[] looper4 ={0, -1,1, 0,0, 1, -1, 0};
    

    /*
     * Flow:
     * get a list of all black pixels
     * reduce adjacent pixels in 4-directional (x-1, y+1 etc)
     * connect the remaining nodes in all 8 directions
     */

     public static void main(String[] args) throws IOException {
        
        //I NEED A CANNY IMAGE
        String fileName = "apple_logo_CANNY";
        BufferedImage img = ImageIO.read(new File("src/graphics/"+fileName+".png"));

        int HEIGHT = img.getHeight(); int WIDTH = img.getWidth();
        ArrayList<Position> positionList = new ArrayList<>();

        int[] current_pos = {0,0};
        int[] current_checking_pos = {0,0};

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                

                //found first node
                if(img.getRGB(x, y) == BLACK){
                    //System.out.println("found start at: " + x+", "+y);
                    positionList.add(new Position(x, y, true));
                    img.setRGB(x, y, WHITE);
                    current_pos[0] = x; current_pos[1] = y;

                    for(int q = 0; q < 1000; q++){ //how many pixels will be searched
                        
                        //loop over all neighbors
                        for (int i = 0; i < looper.length; i+=2) {
                            current_checking_pos[0] = current_pos[0] + looper[i];
                            current_checking_pos[1] = current_pos[1] + looper[i+1];

                            if(img.getRGB(current_checking_pos[0], current_checking_pos[1]) == BLACK){
                                positionList.add(new Position(current_checking_pos[0], current_checking_pos[1], false));
                                img.setRGB(current_checking_pos[0], current_checking_pos[1], WHITE);
                                current_pos[0] = current_checking_pos[0];
                                current_pos[1] = current_checking_pos[1];
                                break;

                            }

                           // System.out.println(current_checking_pos[0] + ", " + current_checking_pos[1] +": " + img.getRGB(current_checking_pos[0],current_checking_pos[1]));
                        }
                        
                    }
                    
                    
                }

            }

        }
        printIntArray(positionList);
        System.exit(1);
    }
        

     static void printIntArray(ArrayList<Position> a) throws IOException{
        PrintWriter f0 = new PrintWriter(new FileWriter("output.txt"));

		//fix stuff
		for(Position b: a)
		{
			
			f0.println("["+b.x + ", " + b.y + ", " + b.headSwitch+"], ");
			
		}
		f0.close();
     }
    
}
