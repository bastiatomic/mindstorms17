package mindstorms17;

import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class Robot {
    Boolean headPos;
    double realPosX;
    double realPosY;
    RegulatedMotor chainMotor;
    RegulatedMotor wheelMotor;
    RegulatedMotor headMotor;
    EV3TouchSensor touchSensor;
    EV3ColorSensor colorSensor;

    public Robot(int a, int b, Boolean c,
        RegulatedMotor d, RegulatedMotor e, RegulatedMotor z, EV3TouchSensor f, EV3ColorSensor g) {
        this.realPosX = a;
        this.realPosY = a;
        this.headPos = c;
        this.chainMotor = d;
        this.wheelMotor = e;
        this.touchSensor = f;
        this.colorSensor = g;
        this.headMotor = z;
    } //TODO: kill unused variables

    void headUp() {
        if(headPos){
            headSwitch();
        }
    }

    void headDown() {
        if(!headPos){
            headSwitch();
        }
    }

    public void headSwitch() {
        headMotor.rotate(180);
        this.headPos = !this.headPos;
        Delay.msDelay(10);
    }

    void moveToPos(int x, int y){

        double newMoveX = this.realPosX - x;
        double newMoveY = this.realPosY - y;

        double rotationsNeededA = newMoveX / mindstorms17.Gear.chainGear();
        double rotationsNeededB = newMoveY / mindstorms17.Gear.wheelGear();

        this.realPosX -= newMoveX;
        this.realPosY -= newMoveY;

        double speedA = 100; // magic number
        double timeForLength = rotationsNeededA / speedA;
        double speedB = rotationsNeededB / timeForLength;

        if(timeForLength == 0) {speedB = 100;} // this affects if speedA = 0 
        //TODO: this call requires that the previous is a DivisionZeroErro
    
        this.chainMotor.synchronizeWith(new RegulatedMotor[] { this.wheelMotor });
        this.chainMotor.startSynchronization();
        this.chainMotor.setSpeed((int) speedA);
        this.wheelMotor.setSpeed((int) speedB);
        this.chainMotor.rotate((int) Math.round(rotationsNeededA)); // WARNING: conversion to int
        this.wheelMotor.rotate((int) Math.round(rotationsNeededB));
        this.chainMotor.endSynchronization();
        this.chainMotor.waitComplete();
        this.wheelMotor.waitComplete();
    }

    void driveToHome() {

        headUp();

        //move gearMotor
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

        //move wheelMotor
        Delay.msDelay(10);
        this.wheelMotor.setSpeed(50);
        SensorMode ambientSensorMode = this.colorSensor.getRedMode();
        float[] sample1 = new float[ambientSensorMode.sampleSize()];
        while (sample1[0] < 0.20) {
            ambientSensorMode.fetchSample(sample1, 0);
            this.wheelMotor.backward();
            LCD.refresh();
            LCD.clear();
            LCD.drawString("[RedMode] Intensity: " + sample1[0], 1, 1);
            Delay.msDelay(10);
        }
        this.touchSensor.close();
        this.colorSensor.close();
        
        headDown();
    }

}
