package mindstorms17;
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
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) throws InterruptedException, IOException {

		// SOA applied here
		// ATTENTION: the head is down at start;
		
		// init all objects
		Robot myRobot = new Robot(0,0,true,
				new EV3LargeRegulatedMotor(MotorPort.A), // chainMotor
				new EV3LargeRegulatedMotor(MotorPort.B), // wheelMotor
				new EV3LargeRegulatedMotor(MotorPort.C), // headMotor
				new EV3TouchSensor(SensorPort.S1), // touchSensor
				new EV3ColorSensor(SensorPort.S2)); // colorSensor

		
		LCD.clear();LCD.drawString("Img processing ...", 0, 0);
		BufferedImage img_CANNY = ImageIO.read(new File("src/graphics/apple_logo_CANNY.png"));

		//img.getCanny();

		LineFollower3 img = new LineFollower3(img_CANNY);


		img.getLocationList();
		/*
		//getting the image and processing it
		ImageService img1 = new ImageService();
		img1.load(ImageIO.read(new File("src/graphics/apple_logo.png")));
		img1.verify();
		img1.canny();
		img1.positionCreator();*/

		myRobot.headSwitch(); // headUp expected

		LCD.drawString("driving home", 0, 0);
		LCD.drawString("for christmas", 1, 1);
		myRobot.driveToHome(); // both speeds are very good //positive integer drive towards sensor

		//moving the robot along the paper
		//I'm using the LineFollower5 data and i expect clean data
		int a = 0;
		for (Position b : img.locations) {
			LCD.clear();
			LCD.drawString("[" + a + "/" + img.locations.size() + "]", 0, 1);
			LCD.drawString("Take a hike,", 0, 6);
			LCD.drawString("be curious", 1, 7);

			if(a == 100){break;} //debugging threshold

			if (b.headSwitch) {
				myRobot.headSwitch();
			}
			myRobot.moveToPos(b.x, b.y);

			a++;

			//Delay.msDelay(10);
		}

		//cleanup at the end of the printing
		LCD.clear();
		LCD.drawString("RESET ...", 0, 0);
		if (myRobot.headPos) { //if down, move up
			myRobot.headSwitch();
		}
		myRobot.move(0, 250);
		
		myRobot.headSwitch(); //move him down at the end

		myRobot.chainMotor.stop();
		myRobot.wheelMotor.stop();
		myRobot.headMotor.stop();
	}

}