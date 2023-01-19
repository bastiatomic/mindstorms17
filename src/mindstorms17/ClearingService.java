package mindstorms17;

public class ClearingService {

    public static void clearingService(int progress, String flag) {

        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

            } else {
                throw new Exception("This printerservice only works with windows! Please start again...");
            }

            if (progress > 0) {

                int i;

                System.out.println("Your progress:");
                System.out.print("[");
                for (i = 0; i < progress * 5; i++) {
                    System.out.print("*");
                    Thread.sleep(75);
                }
                for (; i < 50; i++) {
                    System.out.print("-");
                    Thread.sleep(25);
                }
                System.out.print("]");
                System.out.println("");
                System.out.println("");

                System.out.println(flag);
            }

            if (progress > 1) {
                Thread.sleep(4000);
            }

        } catch (Exception e) {
            System.out.println("Clearing of console doesn't possible...");
        }

    }
}
