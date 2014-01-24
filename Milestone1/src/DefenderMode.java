import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;

public class DefenderMode {
	//Work By:
	// Peter MacGregor : Motion
	//Aris Tsialos, Andrew Leith : Halting 
	public static void main(String[] args) {
		//flag for if any button is pressed to end the program
		Boolean flag = true;
		
		//2 sensors, sensor1 is on the right side and sensor2 on the left
		LightSensor sensor1 = new LightSensor(SensorPort.S1);
		LightSensor sensor2 = new LightSensor(SensorPort.S2);
		
		int circumference = 27000; // The circumference of the white area measured in motor rotations. EXPERIMENTAL CONSTANT.
		
		//TODO: The circumference is at a pretty good est.
		int rotations = 0; // Stores how many rotations the motors have made while moving forward.
		
		int speedFast = 100;
		int speedSlow = 50;
		Move.setSpeedLeft(speedFast);
		Move.setSpeedRight(speedFast);
		while (sensor1.getLightValue()<=40 & sensor2.getLightValue()<=40){
			Motor.A.rotate(180,true);
			Motor.B.rotate(180,true);
		}
		if (sensor1.getLightValue()>40){
			while (flag && ( rotations < circumference)) {
			    //keep moving while the sensor1 is on the white edge and sensor2 in on the green area
				Move.setSpeedLeft(speedFast);
				Move.setSpeedRight(speedFast);
			    while (sensor1.getLightValue()<=40)
			    {
					Motor.A.rotate(180,true);
					Motor.B.rotate(180,true);
					rotations +=1;
					LCD.drawInt(rotations, 0, 0);
					
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
			    while (sensor2.getLightValue()<=40)
			    {
					Motor.A.rotate(180,true);
					Motor.B.rotate(180,true);
					rotations +=1;
					LCD.drawInt(rotations, 0, 0);
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
