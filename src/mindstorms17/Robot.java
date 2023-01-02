package mindstorms17;

import java.util.ArrayList;

import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class Robot {
    String headPos;
    int realPosX;
    int realPosY;
    double exactPosX;
    double exactPosY;
    double thresholdX;
    double thresholdY;
    RegulatedMotor chainMotor;
    RegulatedMotor wheelMotor;
    RegulatedMotor headMotor;
    EV3TouchSensor touchSensor;
    EV3ColorSensor colorSensor;

    Robot(int a, int b, String c,
            RegulatedMotor d, RegulatedMotor e, RegulatedMotor z, EV3TouchSensor f, EV3ColorSensor g) {
        this.exactPosX = a;
        this.exactPosY = a;
        this.realPosX = a;
        this.realPosY = a;
        this.headPos = c;
        this.chainMotor = d;
        this.wheelMotor = e;
        this.touchSensor = f;
        this.colorSensor = g;
        this.headMotor = z;
        this.thresholdX = 0;
        this.thresholdY = 0;
    }

    void headUp() {
        headMotor.rotate(180);
        Delay.msDelay(10);
    }

    void headSwitch() {
        headMotor.rotate(180);
        Delay.msDelay(10);
    }

    void move(int x, int y) { // distance in mm;
        // WHATIS: I move the motors in two directions simultanesouly while respecting
        // the offset it will generate

        double rotationsNeededA = x / mindstorms17.Gear.chainGear();
        double rotationsNeededB = y / mindstorms17.Gear.wheelGear();

        // threshold start
        this.thresholdX += (rotationsNeededA - (int) rotationsNeededA);
        this.thresholdY += (rotationsNeededB - (int) rotationsNeededB);

        // adding threshold (zero or one)
        rotationsNeededA += (int) thresholdX;
        rotationsNeededB += (int) thresholdY;

        // removing excesshreshold (1+)
        thresholdX -= (int) thresholdX;
        thresholdY -= (int) thresholdY;

        double speedA = 100; // magic number
        double timeForLength = rotationsNeededA / speedA;
        double speedB = rotationsNeededB / timeForLength; // TODO: this should create the same speed, maybe magic number
        
        if(timeForLength == 0) {speedB = 100;} // this affects if speedA = 0   
    
        /*
         * What happens here: move both motors at each speed for 'timeForLength' seconds
         * synchronized
         */

        this.chainMotor.synchronizeWith(new RegulatedMotor[] { this.wheelMotor });
        this.chainMotor.startSynchronization();
        this.chainMotor.setSpeed((int) speedA);
        this.wheelMotor.setSpeed((int) speedB);
        this.chainMotor.rotate((int) rotationsNeededA); // WARNING: conversion to int
        this.wheelMotor.rotate((int) rotationsNeededB);
        // Delay.msDelay( (long) timeForLength*1000);
        this.chainMotor.endSynchronization();
        this.chainMotor.waitComplete();
        this.wheelMotor.waitComplete();
    }
    void moveToPos(int x, int y) { // distance in mm;
        // WHATIS: I move the motors in two directions simultanesouly while respecting
        // the offset it will generate

        double rotationsNeededA = this.realPosX - x / mindstorms17.Gear.chainGear();
        double rotationsNeededB = this.realPosY - y / mindstorms17.Gear.wheelGear();

        // threshold start
        this.thresholdX += (rotationsNeededA - (int) rotationsNeededA);
        this.thresholdY += (rotationsNeededB - (int) rotationsNeededB);

        // adding threshold (zero or one)
        rotationsNeededA += (int) thresholdX;
        rotationsNeededB += (int) thresholdY;

        // removing excesshreshold (1+)
        thresholdX -= (int) thresholdX;
        thresholdY -= (int) thresholdY;

        double speedA = 100; // magic number
        double timeForLength = rotationsNeededA / speedA;
        double speedB = rotationsNeededB / timeForLength; // TODO: this should create the same speed, maybe magic number
        
        if(timeForLength == 0) {speedB = 100;} // this affects if speedA = 0   
    
        /*
         * What happens here: move both motors at each speed for 'timeForLength' seconds
         * synchronized
         */
        this.realPosX -= rotationsNeededA;
        this.realPosY -= rotationsNeededB;

        this.chainMotor.synchronizeWith(new RegulatedMotor[] { this.wheelMotor });
        this.chainMotor.startSynchronization();
        this.chainMotor.setSpeed((int) speedA);
        this.wheelMotor.setSpeed((int) speedB);
        this.chainMotor.rotate((int) rotationsNeededA); // WARNING: conversion to int
        this.wheelMotor.rotate((int) rotationsNeededB);
        // Delay.msDelay( (long) timeForLength*1000);
        this.chainMotor.endSynchronization();
        this.chainMotor.waitComplete();
        this.wheelMotor.waitComplete();
    }

    void driveToHome() {
        // what happens: first drive the head to touchSensor, THEN drive the paper to
        // the lightSensor
        SensorMode sensorMode = this.touchSensor.getTouchMode();
        RegulatedMotor m = this.chainMotor;
        m.setSpeed(20);
        float[] sample = new float[sensorMode.sampleSize()];
        sensorMode.fetchSample(sample, 0);
        m.setSpeed(100);
        while (sample[0] == 0) {
            m.forward();
            sensorMode.fetchSample(sample, 0);
        }
        m.stop();

        Delay.msDelay(2000);
        this.wheelMotor.setSpeed(50);
        SensorMode ambientSensorMode = this.colorSensor.getRedMode();
        float[] sample1 = new float[ambientSensorMode.sampleSize()];
        while (sample1[0] < 0.20) {
            ambientSensorMode.fetchSample(sample1, 0);
            this.wheelMotor.backward();
            LCD.refresh();
            LCD.clear();
            LCD.drawString("Intensity: " + sample1[0], 1, 1);
            Delay.msDelay(100);
        }
        // TODO: technical difference between Thread.sleep and Delay.msDelay
        // sensor.close();
    }

    void moveCm(int x, int y) {

        x = x * 9; // 8.925619834710744
        y = y * 8; // 7.957747309102483

        this.chainMotor.synchronizeWith(new RegulatedMotor[] { this.wheelMotor });
        this.chainMotor.startSynchronization();
        this.chainMotor.setSpeed((int) 100); // TODO: how does speed affect the outcome. what is speed?
        this.wheelMotor.setSpeed((int) 100);
        this.chainMotor.rotate((int) x); // WARNING: conversion to int
        this.wheelMotor.rotate((int) y);
        // Delay.msDelay( (long) timeForLength*1000);
        this.chainMotor.endSynchronization();
        this.chainMotor.waitComplete();
        this.wheelMotor.waitComplete();
    }

    void printIntArray(ArrayList<int[]> a) {

        for (int i = 0; i < a.size() - 1; i++) {

            move(
                    a.get(i + 1)[0] - a.get(i)[0],
                    a.get(i + 1)[1] - a.get(i)[1]);

        }

    }

}
