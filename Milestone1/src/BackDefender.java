
import lejos.nxt.Button;

import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;

public class BackDefender {
	//Work By:
	// Peter MacGregor : Motion
	//Aris Tsialos, Andrew Leith : Halting 
	public static void main(String[] args) {
		//flag for if any button is pressed to end the program
		Boolean flag = true;
		boolean f = false;
		boolean onWhite = false;
		
		//2 sensors, sensor1 is on the right side and sensor2 on the left
		LightSensor sensor1 = new LightSensor(SensorPort.S1);
		LightSensor sensor2 = new LightSensor(SensorPort.S2);
		
		int circumference = 23500; // The circumference of the white area measured in motor rotations. EXPERIMENTAL CONSTANT.
		
		//TODO: The circumference is at a pretty good est.
		int rotations = 0; // Stores how many rotations the motors have made while moving forward.
		
		int speedFast = 100;
		int speedSlow = 50;
		Move.setSpeedLeft(speedFast);
		Move.setSpeedRight(speedFast);
		
		
		if (sensor1.getLightValue()>=40 && sensor2.getLightValue()<=40 || sensor2.getLightValue()>=40 && sensor1.getLightValue()<=40) {
			onWhite = true;
		}
		
		
		
		while (sensor1.getLightValue()<=40 && sensor2.getLightValue()<=40){
			Motor.A.rotate(-180,true);
			Motor.B.rotate(-180,true);
		}
		if (sensor1.getLightValue()>40){
			while (flag && ( rotations < circumference)) {
			    //keep moving while the sensor1 is on the white edge and sensor2 in on the green area
				Move.setSpeedLeft(speedFast);
				Move.setSpeedRight(speedFast);
				
				while (sensor2.getLightValue()<=40 && sensor1.getLightValue() >=40 && onWhite) {
					//Motor.A.rotate(-180,true);
					while (sensor1.getLightValue() > 40)
						Motor.B.rotate(-180,true);
					while (sensor1.getLightValue() <=40) {
						Motor.A.rotate(-90,true);
						Motor.B.rotate(90,true);
						f = true;
					}
					if (f) {
						onWhite = false;
						break;
					}
				}
			    while (sensor1.getLightValue()<=40)
			    {
					Motor.A.rotate(-180,true);
					Motor.B.rotate(-180,true);
					rotations +=1;
					LCD.drawInt(rotations, 0, 0);
					
					if (rotations > circumference)
						break;
					
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
			while (flag && ( rotations < circumference)) {
			    //keep moving while the sensor1 is on the white edge and sensor2 in on the green area
				Move.setSpeedLeft(speedFast);
				Move.setSpeedRight(speedFast);
				
				while (sensor1.getLightValue()<=40 && sensor2.getLightValue() >=40 && onWhite ) {
					while (sensor2.getLightValue() > 40)
						Motor.A.rotate(-180,true); 
					//Motor.B.rotate(-180,true);
					while (sensor2.getLightValue() <=40) {
						Motor.A.rotate(90,true);
						Motor.B.rotate(-90,true);
						f = true;
					}
					if (f) {
						onWhite = false;
						break;
					}
				}
				
			    while (sensor2.getLightValue()<=40)
			    {
					Motor.A.rotate(-180,true);
					Motor.B.rotate(-180,true);
					rotations +=1;
					LCD.drawInt(rotations, 0, 0);
					
					if (rotations > circumference)
						break;
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

