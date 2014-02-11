import java.util.ArrayList;
import java.util.List;
import lejos.nxt.Motor;



public class Robot {
	public enum CommandNames{
		 MOVEFORWARD, KICK, MOVEBACKWARD, UPDATEANGLE, CHANGEANGLE, ABORT, EXIT
		}
	

	final double defAngleConst= 3.05;
	final double defDistConst= 10.2;
	final double atkAngleConst = 1.1;
	final double atkDistConst= 5.75;
	final int defKickerAngle=100;
	final int defReturnAngle = 110;
	final int atkKickerAngle=-180;
	final int atkReturnAngle=0;
	final int atkspeed=100;
	final int defspeed=900;
	final boolean isDefender;
	final double angleConstant; //constant, which is used to determine amount of degrees needed to turn the robot one angle. Depends on weight/wheels of robot.
	final double distConstant; // should be equal to 0.5 cm in real world // constant, which is used to determine how much move is needed to cross one square.
	final int kickerAngle;// depends on kicker, how much does kicker need to kick each time.
	final int kickerReturn;// depends on the kicker, whether it needs to return back.
	int curAngle=0;
	List<Commands> commands = new ArrayList<Commands>(); 
	
	private int speed = 200;// default speed, can be changed in mid run;

	public Robot(boolean isDefender)
	{
	  this.isDefender = isDefender;
	  if (isDefender)
	  {
		  angleConstant = defAngleConst;
		  distConstant = defDistConst;
		  kickerAngle=defKickerAngle;
		  kickerReturn=defReturnAngle;
		  speed = defspeed;
	  }
	  else 
	  {
		  angleConstant = atkAngleConst;
		  distConstant = atkDistConst;
		  kickerAngle=atkKickerAngle;
		  kickerReturn=atkReturnAngle;
		  speed=atkspeed;
	  }
	  Motor.A.setAcceleration(3000);
	  Motor.B.setAcceleration(3000);
	  Motor.C.setAcceleration(10000);
	  Motor.A.setSpeed(speed);
	  Motor.B.setSpeed(speed);
	  
	}
	
	private void kick(int speed)
	{
	   Motor.C.setSpeed(900);
	   Motor.C.rotate(-kickerAngle);
	   Motor.C.rotate(kickerReturn,true);
	   
	}
	
	private void abort() //check if the robot needs to abandon all of its commands
	{
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
			case MOVEFORWARD:
				moveDistance(curcommand.firstArg,curcommand.secondArg);
				break;
			case MOVEBACKWARD:
				moveDistance(curcommand.firstArg,-curcommand.secondArg);
				break;
			case CHANGEANGLE:
				turnAngle(curcommand.firstArg,curcommand.secondArg);
				break;
			case UPDATEANGLE:
				setAngle((curcommand.firstArg*360/256));
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
		  Motor.A.setSpeed(desiredSpeed);
		  Motor.B.setSpeed(desiredSpeed);
	}

	private void setAngle(int angle)
	{
		curAngle=angle;
	}


	private void moveDistance(int desiredSpeed,int distance) //Move forward the distance at desiredSpeed
	{
	//setSpeed(desiredSpeed);
	setSpeed(speed);
	Motor.A.rotate((int)(distance*distConstant),true); // move forward, do not wait for it to finish, so move is simultaneous and does not turn a robot.
	Motor.B.rotate((int)(distance*distConstant),false); 
	}
	
	private void turnAngle(int desiredSpeed, int toTurnAngle) // turn the angle
	{
		    //setSpeed(desiredSpeed);
		    setSpeed(75);
		    toTurnAngle= toTurnAngle*360/256;
		   
		    int tempAngle1=toTurnAngle-curAngle;
		    int tempAngle2=toTurnAngle-curAngle-360;
		    int tempAngle3=toTurnAngle-curAngle+360;
		    System.out.println(curAngle);
		    System.out.println(tempAngle1);
		    System.out.println(tempAngle2);
		    System.out.println(tempAngle3);
		    if (Math.abs(tempAngle1)<Math.abs(tempAngle2)&& Math.abs(tempAngle1)<Math.abs(tempAngle3))
		    {
				Motor.A.rotate((int)(angleConstant*tempAngle1),true);
				Motor.B.rotate((int)(-angleConstant*tempAngle1));	
		    }
		    else if (Math.abs(tempAngle2)<Math.abs(tempAngle3))
		    {
				Motor.A.rotate((int)(angleConstant*tempAngle2),true);
				Motor.B.rotate((int)(-angleConstant*tempAngle2));	
		    }
		    else 
		    {
				Motor.A.rotate((int)(angleConstant*tempAngle3),true);
				Motor.B.rotate((int)(-angleConstant*tempAngle3));	    	
		    }
			curAngle = toTurnAngle%360;
	}

}
