package java_testing;

import java.util.ArrayList;


public class c {
	
	public static void main(String[] args) {
		
        double distanceOneDegreeMotorChainMotor = (121.0 / 360) * 12 / 36; // TODO: abstract into classes
        double distanceOneDegreeMotorWheelMotor = (135.7168 / 360) * 12 / 36; // mm distance of 1 degree

        double lengthA = 15;
        double lengthB = 0;

        ArrayList<int[]> mover = new ArrayList<>();
        mover.add(new int[]{-150,0});
        mover.add(new int[]{0,-200});
        mover.add(new int[]{150,0});
        mover.add(new int[]{0,200});

        ArrayList<double[]> exactPos = new ArrayList<>();
        ArrayList<int[]> realPos = new ArrayList<>();
        
        

        // double finalLength = Math.sqrt(Math.pow(lengthA, 2) + Math.pow(lengthB, 2));
        // // C

        for (int i = 0; i < mover.size(); i++) {

            lengthA = mover.get(i)[0]; lengthB = mover.get(i)[1];

            double rotationsNeededA = lengthA / distanceOneDegreeMotorChainMotor;
            double rotationsNeededB = lengthB / distanceOneDegreeMotorWheelMotor;
            
            exactPos.add( new double[]{rotationsNeededA, rotationsNeededB});
            
            realPos.add(new int[] {(int) rotationsNeededA, (int) rotationsNeededB});
           
            double speedA = 100; // magic number
            double timeForLength = rotationsNeededA / speedA;
            double speedB = rotationsNeededB / timeForLength;
            
        }

        printD(exactPos); print(realPos);
        
	}
    static void print(ArrayList<int[]> a){
        System.out.println("..........." + a.size());
        for (int i = 0; i < a.size(); i++) {
            System.out.println(a.get(i)[0]+", " + a.get(i)[1]);
        }

    }
    static void printD(ArrayList<double[]> a){
        System.out.println("..............." + a.size());
        for (int i = 0; i < a.size(); i++) {
            System.out.println(a.get(i)[0]+", " + a.get(i)[1]);
        }

    }

}
