package mindstorms17;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class App {
	//HOW TO USE ME
    //execute App.java. It will write a TMP_Positions.java file
    //execute Main.java. It will run a LeJos application. 
        //fetching the TMP_Position.java file and start the robot.

    public static void main(String[] args) throws IOException {
        
        ImageService img1 = new ImageService();
		img1.load(ImageIO.read(new File("src/graphics/tony_helmet.png")));
		img1.verify();
		img1.canny();
		img1.positionCreator();
		img1.exportPositions();

    }
    
}
