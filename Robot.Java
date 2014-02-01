import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Motor;




public class Robot {
	public enum CommandNames{
		 MOVEFORWARD, MOVEBACKWARD, CHANGEANGLE, ABORT, EXIT
		}
	

	
	
	static int angleConstant = 2; //constant, which is used to determine amount of degrees needed to turn the robot one angle. Depends on weight/wheels of robot.
	static int distConstant = 100; // constant, which is used to determine how much move is needed to cross one square.
	
	List<Commands> commands = new ArrayList<Commands>(); 
	private int speed = 200;// default speed, can be changed in mid run;
	private int angle; // current angle, default is set along with creation of robot (and placement of nxt). May give an override to prevent desyncs, but that would make opportunities for mistakes.
	public Robot()
	{
	  Motor.A.setSpeed(speed);
	  Motor.B.setSpeed(speed);
	}
	
	private void Abort() //check if the robot needs to abandon all of its commands
	{
	   boolean isAbort= false; // assume it doesn't need to
	   for (Commands current: commands) // go through all current commands
	   {
		   if (current.commandName ==CommandNames.ABORT) // if you find it, then initiate abort
		   {
			   isAbort=true;
			   break;
		   }
	   }
		
		
	   if (isAbort)
		   while(!commands.isEmpty()) // if you're aborting, dump everything you have in your command list.
		   {
			   commands.remove(0);
		   }
	   
	}
	public void addCommand(Commands toAdd) // user command input (through ENUMs)
	{
		commands.add(toAdd);
	}
	
	private boolean Exit() // check if there is an exit somewhere in the command list, close the robot (maybe change it to be first program)
	{

	   for (Commands current: commands)
	   {
		   if (current.commandName ==CommandNames.EXIT)
		   {
			   return true;
		   }
	   }
	   return false;
		
	}
	
	public void run() // main thread, which works on stuff. takes a command, analyses it and does it.
	{
		
		while(!Exit()&&!commands.isEmpty())
		{
			Abort();
			if (!commands.isEmpty())
			{
			Commands curcommand = commands.remove(0);
			switch (curcommand.commandName)
			{
			case MOVEFORWARD:
				moveDistance(curcommand.firstArg,curcommand.secondArg);
				break;
			case MOVEBACKWARD:
				moveDistance(curcommand.firstArg,-curcommand.secondArg);
				break;
			case CHANGEANGLE:
				turnAngle(curcommand.firstArg,curcommand.secondArg);
				break;
			}
			}
			
		}
		
		
		
	}
	
	private void setSpeed(int desiredSpeed)// set the current speed
	{
		  speed = desiredSpeed;
		  Motor.A.setSpeed(desiredSpeed);
		  Motor.B.setSpeed(desiredSpeed);
	}
	


	private void moveDistance(int desiredSpeed,int distance) //Move forward the distance at desiredSpeed
	{
	int tempspeed = speed;// in case we need to return to old speed;
	setSpeed(desiredSpeed);
	Motor.A.rotate(distance*distConstant,true); // move forward, do not wait for it to finish, so move is simultaneous and does not turn a robot.
	Motor.B.rotate(distance*distConstant); 
	}
	
	private void turnAngle(int desiredSpeed, int toTurnAngle) // turn the angle
	{
		    setSpeed(desiredSpeed);
			Motor.A.rotate(angleConstant*toTurnAngle,true);
			Motor.B.rotate(-angleConstant*toTurnAngle);
	}

}
