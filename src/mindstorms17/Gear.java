package mindstorms17;

public class Gear {
 
    public static double chainGear() { 
        double gearAxis = 36.0;
        double gearMotor = 12.0;
        double wheelScope = 121.0; //ORIGINAL: 121.0
        double motorMoveDirection = 1;

        double shift = gearMotor / gearAxis;
        double outputOneDegreeMotor = (wheelScope / 360) * shift * motorMoveDirection;

        return outputOneDegreeMotor; // mm distance of 1 degree
    }

    public static double wheelGear() {
        double gearAxis = 36.0;
        double gearMotor = 12.0;
        double wheelScope = 135.7168; //ORIGINAL: 135.7168
        double motorMoveDirection = 1;

        double shift = gearMotor / gearAxis;
        double outputOneDegreeMotor = (wheelScope / 360) * shift * motorMoveDirection;
        
        return outputOneDegreeMotor;
    }

}