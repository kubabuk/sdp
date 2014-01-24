import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;

public class TwoSensors {
	//Codes for 1st Milestone
	//By Roy
	
	public static void main(String[] args) {
		//flag for if any button is pressed to end the program
		Boolean flag = true;
		
		//2 sensors, sensor1 is on the right side and sensor2 on the left
		LightSensor sensor1 = new LightSensor(SensorPort.S1);
		LightSensor sensor2 = new LightSensor(SensorPort.S2);
		
		int speedFast = 200;
		int speedSlow = 50;
		Move.setSpeedLeft(speedFast);
		Move.setSpeedRight(speedFast);
		while (sensor1.getLightValue()<=40 & sensor2.getLightValue()<=40){
			Motor.A.rotate(180,true);
			Motor.B.rotate(180,true);
		}
		if (sensor1.getLightValue()>40){
			while (flag) {
			    //keep moving while the sensor1 is on the white edge and sensor2 in on the green area
				Move.setSpeedLeft(speedFast);
				Move.setSpeedRight(speedFast);
			    while (sensor1.getLightValue()<=40)
			    {
					Motor.A.rotate(180,true);
					Motor.B.rotate(180,true);
			    }
			    //turn left while the sensor is` on the white edge
				Move.setSpeedLeft(speedSlow);
				Move.setSpeedRight(speedSlow);
			    while (sensor1.getLightValue()>40)
			    {
			    	Motor.A.rotate(10,true);
			    	Motor.B.rotate(-10,true);
			    }
			    
			    flag = ! Button.ESCAPE.isPressed();
		    
			}	
		} else {
			while (flag) {
			    //keep moving while the sensor1 is on the white edge and sensor2 in on the green area
				Move.setSpeedLeft(speedFast);
				Move.setSpeedRight(speedFast);
			    while (sensor2.getLightValue()<=40)
			    {
					Motor.A.rotate(180,true);
					Motor.B.rotate(180,true);
			    }
			    //turn left while the sensor is` on the white edge
				Move.setSpeedLeft(speedSlow);
				Move.setSpeedRight(speedSlow);
			    while (sensor2.getLightValue()>40)
			    {
			    	Motor.A.rotate(-10,true);
			    	Motor.B.rotate(10,true);
			    }
			    
			    flag = ! Button.ESCAPE.isPressed();
		    
			}	
		}
	}

}
