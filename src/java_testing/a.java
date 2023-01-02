package java_testing;

import java.util.ArrayList;

public class a {

    public static double thresholdX = 0; public static double thresholdY = 0;

    public static void main(String[] args) throws InterruptedException{

        ArrayList<int[]> locations = new ArrayList<>();

        locations.add(new int[]{-15, -15});
        locations.add(new int[]{-15, 0});
        locations.add(new int[]{18, -3});
        locations.add(new int[]{0, -9});
        locations.add(new int[]{-21, 9});
        locations.add(new int[]{0, -9});
        locations.add(new int[]{-51, 0});
        locations.add(new int[]{-12, 0});
        locations.add(new int[]{81, -3});


        for (int i = 0; i < locations.size(); i++) {
            
            move(locations.get(i)[0],locations.get(i)[1]);
        }

  
    }

    static void move(int x, int y) throws InterruptedException { // distance in mm;
        // WHATIS: I move the motors in two directions simultanesouly while respecting
        // the offset it will generate

        double rotationsNeededA = x / mindstorms17.Gear.chainGear();
        double rotationsNeededB = y / mindstorms17.Gear.wheelGear();

        System.out.println(rotationsNeededA +"="+x + "/" + mindstorms17.Gear.chainGear());
        System.out.println(rotationsNeededB +"="+y + "/" + mindstorms17.Gear.wheelGear());

        // threshold start
        thresholdX += (rotationsNeededA - (int) rotationsNeededA);
        thresholdY += (rotationsNeededB - (int) rotationsNeededB);

        // adding threshold (zero or one)
        /*
        rotationsNeededA += (int) thresholdX;
        rotationsNeededB += (int) thresholdY;

        // removing excesshreshold (1+)
        thresholdX -= (int) thresholdX;
        thresholdY -= (int) thresholdY;*/

        double speedA = 100; // magic number
        double timeForLength = rotationsNeededA / speedA;
        double speedB = rotationsNeededB / timeForLength; // TODO: this should create the same speed, maybe magic number

        if(timeForLength == 0) {speedB = 100;} // this affects if speedA = 0   
        System.out.println("TFL = " + timeForLength + "; rotA: " + rotationsNeededA + " /  speedA: " + speedA);     
        System.out.println((int)speedB +"="+(int)rotationsNeededB + " / " + (int)timeForLength);
        System.out.println("---------------------");
        //System.out.println( timeForLength + " - "+ (int)speedA + " " + (int)speedB);
        Thread.sleep(10);
    }
    
}
