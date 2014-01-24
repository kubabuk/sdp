import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;

public class TwoSensors {
	public static void main(String[] args) {
		//flag for if any button is pressed to end the program
		Boolean flag = true;
		
		//2 sensors, sensor1 is on the right side and sensor2 on the left
		LightSensor lightSensorLeft = new LightSensor(SensorPort.S1);
		LightSensor lightSensorRight = new LightSensor(SensorPort.S2);
		
		int speedFast = 200;
		int speedSlow = 50;
		Move.setSpeedLeft(250);
		Move.setSpeedRight(250);
		while (flag) {
		    if (lightSensorLeft.getLightValue() <= 40){
		    	if (lightSensorRight.getLightValue() <= 40){
		    		// both green
		    		while(lightSensorLeft.getLightValue() <= 40){
		    			Motor.A.rotate(180,true);
		    			Motor.B.rotate(150,true);
		    		}
		    	} else {
		    		// left green, right white
		    		while(lightSensorRight.getLightValue() > 40){
		    			Motor.A.rotate(-10,true);
		    			Motor.B.rotate(10,true);
		    		}
		    	}
		    } else {
		    	if (lightSensorRight.getLightValue() > 40){
		    		// both white
		    		while(lightSensorRight.getLightValue() > 40){
		    			Motor.A.rotate(-10,true);
		    			Motor.B.rotate(10,true);
		    		}
		    		
		    	} else {
		    		// left white, right green
		    		while(lightSensorLeft.getLightValue() > 40 & lightSensorRight.getLightValue() <=40){
		    			Motor.A.rotate(180,true);
		    			Motor.B.rotate(180,true);
		    		}
		    	}
		    }
		    
		    flag = ! Button.ESCAPE.isPressed();
	    
		}
	}
}
