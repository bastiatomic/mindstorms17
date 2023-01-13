package mindstorms17;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class App {
	/*
	HOW TO USE ME
	ensure that the head is DOWN. it will not work if you dont listen to me.
    execute App.java. It will write a TMP_Positions.java file
    execute Main.java. It will run a LeJos application.
	*/

    public static void main(String[] args) throws IOException {
        
		ImageService img1 = new ImageService();
		img1.load(ImageIO.read(new File("src/graphics/2070_tycoon_logo_150.png")));
		img1.verify();
		img1.canny();
		img1.positionCreator();
		img1.exportPositions();

		System.out.println("ImageService done. Good luck with the rest.");
		System.out.println("You may now start the Main.main() function to start printing.");

    }
    
}
