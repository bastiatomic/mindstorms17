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
				new EV3LargeRegulatedMotor(MotorPort.C), // headMotor
				new EV3TouchSensor(SensorPort.S1), // touchSensor
				new EV3ColorSensor(SensorPort.S2)); // colorSensor

		Delay.msDelay(10);
		LCD.clear();
		LCD.drawString("Soo ...!", 0, 0);
		
		ArrayList<int[]> locations = new ArrayList<>();;
	
		myRobot.driveToHome(); // both speeds are very good //positive integer drive towards sensor

		//TODO: the head is down at start;
		myRobot.headUp();
		
		//TODO test me, i'm looking alright
		locations.add(new int[]{1, 1});
		locations.add(new int[]{8, 1});
		locations.add(new int[]{14, 9});
		locations.add(new int[]{20, 9});
		locations.add(new int[]{20, 10});
		locations.add(new int[]{20, 10}); 
		locations.add(new int[]{30, 14});
		locations.add(new int[]{36, 20});
		locations.add(new int[]{36, 21});
		locations.add(new int[]{36, 21});
		locations.add(new int[]{16, 26});
		locations.add(new int[]{17, 27});
		locations.add(new int[]{15, 27});
		locations.add(new int[]{16, 27});
		locations.add(new int[]{16, 28});
		locations.add(new int[]{16, 28});
		locations.add(new int[]{30, 28});
		locations.add(new int[]{30, 30});
		locations.add(new int[]{29, 31});
		locations.add(new int[]{27, 33});
		locations.add(new int[]{31, 31});
		locations.add(new int[]{33, 33});
		locations.add(new int[]{15, 32});
		locations.add(new int[]{16, 33});
		locations.add(new int[]{14, 33});
		locations.add(new int[]{15, 34});
		locations.add(new int[]{25, 33});
		locations.add(new int[]{26, 33});
		locations.add(new int[]{30, 33});
		locations.add(new int[]{32, 33});
		locations.add(new int[]{34, 33});
		locations.add(new int[]{35, 33});
		locations.add(new int[]{28, 34});
		locations.add(new int[]{30, 36});
		locations.add(new int[]{32, 34});
		locations.add(new int[]{31, 35});
		locations.add(new int[]{30, 37});
		// ADD ME TO MAIN.JAVA
		
		//I'm using the line follower data
		for (int i = 0; i< locations.size()-1; i++) {
			LCD.clear();
			LCD.drawString("["+i+"] ("+locations.get(i)[0] +","+ locations.get(i)[1]+")", 0, 0);
			myRobot.move(locations.get(i)[0], locations.get(i)[1]);
			myRobot.headSwitch();
			Delay.msDelay(50);
		}
		
		myRobot.chainMotor.stop(); myRobot.wheelMotor.stop();

	}


}