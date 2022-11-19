package mindstorms17;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;

public class Basics {
    public static void main(String[] args){


        RegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
        RegulatedMotor mB = new EV3LargeRegulatedMotor(MotorPort.B);
        mA.setSpeed(20); mB.setSpeed(20);

        mA.synchronizeWith(new RegulatedMotor[] { mB });

        mA.startSynchronization();
        mA.rotate(-360, true);
        mB.rotate(-360, true);
        mA.endSynchronization();
        mA.waitComplete();
        mB.waitComplete();

    }

    public static double hypothenuseLength(int x, int y, int startX, int startY) {
         double lengthA = x - startX;
         double lengthB = y - startY;

        return Math.sqrt((Math.pow(lengthA, 2) + Math.pow(lengthB, 2)));
    }

    public static double[] hypothenuseSpeedOfEachMotor(double lengthHypothenuse, int wishedSpeed, int lengthA, int lengthB) {

        double neededTime = lengthHypothenuse / wishedSpeed;
         double speedA = lengthA / neededTime;
         double speedB = lengthB / neededTime;

         return new double[]{speedA, speedB};
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


    public static void moveA(int distance, int mmSec) {
        RegulatedMotor m = new EV3LargeRegulatedMotor(MotorPort.A);
        int degree = (int) Math.round(distance / gearsMotor1());                                     // Ergibt Gradzahl
        int degreeSec = (int) Math.round(mmSec / gearsMotor1());
        m.setSpeed(degreeSec);
        m.rotate(degree);
        m.close();
    }

    public static void moveB(int distance, int mmSec) {
        RegulatedMotor m = new EV3LargeRegulatedMotor(MotorPort.B);
        int degree = (int) Math.round(distance / gearsMotor2());                                     // Ergibt Gradzahl
        int degreeSec = (int) Math.round(mmSec / gearsMotor2());
        m.setSpeed(degreeSec);
        m.rotate(degree);
        m.close();
    }

    public static void moveMotorUntilSensor() {
        RegulatedMotor m = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3TouchSensor sensor = new EV3TouchSensor(SensorPort.S1);
        SensorMode mode = sensor.getTouchMode();
        float[] values = new float[mode.sampleSize()];
        mode.fetchSample(values, 0);
        m.setSpeed(20);
        while (values[0] == 0) {
            m.forward();
            mode.fetchSample(values, 0);
        }
        m.stop();

    }
}
