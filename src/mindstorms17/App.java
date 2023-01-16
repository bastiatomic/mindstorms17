package mindstorms17;

import java.io.File;
import javax.imageio.ImageIO;

public class App {
	/*
	HOW TO USE ME
	1. ensure that the head is DOWN. it will not work if you dont listen to me.
	2. set the fileName as the image u like to print (150px by 150px in size)
    3. execute App.java. Move forward if there are no errors
    4. execute Main.java. It will run a LeJos application.
	*/

    public static void main(String[] args) throws Exception {

		//TODO: move Canny.java into ImageService.java
        
    	String fileName = "drone";

		ImageService img1 = new ImageService(fileName);
		img1.load(ImageIO.read(new File("src/graphics/"+fileName+".png")));
		img1.verify();
		img1.canny();
		img1.saveCanny();
		img1.positionCreator();
		img1.exportPositions();
		
		System.out.println("ImageService done. Good luck with the rest.");
		System.out.println("You may now start the Main.main() function to start printing.");

    }
    
}
