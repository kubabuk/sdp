import lejos.nxt.Motor;

public class Move {
        //by Roy
        //Here, Motor A should always be on the right side of the robot
        //while Motor B on the left.
	
		public static void setSpeedRight(int speed) {
				Motor.A.setSpeed(speed);
		}
		
		public static void setSpeedLeft(int speed) {
				Motor.B.setSpeed(speed);
		}
        
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
        
        public static void stop() {
        		Motor.A.stop();
        		Motor.B.stop();
        }
        
        
        
}