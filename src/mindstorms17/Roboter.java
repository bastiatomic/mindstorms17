package mindstorms17;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;

public class Roboter {
    String name;
    int[] current_pos;
    boolean head_position;
    RegulatedMotor chainMotor;
    RegulatedMotor wheelMotor;
    EV3TouchSensor touchSensor;
    EV3ColorSensor colorSensor;


    Roboter(String a, int[] b, boolean c,
            RegulatedMotor d, RegulatedMotor e, EV3TouchSensor f, EV3ColorSensor g){
        this.name = a; this.current_pos = b; this.head_position = c;
        this.chainMotor = d; this.wheelMotor = e; this.touchSensor = f; this.colorSensor = g;
    }


    void move(int x, int y){

        double outputOneDegreeMotorWheelMotor = (135.7168 / 360) * 12 / 36; //cm distance of 1 degree
        double outputOneDegreeMotorChainMotor = (121.0 / 360) * 12 / 36;
        
        double lengthA = x - this.current_pos[0];
        double lengthB = y - this.current_pos[1];

        //double finalLength = Math.sqrt(Math.pow(lengthA, 2) + Math.pow(lengthB, 2)); // C

        double rotationsNeededA = lengthA / outputOneDegreeMotorWheelMotor;
        double rotationsNeededB = lengthB / outputOneDegreeMotorChainMotor;

        /*
        double speedA = rotationsNeededA / desiredTime;
        double speedB = rotationsNeededB / desiredTime;

        double oldSpeedA = speedA;
        double oldSpeedB = speedB;

        speedA = 10; // magic number WIP // send us to .setspeed()
        speedB = oldSpeedB * speedA/oldSpeedA;*/

        /*What happens here: move both motors at each speed for 'timeForLength' seconds synchronized*/
        this.chainMotor.synchronizeWith(new RegulatedMotor[] { this.wheelMotor });
        this.chainMotor.startSynchronization();
        this.chainMotor.rotate( (int) rotationsNeededA); //WARNING: conversion to int
        this.wheelMotor.rotate( (int) rotationsNeededB);
        //Delay.msDelay( (long) timeForLength*1000);
        this.chainMotor.endSynchronization();
        this.chainMotor.waitComplete(); this.wheelMotor.waitComplete();
    }

    void driveToHome(){
        //what happens: first drive the head to touchSensor, THEN drive the paper to the lightSensor
		SensorMode sensorMode = this.touchSensor.getTouchMode();
        RegulatedMotor m = this.chainMotor;
		float[] sample = new float[sensorMode.sampleSize()];
		sensorMode.fetchSample(sample, 0);
		m.setSpeed(20);
		while (sample[0] == 0) {
			m.forward();
			sensorMode.fetchSample(sample, 0);
		}
		m.stop();

        //TODO: implement method to check the relative light change (get base value, calculate offset (-0.4) and return true if met)

    }

}
