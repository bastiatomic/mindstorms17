package mindstorms17;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class tmp {
	//TODO: kill me

    public static void main(String[] args) throws IOException {
        
        ImageService img1 = new ImageService();
		img1.load(ImageIO.read(new File("src/graphics/apple_logo.png")));
		img1.verify();
		img1.canny();
		img1.positionCreator();
		img1.exportPositions();

    }
    
}
