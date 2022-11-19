package mindstorms17;

import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class scratch2 {

    public static void main(String[] args) {

        moveHeadToPositionByTime(0, 0, 10, 10, 10);

    }
    public static void moveHeadToPositionByTime(int startX, int startY, int endX, int endY, int desiredTime){
        double outputOneDegreeMotorWheelMotor = (135.7168 / 360) * 12 / 36;
        double outputOneDegreeMotorChainMotor = (121.0 / 360) * 12 / 36;

        if(!moveHeadToPositionVerifier( startX,  startY,  endX,  endY,  desiredTime)){return;}

        double lengthA = endX - startX;
        double lengthB = endY - startY;

        double finalLength = Math.sqrt(Math.pow(lengthA, 2) + Math.pow(lengthB, 2)); // C

        double pythagorasLengthA = Math.sqrt(Math.pow(finalLength, 2) - Math.pow(lengthB, 2));
        double pythagorasLengthB = Math.sqrt(Math.pow(finalLength, 2) - Math.pow(lengthA, 2));

        //hypotenuse = C = sqrt(A^2 + B ^2)
        // A = sqrt(C^2 - B^2)
        // B = sqrt(C^2 - A^2)

        double rotationsNeededA = pythagorasLengthA / outputOneDegreeMotorWheelMotor;
        double rotationsNeededB = pythagorasLengthB / outputOneDegreeMotorChainMotor;

        double speedA = rotationsNeededA / desiredTime; // send us to .setspeed()
        double speedB = rotationsNeededB / desiredTime;

        System.out.println("Motor A rotates at " + speedA+".");
        System.out.println("Motor B rotates at " + speedB+".");
        System.out.println("For the duration of " + desiredTime + " sec."); 

        //TODO: Remove time variable entirely and the program figures out it speed itself to align with 'double to int conversion'
    }



    public static void moveHeadToPosition(int startX, int startY, int endX, int endY, int speed){
        
        if(moveHeadToPositionVerifier( startX,  startY,  endX,  endY,  speed)){

            double lengthA = endX - startX;
            double lengthB = endY - startY;

            double finalLength = Math.sqrt((Math.pow(lengthA, 2) + Math.pow(lengthB, 2)));

            double timeForLength = finalLength / speed; //how long do the motors have to move

            double speedA = lengthA / timeForLength;
            double speedB = lengthB / timeForLength;


            System.out.println(speedA + " " + speedB + " | time: " + timeForLength);
            /*What happens here: move both motors at each speed for 'timeForLength' seconds synchronized
            a.synchronizeWith(new RegulatedMotor[] { b });
            a.startSynchronization();
            a.setSpeed( (int)speedA); //WARNING: conversion to int
            b.setSpeed( (int) speedB);
            a.forward();
            b.forward();
            Delay.msDelay( (long) timeForLength*1000);
            a.endSynchronization();
            a.waitComplete();
            b.waitComplete();*/
        }

    }

    public static boolean moveHeadToPositionVerifier(int startX, int startY, int endX, int endY, int speed){

        int[] array = new int[]{startX, startY, endX, endY};
        for (int i: array){
            if( !(i >= 0 && i < 1500)){
                System.out.println("Numbers not in range error");
                return false;
            }
        }
        if( !(speed > 0 && speed <100)){
            System.out.println("speed not in range error");
            return false;
        }

        return true;

    }


    public static double hypothenuseLength(int x, int y, int startX, int startY) {
        double lengthA = x - startX;
        double lengthB = y - startY;

        return Math.sqrt((Math.pow(lengthA, 2) + Math.pow(lengthB, 2)));
    }

    public static double hypothenuseSpeed(double lengthHypothenuse, int wishedSpeed) {
        return lengthHypothenuse / wishedSpeed;
    }

    public static double speedA(int x, int startX, double neededTime) {
        int lengthX = x - startX;
        return lengthX / neededTime;
    }

    public static double speedB(int y, int startY, double neededTime) {
        int lengthY = y - startY;
        return lengthY / neededTime;
    }
}
