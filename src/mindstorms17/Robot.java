package mindstorms17;

import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

class Robot {
    double realPosX;
    double realPosY;
    Boolean headPos;
    RegulatedMotor chainMotor;
    RegulatedMotor wheelMotor;
    RegulatedMotor headMotor;
    EV3TouchSensor touchSensor;
    EV3ColorSensor colorSensor;
    double speedX;
    double speedY;

    Robot(int a, int b, Boolean c,
        RegulatedMotor d, RegulatedMotor e, RegulatedMotor z, EV3TouchSensor f, EV3ColorSensor g) {
        this.realPosX = a;
        this.realPosY = b;
        this.headPos = c;
        this.chainMotor = d;
        this.wheelMotor = e;
        this.touchSensor = f;
        this.colorSensor = g;
        this.headMotor = z;
    }

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

    void headSwitch() {
        headMotor.rotate(180);
        this.headPos = !this.headPos;
        Delay.msDelay(10);
    }

    void moveToPos(int x, int y){

        double newMovementX = this.realPosX - x;
        double newMovementY = this.realPosY - y;

        double rotationsNeededA = newMovementX / mindstorms17.Gear.chainGear();
        double rotationsNeededB = newMovementY / mindstorms17.Gear.wheelGear();

        this.realPosX -= newMovementX;
        this.realPosY -= newMovementY;

        this.speedX = 100; // magic number
        this.speedY = 100;
        double timeForLength = rotationsNeededA / this.speedX;

        this.speedY = rotationsNeededB / timeForLength;

        if(timeForLength == 0) {this.speedY = 100;}
       
        this.chainMotor.synchronizeWith(new RegulatedMotor[] { this.wheelMotor });
        this.chainMotor.startSynchronization();
        this.chainMotor.setSpeed((int) speedX);
        this.wheelMotor.setSpeed((int) speedY);
        this.chainMotor.rotate((int) Math.round(rotationsNeededA)); // WARNING: conversion to int
        this.wheelMotor.rotate((int) Math.round(rotationsNeededB));
        this.chainMotor.endSynchronization();
        this.chainMotor.waitComplete();
        this.wheelMotor.waitComplete();
    }

    void driveToHome() {

        headUp();

        //move chainMotor
        SensorMode sensorMode = this.touchSensor.getTouchMode();
        this.chainMotor.setSpeed(100);
        float[] sample_chain = new float[sensorMode.sampleSize()];
        sensorMode.fetchSample(sample_chain, 0);
        while (sample_chain[0] == 0) {
            this.chainMotor.forward();
            sensorMode.fetchSample(sample_chain, 0);
        }
        this.chainMotor.stop();
        Delay.msDelay(10);

        //move wheelMotor
        this.wheelMotor.setSpeed(50);
        SensorMode ambientSensorMode = this.colorSensor.getRedMode();
        float[] sample_wheel = new float[ambientSensorMode.sampleSize()];
        while (sample_wheel[0] < 0.20) {
            ambientSensorMode.fetchSample(sample_wheel, 0);
            this.wheelMotor.backward();
            LCD.clear();
            LCD.drawString("[RedMode] Intensity: " + sample_wheel[0], 1, 1);
            Delay.msDelay(10);
        }
        this.wheelMotor.stop();
        
        this.touchSensor.close();
        this.colorSensor.close();
        
        headDown();
    }

}
