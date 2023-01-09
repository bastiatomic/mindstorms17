package java_testing;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

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

	public static void bestimmeHelligkeit(EV3ColorSensor sensor) throws InterruptedException {

		sensor.setFloodlight(false);
		LCD.drawString("Init", 2, 2);
		LCD.setAutoRefresh(false);
		SensorMode ambientSensorMode = sensor.getAmbientMode();
		float[] sample = new float[ambientSensorMode.sampleSize()];

		for (int i = 0; i < 100; i++) {
			ambientSensorMode.fetchSample(sample, 0);
			LCD.refresh();
			LCD.clear();
			LCD.drawString("Intensity: " + sample[0], 1, 1);
			Thread.sleep(100);
		}
		sensor.close();
	}

	private static void demoTachoCount() {
		RegulatedMotor mB = new EV3LargeRegulatedMotor(MotorPort.B);
		int tachoCount = mB.getTachoCount();
		mB.resetTachoCount();
		tachoCount = mB.getTachoCount();
		mB.setSpeed(100);
		mB.rotate(20);
		tachoCount = mB.getTachoCount();
		mB.close();
	}

	public static void dreheMotorFuerXSekunden(float sekunden, Port port) throws InterruptedException {
		RegulatedMotor m = new EV3LargeRegulatedMotor(port);
		m.setSpeed(200);
		m.forward();
		// Thread.sleep((int) (sekunden * 1000));
		Delay.msDelay((int) (sekunden * 1000));
		m.stop();
		m.close();
	}

	public static void dreheMotorUmXGrad(int grad, RegulatedMotor m) {
		m.rotate(grad);
		// m.close();
	}

	public static void rotateMotorByDegree(int grad, RegulatedMotor m) {
		int grad2 = grad / 60;
		m.rotate(grad2);

	}

	public static void dreheMotorUmXGradMitMaximalerGeschwindigkeit(int grad, Port port) {
		RegulatedMotor m = new EV3LargeRegulatedMotor(port);
		LCD.drawString(m.getMaxSpeed() + "", 0, 4);
		Delay.msDelay(5000);
		m.setSpeed((int) m.getMaxSpeed());
		m.rotate(grad);
		m.close();
	}

	public static void dreheMotorUmXGradMitYGradProSekunde(int grad, int gradSekunde, Port port) {
		RegulatedMotor m = new EV3LargeRegulatedMotor(port);
		LCD.drawString(m.getMaxSpeed() + "", 0, 4);
		Delay.msDelay(5000);
		m.setSpeed(gradSekunde);
		m.rotate(grad);
		m.close();
	}

	private static void synchroExample() {
		RegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
		RegulatedMotor mB = new EV3LargeRegulatedMotor(MotorPort.B);

		mA.synchronizeWith(new RegulatedMotor[] { mB });

		mA.startSynchronization();
		mA.rotate(-360, true);
		mB.rotate(-360, true);
		mA.endSynchronization();
		mA.waitComplete();
		mB.waitComplete();
	}
}
