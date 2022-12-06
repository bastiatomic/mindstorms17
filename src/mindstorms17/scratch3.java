package mindstorms17;

public class scratch3 {

    public static void main(String[] args) {

        double distanceX = 135.7168;
        double distanceY = 121.0;

        double distanceOneDegreeMotorWheelMotor = (135.7168 / 360) * 12 / 36; //mm distance of 1 degree
        double distanceOneDegreeMotorChainMotor = (121.0 / 360) * 12 / 36; //TODO: abstract into classes
        
        double lengthA = distanceX;
        double lengthB = distanceY;

        //double finalLength = Math.sqrt(Math.pow(lengthA, 2) + Math.pow(lengthB, 2)); // C

        double rotationsNeededA = lengthA / distanceOneDegreeMotorChainMotor;
        double rotationsNeededB = lengthB / distanceOneDegreeMotorWheelMotor;
        
        double speedA = 100; // magic number
        double timeForLength = rotationsNeededA / speedA;
        double speedB = rotationsNeededB / timeForLength;

        System.out.println("chainMotor speed: " + (int) speedA);
        System.out.println("chainMotor rotations: " + (int) rotationsNeededA + " real value: " + rotationsNeededA);
        System.out.println("");
        System.out.println("wheelMotor speed: " + (int) speedB);
        System.out.println("chainMotor rotations: " + (int) rotationsNeededB + " real value: " + rotationsNeededB);
  
    }
    
}
