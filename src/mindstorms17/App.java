package mindstorms17;

import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class App {
	/*
	 * HOW TO USE ME
	 * 1. ensure that the head is DOWN. it will not work if you dont listen to me.
	 * 2. set the fileName as the image u like to print (150px by 150px in size)
	 * 3. execute App.java. Move forward if there are no errors
	 * 4. execute Main.java. It will run a LeJos application.
	 */

	public static void main(String[] args) throws Exception {

		ClearingService.clearingService(0, "");

		System.out.println("Hello , this is the printerservice from Jonas Schindehuette and Bastian Hesse.");
		System.out.println("Were there any changes to the printer? [y/n]");
		Scanner readScanner = new Scanner(System.in);
		String userInput = readScanner.nextLine();

		if (userInput.equals("y")) {
			System.out.println(
					"Changing settings isn't possible at this version! It'll implemented with further releases.");
			System.out.println("You can change the settings manualy in the Gear.java...");
			Thread.sleep(4500);
		}

		ClearingService.clearingService(1, "");

		System.out.println("Which object do you want do print?");
		System.out.println("     ->[1] Tycoon-logo from anno 2070");
		System.out.println("     ->[2] Apple-logo");
		System.out.println("     ->[3] Picture of drone");
		System.out.println("     ->[4] ironman-helmet");
		System.out.println("     ->[5] the nyan-cat");
		System.out.println("     ->[6] your self-uploaded file");
		String fileName = "";

		readScanner = new Scanner(System.in);
		int userInputNumber = readScanner.nextInt();
		ClearingService.clearingService(0, "");

		switch (userInputNumber) {
			case 1:
				fileName = "2070_tycoon_logo_150";
				break;
			case 2:
				fileName = "apple_logo";
				break;
			case 3:
				fileName = "drone";
				break;
			case 4:
				fileName = "tony_helmet";
				break;
			case 5:
				fileName = "nyan_cat";
				break;
			case 6:
				System.out.println("Please insert the name without filename extension...");
				readScanner = new Scanner(System.in);
				fileName = readScanner.nextLine();

				try {
					ImageIO.read(new File("src/graphics/" + fileName + ".png"));
				} catch (Exception e) {
					System.err.println("The entered file doesn't exist! Please start again...");
					System.exit(0);
				}

				break;

			default:
				System.err.println("You have entered a wrong number! Please start again...");
				System.exit(0);
				break;
		}

		ClearingService.clearingService(2, "The selected file will be transmittet to ImageService.java...");

		ImageService img1 = new ImageService(fileName);
		img1.load(ImageIO.read(new File("src/graphics/" + fileName + ".png")));

		System.out.println("ImageService.java is verifying if the selected file has the correct size...");
		img1.verify();

		ClearingService.clearingService(3,
				"Verifiying worked correctly. We're converting the selected file into a monopixelart...");
		img1.canny();
		img1.saveCanny();

		ClearingService.clearingService(6,
				"The transforming was successfull. We're starting searching for connected pixels...");

		img1.positionCreator();

		ClearingService.clearingService(9, "The connected pixels were found. We're start the export...");
		img1.exportPositions();

		ClearingService.clearingService(10,
				"We have " + img1.positions.size() + " Postions found. A print will take about "
						+ Math.round(img1.positions.size() / 120) + " minutes...");
		System.out.println("Do you want to start the print?");

		System.out.println("You may now start the Main.main() function to start printing.");

	}

}
