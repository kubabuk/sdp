package test;
import lejos.nxt.I2CPort;
import lejos.nxt.I2CSensor;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.LCD;
import java.util.ArrayList;
import java.util.List;
import lejos.robotics.navigation.OmniPilot;

import lejos.nxt.Motor;

public class ControllerHolo {

		public static I2CSensor MOTORMUX;
		private final byte  kickSpeed = (byte) 255;
		private final byte forward = (byte)1;
		private final byte backward = (byte)2;
		private final byte off = (byte)0;
		private final int kickerDirection = 0x01;
		private final int kickerSpeed = 0x02;
		private volatile boolean isKicking = false;
		private final int mainKickTime = 600;
		private final int mainCatchTime = 150;
		@SuppressWarnings("deprecation")


	
	boolean caught=false;
//	LightSensor light = new LightSensor(SensorPort.S1);
	final double angleConstant=1; //constant, which is used to determine amount of degrees needed to turn the robot one angle. Depends on weight/wheels of robot.
	final double distConstant=1; // should be equal to 0.5 cm in real world // constant, which is used to determine how much move is needed to cross one square.
	final int kickerAngle=0;// depends on kicker, how much does kicker need to kick each time.
	final int kickerReturn=0;// depends on the kicker, whether it needs to return back.
	int curAngle=0;
	OmniPilot robot = new OmniPilot((float)4,(float)6.5,Motor.A,Motor.B,Motor.C,false,false);
	List<Commands> commands = new ArrayList<Commands>(); 
	

	public ControllerHolo()
	{

		I2CPort I2Cport = SensorPort.S4; //Assign port
		I2Cport.i2cEnable(I2CPort.STANDARD_MODE);

		MOTORMUX = new I2CSensor(I2Cport);



		MOTORMUX.setAddress(0xB4);
		//robot.setAcceleration(2500);
	}
	
	private void kick()
	{
		

		try {
			
			
			//Move motor forward at speed 200
			MOTORMUX.sendData(0x02,(byte) 255);
			MOTORMUX.sendData(0x01,(byte) 2);

			

			Thread.sleep(mainKickTime);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException in kick");
		} finally {
			MOTORMUX.sendData(kickerDirection, off);
			MOTORMUX.sendData(kickerSpeed, off);

			
		}

	}
	
	
	private void kickercatch()
	{// needs different constants
		//if(isKicking){

		try {
			MOTORMUX.sendData(0x02,(byte) 255);
			MOTORMUX.sendData(0x01,(byte) 1);

			Thread.sleep(mainCatchTime);
			System.out.println("Inside kickercatch");

		} catch (InterruptedException e) {
			System.out.println("InterruptedException in kick");
		} finally {
			MOTORMUX.sendData(kickerDirection, off);
			MOTORMUX.sendData(kickerSpeed, off);

			//isKicking = false;
		}
		//}
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
		if (toAdd.commandName == CommandNames.ABORT)
			abort();
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
		int lightvalue=0;
		boolean isExit=false;
		while(!isExit)
		{
		//	lightvalue = light.getLightValue();
		//	System.out.println(lightvalue);
		//	if (lightvalue>41&&!caught)
		//	{
		//		System.out.println("CAUGHT");
		//		caught=true;
		//		kickercatch();
		//	}	
			
			Commands curcommand = null;
			synchronized(this) {if (!commands.isEmpty())
			curcommand = commands.remove(0);
			}
			if (curcommand!=null){
			switch (curcommand.commandName)
			{
			case ABORT:
				abort();
				break;
			case CATCH:
				//if (!caught)
				//{
				kickercatch();
				//caught=true;
				System.out.println("CATCHING");
				//}
				break;
			case KICK:
				//if (caught)
				//{
				kick();
				//caught=false;
				System.out.println("KICKING");
				//}
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
		
	}



	private void moveDistance(int speed,int distance, int angle) //Move forward the distance at desiredSpeed
	{
		setSpeed(speed);
		//System.out.println(distance);

		angle=angle*360/255 -180;
		System.out.println(angle);
		robot.travel((distance/1.5), -angle);
	}
	
	private void turnAngle(int speed, int turnAngle) // turn the angle
	{
		turnAngle=turnAngle*360/255 - 180; 
		System.out.println("Angle"+ turnAngle);
		 setSpeed(75);
		robot.rotate(-turnAngle*2.25);	  
	}

}