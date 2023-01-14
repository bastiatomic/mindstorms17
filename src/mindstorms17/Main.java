package mindstorms17;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Delay;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws InterruptedException, IOException {
		//ATTENTION
		//make sure you have read the readme before running anything :)
		
		//TODO: recursive depth-first search, so u never really move the head to a "new" position e.g. "flood function"
		//TODO: forget above todo
		//TODO: stop lightSensor after driveToHome() to remove annoying light
		// CHAPTER 1: init robot object
		Robot myRobot = new Robot(0,0,true,
				new EV3LargeRegulatedMotor(MotorPort.A), // chainMotor
				new EV3LargeRegulatedMotor(MotorPort.B), // wheelMotor
				new EV3LargeRegulatedMotor(MotorPort.C), // headMotor
				new EV3TouchSensor(SensorPort.S1), // touchSensor
				new EV3ColorSensor(SensorPort.S2)); // colorSensor

		//CHAPTER 2: getting the location file created by App.java
		LCD.clear();LCD.drawString("Img processing ...", 0, 0);
		ImageService img1 = new ImageService();
		img1.positions = TMP_Positions.write();

		//CHAPTER 3: init start drawing
		final long startTime = System.nanoTime();
		LCD.clear();
		LCD.drawString("driving home", 0, 0);
		LCD.drawString("for christmas", 1, 1);
		myRobot.driveToHome(); // both speeds are very good //positive integer drive towards sensor

		//CHAPTER 4: moving & printing to all positions
		int a = 0;
		for (Position b : img1.positions) {
			
			int seconds = (int) ((System.nanoTime() - startTime)/1_000_000_000);
			
			//LCD.clear(); //TODO: test me
			LCD.drawString("[Tour] " + a + "/" + img1.positions.size()+"     ", 0, 1);
			LCD.drawString("[Time] " + seconds + " sec.", 0, 3);
			LCD.drawString("Take a hike,", 0, 6);
			LCD.drawString("be curious", 1, 7);

			if (b.headSwitch) {
				myRobot.headSwitch();
			}
			myRobot.moveToPos(b.x, b.y);

			a++;

		}

		Delay.msDelay(5000);
		//CHAPTER 5: cleanup at the end of the printing
		LCD.clear();
		LCD.drawString("RESET ...", 0, 0);
		
		myRobot.headUp();
		myRobot.moveToPos(0, 0);
		myRobot.headDown();

		myRobot.chainMotor.stop();
		myRobot.wheelMotor.stop();
		myRobot.headMotor.stop();
	}

}