package mindstorms17;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws InterruptedException, IOException {

		
		
		// CHAPTER 1: init robot object
		Robot myRobot = new Robot(0,0,true,
				new EV3LargeRegulatedMotor(MotorPort.A), // chainMotor
				new EV3LargeRegulatedMotor(MotorPort.B), // wheelMotor
				new EV3LargeRegulatedMotor(MotorPort.C), // headMotor
				new EV3TouchSensor(SensorPort.S1), // touchSensor
				new EV3ColorSensor(SensorPort.S2)); // colorSensor

		//CHAPTER 2: getting the image and processing it
		LCD.clear();LCD.drawString("Img processing ...", 0, 0);
		ImageService img1 = new ImageService();
		img1.positions = TMP_Positions.write();

		//CHAPTER 3: init start drawing
		
		LCD.drawString("driving home", 0, 0);
		LCD.drawString("for christmas", 1, 1);
		myRobot.driveToHome(myRobot.headPos); // both speeds are very good //positive integer drive towards sensor
		//CHAPTER 4: moving & printing to all positions
		int a = 0;
		for (Position b : img1.positions) {
			//LCD.clear();
			LCD.drawString("[" + a + "/" + img1.positions.size() + "]", 0, 1);
			LCD.drawString("real: "+ myRobot.realPosX + ", " + myRobot.realPosY, 0, 2);
			LCD.drawString("thres: "+myRobot.thresholdX + ", " + myRobot.thresholdY, 0, 3);
			LCD.drawString("Take a hike,", 0, 6);
			LCD.drawString("be curious", 1, 7);

			if (b.headSwitch) {
				myRobot.headSwitch();
			}
			myRobot.moveToPos(b.x, b.y);

			a++;

			//Delay.msDelay(10);
		}

		//CHAPTER 5: cleanup at the end of the printing
		LCD.clear();
		LCD.drawString("RESET ...", 0, 0);
		//TODO: kill me because the head is always up at the end
		if (myRobot.headPos) { //if down, move up
			myRobot.headSwitch();
		}

		//TODO: maybe calculate required Y movement?
		myRobot.move(0, 250);
		
		myRobot.headDown(); //move him down at the end

		myRobot.chainMotor.stop();
		myRobot.wheelMotor.stop();
		myRobot.headMotor.stop();
	}

}