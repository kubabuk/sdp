import lejos.nxt.Motor;

public class Robot {
	private int x,y; // current x and y, default is set along with creation of robot. May give an override to prevent desyncs, but that would make opportunities for mistakes.
	static int angleConstant = 2; //constant, which is used to determine amount of degrees needed to turn the robot one angle. Depends on weight/wheels of robot.
	static int distConstant = 100; // constant, which is used to determine how much move is needed to cross one square.
	private int speed = 200;// default speed, can be changed in mid run;
	private int angle; // current angle, default is set along with creation of robot (and placement of nxt). May give an override to prevent desyncs, but that would make opportunities for mistakes.
	public Robot(int startingX, int startingY, int startingAngle)
	{
	  x= startingX;
	  y= startingY;
	  angle=startingAngle;
	  Motor.A.setSpeed(speed);
	  Motor.B.setSpeed(speed);
	}
	public void setSpeed(int desiredSpeed)// set the current speed
	{
		  Motor.A.setSpeed(desiredSpeed);
		  Motor.B.setSpeed(desiredSpeed);
	}
	public int getX() //get current x of robot.
	{
		return x;
	}
	public int getY() // get current y of robot.
	{
		return y;
	}
	public int getAngle() // get current angle of robot.
	{
		return angle;
	}

	public void moveTo(int targetX,int targetY) //Move to a target in space (board will be split into x and y squares)
	{
	int deltaX = (targetX-x); 
    int deltaY = (targetY-y);
    int dist = (int) Math.sqrt((deltaX*deltaX)+(deltaY*deltaY))*distConstant; //find the difference between current robot position and target, convert it to distance needed to travel.
    int angleTo = (int)Math.round((Math.atan((deltaX*1.0)/(deltaY*1.0)))*(180/Math.PI)); // find the angle the robot needs to turn before being able to move.
	if (deltaX ==0 && deltaY<0)
	{
		angleTo = 180;
	}
    System.out.println(angleTo);
    turnAngle(angleTo-angle); // turn the angle.
	Motor.A.rotate(dist,true); // move forward, do not wait for it to finish, so move is simultaneous and does not turn a robot.
	Motor.B.rotate(dist); 
	x=targetX;
	y=targetY;
	}
	
	public void turnAngle(int toTurnAngle)
	{
			Motor.A.rotate(angleConstant*toTurnAngle,true);
			Motor.B.rotate(-angleConstant*toTurnAngle);

		if (angle+toTurnAngle>180)
			angle = angle +toTurnAngle-360;
		else if (angle+toTurnAngle<-179)
			angle=angle+toTurnAngle+360;
		else angle=angle+toTurnAngle;
	}

}
