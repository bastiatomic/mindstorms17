package mindstorms17;

/*
WHAT DO WE SEE HERE
this class is for constructing each gear with its size, ratio and motor direction
it returns a mm value that the motor moves when it rotates by 1 degree
*/

public class Gear {
 
    static double chainGear() { 
        double gearAxis = 36.0;
        double gearMotor = 12.0;
        double wheelScope = 121.0;
        double motorMoveDirection = 1; // 1: clockwise; -1: counter-clockwise

        double shift = gearMotor / gearAxis;
        double outputOneDegreeMotor = (wheelScope / 360) * shift * motorMoveDirection;

        return outputOneDegreeMotor;
    }

    static double wheelGear() {
        double gearAxis = 36.0;
        double gearMotor = 12.0;
        double wheelScope = 135.7168;
        double motorMoveDirection = 1;

        double shift = gearMotor / gearAxis;
        double outputOneDegreeMotor = (wheelScope / 360) * shift * motorMoveDirection;
        
        return outputOneDegreeMotor;
    }

}