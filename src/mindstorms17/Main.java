package mindstorms17;

import java.util.ArrayList;

import java_testing.Position;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Delay;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.awt.Color;
import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) throws InterruptedException, IOException {

		ArrayList<Position> locations = new ArrayList<>();
		// SOA applied here
		// ATTENTION: the head is down at start;

		BufferedImage img_CANNY = ImageIO.read(new File("src/graphics/apple_logo_CANNY.png"));
    

		//img.getCanny();

		LineFollower3 img = new LineFollower3(img_CANNY);


		img.getLocationList();
		System.out.println(img.locations.size());

		PrintWriter f0 = new PrintWriter(new FileWriter("output.txt"));

		//fix stuff
		for(Position a: img.locations)
		{
			String str = String.valueOf(a.headSwitch);
			f0.println("["+a.x + ", " + a.y + ", " + str.substring(0, 1).toUpperCase() + str.substring(1) + "], ");
			
		}
		f0.close();
		System.out.println(img.locations.size());
		System.exit(0); 

		locations = locationsRawList.addElements();
		
		// init all objects
		Robot myRobot = new Robot(0,0,"UP",
				new EV3LargeRegulatedMotor(MotorPort.A), // chainMotor
				new EV3LargeRegulatedMotor(MotorPort.B), // wheelMotor
				new EV3LargeRegulatedMotor(MotorPort.C), // headMotor
				new EV3TouchSensor(SensorPort.S1), // touchSensor
				new EV3ColorSensor(SensorPort.S2)); // colorSensor

		LCD.clear();
		LCD.drawString("Soo ...!", 0, 0);

		
		boolean isUp = false;

		/*
		 * imageService is not a 'part' of Robot.java SOA
		 * imageService.load("file");
		 * imageService.scale();
		 * imageService.monoColor();
		 * imageService.travelerService();
		 * myRobot.locations = imageService.locations;
		 */

		myRobot.headSwitch(); // headUp expected

		myRobot.driveToHome(); // both speeds are very good //positive integer drive towards sensor

		// TODO test me, i'm looking alright
		// TRUE = move head down
		

		// I'm using the TravelerService data and i expect clean data
		for (int i = 0; i < locations.size(); i++) {
			LCD.clear();
			LCD.drawString("[" + i + "/" + locations.size() + "]", 0, 1);
			LCD.drawString("Take a hike,", 0, 6);
			LCD.drawString("be curious", 1, 7);

			if(i == 100){break;}

			myRobot.moveToPos(locations.get(i).x, locations.get(i).y);

			if (locations.get(i).headSwitch) {
				myRobot.headSwitch();
				isUp = !isUp;
			}

			Delay.msDelay(10);
		}
		if (isUp) {
			myRobot.headSwitch();
		}

		// reset me:
		LCD.clear();
		LCD.drawString("RESET ...", 0, 0);
		myRobot.move(0, 250);
		
		
		myRobot.headSwitch();
		

		myRobot.chainMotor.stop();
		myRobot.wheelMotor.stop();
		myRobot.headMotor.stop();
	}

}