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

		// init all objects
		Roboter myRobot = new Roboter("ascende_superios", new int[] { 0, 0 }, false,
				new EV3LargeRegulatedMotor(MotorPort.A),
				new EV3LargeRegulatedMotor(MotorPort.B),
				new EV3TouchSensor(SensorPort.S1),
				new EV3ColorSensor(SensorPort.S2));

		Delay.msDelay(1000); // generates FPS's amount of calls
		LCD.clear();
		LCD.drawString("Soo ...!", 0, 0);

		myRobot.move(10, 10);
		//myRobot.driveToHome();

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