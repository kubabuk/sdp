package com.mydomain;

import java.util.ArrayList;
import java.util.List;
import lejos.robotics.navigation.OmniPilot;
import com.mydomain.Commands.CommandNames;

import lejos.nxt.Motor;

public class ControllerHolo {



	
	final double angleConstant=1; //constant, which is used to determine amount of degrees needed to turn the robot one angle. Depends on weight/wheels of robot.
	final double distConstant=1; // should be equal to 0.5 cm in real world // constant, which is used to determine how much move is needed to cross one square.
	final int kickerAngle=0;// depends on kicker, how much does kicker need to kick each time.
	final int kickerReturn=0;// depends on the kicker, whether it needs to return back.
	int curAngle=0;
	OmniPilot robot = new OmniPilot((float)4,(float)6.5,Motor.A,Motor.B,Motor.C,false,false);
	List<Commands> commands = new ArrayList<Commands>(); 
	
	private int speed = 300;// default speed, can be changed in mid run;

	public ControllerHolo()
	{

	  robot.setAcceleration(2500);
	}
	
	private void kick(int speed)
	{
		
	   Motor.C.setSpeed(900);
	   Motor.C.rotate(-kickerAngle);
	   Motor.C.rotate(kickerReturn,true);
	   
	}
	
	public void abort() //check if the robot needs to abandon all of its commands
	{
	   for (Commands current: commands) // go through all current commands
	   {
		   if (current.commandName ==CommandNames.ABORT) // if you find it, then initiate abort
		   {
			   while(true)
			   {
				   Commands temp = commands.remove(0);
				   System.out.println(temp.commandName);
				   if (temp.commandName==CommandNames.ABORT)
					   break;
			   }
 // test this, many doubts, but may have potential;
			  
		   }
	   }

	   
	}
	public void addCommand(Commands toAdd) // user command input (through ENUMs)
	{
		synchronized(this) 
		{
		commands.add(toAdd);
		}
	}
	
	private boolean Exit() // check if there is an exit somewhere in the command list, close the robot (maybe change it to be first program)
	{
	   synchronized(this) {
	   for (Commands current: commands)
	   {
		   if (current.commandName ==CommandNames.EXIT)
		   {
			   return true;
		   }
	   }
	   return false;
	   }
	}
	
	

	
	public void run() // main thread, which works on stuff. takes a command, analyses it and does it.
	{
		boolean isExit=false;
		while(!isExit)
		{
			Commands curcommand = null;
			synchronized(this) {
			if (!commands.isEmpty())
			curcommand = commands.remove(0);
			}
			if (curcommand!=null){
			switch (curcommand.commandName)
			{
			case ABORT:
				abort();
				break;
			case KICK:
				kick(curcommand.firstArg);
				break;
			case MOVE:
				moveDistance(curcommand.firstArg,curcommand.secondArg,curcommand.thirdArg);
				break;
			case TURN:
				turnAngle(curcommand.firstArg,curcommand.secondArg);
				break;
			case EXIT:
				isExit=true;
				break;
			}
			}		
		}	
	}
	
	private void setSpeed(int desiredSpeed)// set the current speed
	{
		 robot.setTravelSpeed(desiredSpeed);
		  // more motors needed
	}



	private void moveDistance(int desiredSpeed,int angle, int distance) //Move forward the distance at desiredSpeed
	{
		setSpeed(desiredSpeed);
		System.out.println(distance);
		robot.travel((distance/1.5), angle);
	}
	
	private void turnAngle(int desiredSpeed, int turnAngle) // turn the angle
	{
	        turnAngle=turnAngle*360/255-180;// gives a range of -180 and 180 from bluetooth 0 to 255.
		 setSpeed(desiredSpeed);
		robot.rotate(turnAngle*2.25);	  
	}

}
