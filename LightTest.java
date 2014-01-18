import lejos.nxt.*;

public class LightTest {
  public static void main(String[] args) throws Exception {
    LightSensor light = new LightSensor(SensorPort.S1);

    while (true) {
      LCD.drawInt(light.getLightValue(), 4, 0, 0);
      LCD.drawInt(light.getNormalizedLightValue(), 4, 0, 1);
      LCD.drawInt(SensorPort.S1.readRawValue(), 4, 0, 2);
      LCD.drawInt(SensorPort.S1.readValue(), 4, 0, 3);
      Motor.A.forward();
      Motor.B.forward();
      
      if (light.getLightValue() >= 47) {
    	  Motor.B.stop();
    	  Motor.A.stop();
    	  
    	  
      }
      
     
      
       
    }
  }
}
