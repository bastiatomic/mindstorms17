package mindstorms17;

import java.awt.image.BufferedImage;

import lejos.hardware.lcd.LCD;

public class ImageService {

    BufferedImage img;

    ImageService(){ //empty constructor
    }

    void load(BufferedImage a){
        this.img = a;

    }

    void verify(){
        if(img.getWidth() > 150 || img.getHeight() > 150){
            LCD.drawString("img invalid", 0, 0);
            System.exit(-1);
        }
    }


    void canny(){

    }

    void positionCreator(){

    }
    
}

