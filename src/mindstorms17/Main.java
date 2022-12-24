package mindstorms17;

import java.util.ArrayList;

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
		Robot myRobot = new Robot("ascende_superios", false,
				new EV3LargeRegulatedMotor(MotorPort.A), // chainMotor
				new EV3LargeRegulatedMotor(MotorPort.B), // wheelMotor
				new EV3TouchSensor(SensorPort.S1), // touchSensor
				new EV3ColorSensor(SensorPort.S2)); // colorSensor

		Delay.msDelay(1000); // generates FPS's amount of calls
		LCD.clear();
		LCD.drawString("Soo ...!", 0, 0);
		
		ArrayList<int[]> locations = new ArrayList<>();;
	
		
		
		myRobot.driveToHome(); // both speeds are very good
		
		myRobot.move(-150,0); // the final canvas size is 150x250 distance in mm
		Delay.msDelay(1000);
		myRobot.move(1,-200);
		Delay.msDelay(1000);
		myRobot.move(150,0); // the final canvas size is 150x250
		Delay.msDelay(1000);
		myRobot.move(1,200);
		
		for (int i = 0; i< locations.size()-1; i++) {
			myRobot.move(locations.get(i)[0], locations.get(i)[1]);
			LCD.clear();
			LCD.drawString("["+i+"] ("+locations.get(i)[0] +","+ locations.get(i)[1]+")", 0, 0);
		}

		/*
		myRobot.moveCm(10, 10);
		myRobot.moveCm(10, -10);
		myRobot.moveCm(-2, 0);
		myRobot.moveCm(-8, -8);
		myRobot.moveCm(-10, -10);
		myRobot.moveCm(10, 0);*/
		

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