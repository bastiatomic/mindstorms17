package mindstorms17;

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

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO: make cursive font in ONE connected line. each letter has a start and
		// stop and some form of magic connection

		// init all objects
		Robot myRobot = new Robot("ascende_superios", new int[] { 0, 0 }, false,
				new EV3LargeRegulatedMotor(MotorPort.A),
				new EV3LargeRegulatedMotor(MotorPort.B),
				new EV3TouchSensor(SensorPort.S1),
				new EV3ColorSensor(SensorPort.S2));

		Delay.msDelay(1000); // generates FPS's amount of calls
		LCD.clear();
		LCD.drawString("Soo ...!", 0, 0);

		// question: is 50x 1 degree the same as 1x 50 degree
		for (int i = 0; i < 50; i++) {
			myRobot.chainMotor.rotate(1);
			LCD.clear();
			LCD.drawString(i + "", 0, 0);
			Delay.msDelay(10);
		}
		Delay.msDelay(1000);
		myRobot.chainMotor.rotate(50);

		Delay.msDelay(5000);
		int speedFactor = 100;
		int radius = 1;
		int speedX;
		int speedY;
		double magicTranslator = 1.258045884996927; // wanna dig down the rabbit hole of that number?

		for (int angle = 0; angle < 360; angle++) {

			speedX = (int) (speedFactor * radius * Math.cos(angle * 3.141 / 180));
			myRobot.chainMotor.setSpeed(speedX);

			speedY = (int) ((speedFactor * radius * Math.sin(angle * 3.141 / 180)) * magicTranslator);
			myRobot.wheelMotor.setSpeed(speedY);

			LCD.clear();
			LCD.drawString(speedX + "|" + speedY, 0, 0);

			Delay.msDelay(10);
		}

		/*
		 * myRobot.move(20, -20); // positive integer drives to their sensor
		 * myRobot.move(-20, -20);
		 * myRobot.move(-20, 20);
		 * myRobot.move(20, 20); // 15mm
		 * //myRobot.driveToHome();
		 */

		LCD.drawString("Returning", 0, 0);

		// bestimmeHelligkeit(lightSensor);
		// driveByPressing();

		// driveWheelGearToZero(radMotor, lightSensor);
		LCD.drawString("Returning", 0, 0);

	}

	public static void move(int distance, int mmSec, Port motorport) {

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