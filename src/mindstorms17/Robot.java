package mindstorms17;

import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class Robot {
    String name;
    int[] current_pos;
    boolean head_position;
    RegulatedMotor chainMotor;
    RegulatedMotor wheelMotor;
    EV3TouchSensor touchSensor;
    EV3ColorSensor colorSensor;

    Robot(String a, int[] b, boolean c,
            RegulatedMotor d, RegulatedMotor e, EV3TouchSensor f, EV3ColorSensor g) {
        this.name = a;
        this.current_pos = b;
        this.head_position = c;
        this.chainMotor = d;
        this.wheelMotor = e;
        this.touchSensor = f;
        this.colorSensor = g;
    }

    void move(int x, int y) { // distance in mm;

        double distanceOneDegreeMotorChainMotor = (121.0 / 360) * 12 / 36; // TODO: abstract into classes
        double distanceOneDegreeMotorWheelMotor = (135.7168 / 360) * 12 / 36; // mm distance of 1 degree

        double lengthA = x - this.current_pos[0];
        double lengthB = y - this.current_pos[1];

        // double finalLength = Math.sqrt(Math.pow(lengthA, 2) + Math.pow(lengthB, 2));
        // // C

        double rotationsNeededA = lengthA / distanceOneDegreeMotorChainMotor;
        double rotationsNeededB = lengthB / distanceOneDegreeMotorWheelMotor;

        double speedA = 100; // magic number
        double timeForLength = rotationsNeededA / speedA;
        double speedB = rotationsNeededB / timeForLength;

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

    void driveToHome() {
        // what happens: first drive the head to touchSensor, THEN drive the paper to
        // the lightSensor
        SensorMode sensorMode = this.touchSensor.getTouchMode();
        RegulatedMotor m = this.chainMotor;
        float[] sample = new float[sensorMode.sampleSize()];
        sensorMode.fetchSample(sample, 0);
        m.setSpeed(100);
        while (sample[0] == 0) {
            m.forward();
            sensorMode.fetchSample(sample, 0);
        }
        m.stop();

        Delay.msDelay(2000);
        this.colorSensor.setFloodlight(false);
        LCD.drawString("Init", 2, 2);
        LCD.setAutoRefresh(false);
        SensorMode ambientSensorMode = this.colorSensor.getAmbientMode();
        float[] sample1 = new float[ambientSensorMode.sampleSize()];
        m.setSpeed(20);

        // get the current light intensity
        this.wheelMotor.setSpeed(100);
        float initValue = 0;
        for (int i = 0; i < 10; i++) {
            ambientSensorMode.fetchSample(sample1, 0);
            initValue += sample1[0];
            Delay.msDelay(10);
        }
        initValue = initValue / 10;
        float thresHold = (float) (initValue - 0.04); // -0.04 is a magic number

        while (initValue > thresHold) {
            ambientSensorMode.fetchSample(sample, 0);
            initValue = sample[0];
            this.wheelMotor.backward();
            LCD.refresh();
            LCD.clear();
            LCD.drawString("Intensity: " + sample[0], 1, 1);
            Delay.msDelay(1000);
        }
        // TODO: technical difference between Thread.sleep and Delay.msDelay
        // sensor.close();
    }

    void moveCm(int x, int y){

        x = x * 9; // 8.925619834710744
        y = y * 8; // 7.957747309102483

        this.chainMotor.synchronizeWith(new RegulatedMotor[] { this.wheelMotor });
        this.chainMotor.startSynchronization();
        this.chainMotor.setSpeed((int) 100); //TODO: how does speed affect the outcome. what is speed?
        this.wheelMotor.setSpeed((int) 100);
        this.chainMotor.rotate((int) x); // WARNING: conversion to int
        this.wheelMotor.rotate((int) y);
        // Delay.msDelay( (long) timeForLength*1000);
        this.chainMotor.endSynchronization();
        this.chainMotor.waitComplete();
        this.wheelMotor.waitComplete();
    }

}
