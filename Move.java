package com.mydomain;

import lejos.nxt.Motor;

public class Move {
	//by Roy
	//Here, Motor A should always be on the right side of the robot
	//while Motor B on the left.
	
	public static void forward () {
		Motor.A.forward();
		Motor.B.forward();
		
	}
	
	public static void backward() {
		Motor.A.backward();
		Motor.B.backward();
	
	}
	
	public static void turnLeft(){
	//motorA goes forward while motorB stops	
		Motor.A.forward();
			
	}
	
	public static void turnRight(){
		Motor.B.forward();
		
	}
	
	
	
}
