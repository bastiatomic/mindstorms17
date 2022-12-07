package mindstorms17;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Delay;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		// init all objects
		Robot myRobot = new Robot("ascende_superios", new int[] { 0, 0 }, false,
				new EV3LargeRegulatedMotor(MotorPort.A), // chainMotor
				new EV3LargeRegulatedMotor(MotorPort.B), // wheelMotor
				new EV3TouchSensor(SensorPort.S1), // touchSensor
				new EV3ColorSensor(SensorPort.S2)); // colorSensor

		Delay.msDelay(1000); // generates FPS's amount of calls
		LCD.clear();
		LCD.drawString("Soo ...!", 0, 0);

		myRobot.chainMotor.setSpeed(100); // 100 is a magic speed
		//Testing: is 50x 1 degree the same as 1x 50 degree
		for (int i = 0; i < 50; i++) {
			myRobot.chainMotor.rotate(1);
			LCD.clear();
			LCD.drawString(i + "", 0, 0);
			Delay.msDelay(10);
		}
		Delay.msDelay(1000);

		myRobot.chainMotor.rotate(50);
		//--------------------

		Delay.msDelay(5000);

		int speedFactor = 100;
		int radius = 2;
		int speedX = 30;
		int speedY = 30;
		double magicTranslator = 1.121626446280992; // wanna dig down the rabbit hole of that number?

		//Testing: should draw a perfect circle
		myRobot.chainMotor.forward();
		myRobot.wheelMotor.forward();
		for (int angle = 1; angle <= 360; angle++) {

			speedX += (int) (speedFactor * radius * Math.cos(angle * 3.141 / 180)); //speedX is a distance, not speed
			myRobot.chainMotor.setSpeed(speedX); //what about negative integer?

			speedY += (int) ((speedFactor * radius * Math.sin(angle * 3.141 / 180)) * magicTranslator);
			myRobot.wheelMotor.setSpeed(speedY);

			LCD.clear();
			LCD.drawString(speedX + "|" + speedY, 0, 0);

			Delay.msDelay(10);
		}
		myRobot.chainMotor.stop(); myRobot.wheelMotor.stop();

		
		//------------------

		/*
		 * myRobot.move(20, -20); // positive integer drives to their sensor
		 * myRobot.move(-20, -20);
		 * myRobot.move(-20, 20);
		 * myRobot.move(20, 20); // 15mm
		 * //myRobot.driveToHome();
		 */

	}


}