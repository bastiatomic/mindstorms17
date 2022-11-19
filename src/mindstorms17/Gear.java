package mindstorms17;


// chainGear: 12,3cm pro Umdrehung (results in 614 unique nodes WIDTH)

// wheelGear: 43,2mm Durchmesser

// 43*2*PI = 135,69 mm = 13,569cm (results in 792 unique nodes LENGTH)

//43.2 * PI / (360*3) = Distanz bei 1 Grad-Motor-Drehung = 0,1256mm = 0,01256cm pro Grad wheelGear (2364 nodes)

//small -> large -> chain: 0,01129cm / degree (reults in 1860 unique nodes WIDTH)

// small -> large -> wheelGear: 

//A4 = 21.0 x 29.7 cm 

// 1 -> 3

import lejos.hardware.motor.EV3LargeRegulatedMotor;
        import lejos.hardware.port.MotorPort;
        import lejos.hardware.port.Port;
        import lejos.hardware.port.SensorPort;
        import lejos.hardware.sensor.EV3TouchSensor;
        import lejos.hardware.sensor.SensorMode;
        import lejos.robotics.RegulatedMotor;

public class Gear {
    public static void main(String[] args){


        RegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
        RegulatedMotor mB = new EV3LargeRegulatedMotor(MotorPort.B);
        mA.setSpeed((int) 15.617376); mB.setSpeed((int) 12.493900);

        mA.synchronizeWith(new RegulatedMotor[] { mB });

        mA.startSynchronization();
        mA.rotate(100, true);
        mB.rotate(100, true);
        mA.endSynchronization();
        mA.waitComplete();
        mB.waitComplete();

    }


    public static double gearsMotor2() {
        double gearAxis = 36.0;
        double gearMotor = 12.0;
        double wheelScope = 135.7168;

        double shift = gearMotor / gearAxis;
        double outputOneDegreeMotor = (wheelScope / 360) * shift;

        return outputOneDegreeMotor;
    }

    public static double gearsMotor1() {
        double gearAxis = 36.0;
        double gearMotor = 12.0;
        double wheelScope = 121.0;

        double shift = gearMotor / gearAxis;
        double outputOneDegreeMotor = (wheelScope / 360) * shift;

        return outputOneDegreeMotor;
    }

}

