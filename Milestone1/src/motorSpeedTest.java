import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.LCD;
import lejos.util.Delay;

public class motorSpeedTest {
	public static void main(String[] args){
		int speed=50;
		Move.setSpeedRight(speed);
		Move.setSpeedLeft(speed);
		Motor.A.rotate(720,true);
		Motor.B.rotate(720,true);
		
		for (int i = 0 ; i < 8 ; i++){
			Delay.msDelay(200);
			LCD.drawInt(Motor.A.getTachoCount(), 0,i);
			LCD.drawInt(Motor.B.getTachoCount(), 6,i);
		}
		while(Motor.A.isMoving()|| Motor.B.isMoving()||Motor.C.isMoving());
		Button.waitForAnyPress();
	}
}
