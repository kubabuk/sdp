package com.mydomain;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class FirstMileStone {
	//Codes for 1st Milestone
	//By Roy
	public static void main(String[] args) {
		//flag for if any button is pressed to end the program
		Boolean flag = true;
		//2 sensors, sensor1 is on the right side and sensor2 on the left
		LightSensor sensor1 = new LightSensor(SensorPort.S1);
		LightSensor sensor2 = new LightSensor(SensorPort.S2);
		while (flag) {
			//display the sensor data on the LCD screen		
			LCD.drawInt(sensor1.getLightValue(), 4, 0, 0);
		    LCD.drawInt(sensor1.getNormalizedLightValue(), 4, 0, 1);
		    LCD.drawInt(sensor2.getLightValue(), 4, 0, 2);
		    LCD.drawInt(sensor2.getNormalizedLightValue(), 4, 0, 3);
		    //keep moving while the sensor1 is on the white edge and sensor2 in on the green area
		    do
		    {
		    	Move.forward();
		    }
		    
		    while (sensor1.getLightValue()>40&&sensor2.getLightValue()<40);
		    Move.stop();
		    //turn left while both of the sensors are on the white edge
		    do 
		    {
		    	Move.turnLeft();
		    }
		    while (sensor1.getLightValue()>40&&sensor2.getLightValue()>40);
		    Move.stop();
		    
		    //do 
		    //{
		    //	Move.turnRight();
		    //}
		    //while (sensor1.getLightValue()<50&&sensor2.getLightValue()<50);
		    //Move.stop();
		    
		    flag = ! Button.ESCAPE.isPressed();
	    
		}
	}

}
