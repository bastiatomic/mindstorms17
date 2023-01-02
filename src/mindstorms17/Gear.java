package mindstorms17;

// chainGear: 12,3cm pro Umdrehung

// wheelGear: 43,2mm Durchmesser

// 43*2*PI = 135,69 mm = 13,569cm

//43.2 * PI / (360*3) = Distanz bei 1 Grad-Motor-Drehung = 0,1256mm = 0,01256cm pro Grad wheelGear (2364 nodes)

//small -> large -> chain: 0,01129cm / degree (reults in 1860 unique nodes WIDTH)

// small -> large -> wheelGear: 

//A4 = 21.0 x 29.7 cm 

// 1 -> 3

public class Gear {
 
    public static double chainGear() { // mm distance of 1 degree
        double gearAxis = 36.0;
        double gearMotor = 12.0;
        double wheelScope = 121.0;

        double shift = gearMotor / gearAxis;
        double outputOneDegreeMotor = (wheelScope / 360) * shift;

        return outputOneDegreeMotor;
    }

    public static double wheelGear() {
        double gearAxis = 36.0;
        double gearMotor = 12.0;
        double wheelScope = 135.7168;

        double shift = gearMotor / gearAxis;
        double outputOneDegreeMotor = (wheelScope / 360) * shift;

        return outputOneDegreeMotor;
    }

}