package mindstorms17;

public class scratch4 {

    public static void main(String[] args) throws InterruptedException {

        int speedX = 30; int speedY = 30; int speedFactor = 100;
        int radius = 2;
        double magicTranslator = 1.121626446280992;

        for (int angle = 1; angle <= 360; angle++) {

			speedX += (int) (radius * Math.cos(angle * 3.141 / 180));

			speedY += (int) ((radius * Math.sin(angle * 3.141 / 180)));

            System.out.println(angle + " : "+ speedX + " | " + speedY);
			Thread.sleep(0);
		}

    }
    
}
