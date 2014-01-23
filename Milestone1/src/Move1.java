import lejos.navigation.Pilot;
import lejos.nxt.Motor;

public class Move1 {
	Pilot pilot = new Pilot(2.1f,4.4f,Motor.A, Motor.C,true);
	   pilot.setSpeed(720);// 2 RPM
	        pilot.travel(12);
	        pilot.rotate(-90);
	        pilot.travel(-12,true);
	        while(pilot.isMoving())Thread.yield();
	        pilot.rotate(-90);
	        pilot.rotateTo(270);
	        pilot.steer(-50,180,true);
	        while(pilot.isMoving())Thread.yield();
	        pilot.steer(100);
	        try{Thread.sleep(1000);}
	   catch(InterruptedException e){}
	        pilot.stop();
}
