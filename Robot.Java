package com.mydomain;
import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.ButtonListener;



public class Robot {
	public enum CommandNames{
		 MOVEFORWARD, KICK, MOVEBACKWARD, CHANGEANGLE, ABORT, EXIT
		}
	

	
	
	static int angleConstant = 2; //constant, which is used to determine amount of degrees needed to turn the robot one angle. Depends on weight/wheels of robot.
	static int distConstant = 100; // constant, which is used to determine how much move is needed to cross one square.
	static int kickerAngle = 200;// depends on kicker, how much does kicker need to kick each time.
	static int kickerReturn = 0;// depends on the kicker, whether it needs to return back.
	
	List<Commands> commands = new ArrayList<Commands>(); 
	
	private int speed = 200;// default speed, can be changed in mid run;
// possibly useless 	private int angle; // current angle, default is set along with creation of robot (and placement of nxt). May give an override to prevent desyncs, but that would make opportunities for mistakes.
	public Robot()
	{
	  //Motor.A.setAcceleration(5000);
	  Motor.A.setSpeed(speed);
	  Motor.B.setSpeed(speed);
	}
	
	private void kick(int speed)
	{
	   Motor.C.setSpeed(speed);
	   Motor.C.rotate(kickerAngle);
	   Motor.C.rotate(kickerReturn,true);
	   
	}
	
	private void abort() //check if the robot needs to abandon all of its commands
	{
	   boolean isAbort= false; // assume it doesn't need to
	   for (Commands current: commands) // go through all current commands
	   {
		   if (current.commandName ==CommandNames.ABORT) // if you find it, then initiate abort
		   {
			   while(!(commands.remove(0).commandName==CommandNames.ABORT)); // test this, many doubts, but may have potential;
			   break;
		   }
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

		while(!Exit())
		{
			if (!commands.isEmpty())
			{
		    Commands curcommand = commands.remove(0);
			switch (curcommand.commandName)
			{
			case KICK:
				kick(curcommand.firstArg);
				break;
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
	Motor.B.rotate(distance*distConstant,true); 
	try 
	{
		int time = (int) (desiredSpeed*Math.abs(distance)*2.5);
		Thread.sleep(time);
		System.out.println(time);
	}
	catch (Exception e){}
	}
	
	private void turnAngle(int desiredSpeed, int toTurnAngle) // turn the angle
	{
		    setSpeed(desiredSpeed);
			Motor.A.rotate(angleConstant*toTurnAngle,true);
			Motor.B.rotate(-angleConstant*toTurnAngle);
	}

}
