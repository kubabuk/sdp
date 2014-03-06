package com.mydomain;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.I2CPort;
import lejos.nxt.I2CSensor;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

import com.mydomain.Commands.CommandNames;

import lejos.nxt.Motor;
import lejos.robotics.LightScanner;

public class Controller {

	
	
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



	LightSensor light = new LightSensor(SensorPort.S1);
	final double angleConstant=1; //constant, which is used to determine amount of degrees needed to turn the robot one angle. Depends on weight/wheels of robot.
	final double distConstant=1; // should be equal to 0.5 cm in real world // constant, which is used to determine how much move is needed to cross one square.
	final int kickerAngle=0;// depends on kicker, how much does kicker need to kick each time.
	final int kickerReturn=0;// depends on the kicker, whether it needs to return back.
	int curAngle=0;
	List<Commands> commands = new ArrayList<Commands>(); 
	
	private int speed = 300;// default speed, can be changed in mid run;

	public Controller()
	{

		I2CPort I2Cport = SensorPort.S4; //Assign port
		I2Cport.i2cEnable(I2CPort.STANDARD_MODE);

		MOTORMUX = new I2CSensor(I2Cport);



		MOTORMUX.setAddress(0xB4);
	  Motor.A.setAcceleration(3000);
	  Motor.B.setAcceleration(3000);
	  Motor.C.setAcceleration(3000);
	  Motor.A.setSpeed(speed);
	  Motor.B.setSpeed(speed);
	  Motor.C.setSpeed(speed);
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

	
	public void run() // main thread, which works on stuff. takes a command, analyses it and does it.
	{
		int lightvalue=0;
		boolean isExit=false;
		while(!isExit)
		{
			lightvalue = light.getLightValue();
			System.out.println(lightvalue);
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
			case CATCH:
//				if (!caught)
				{
				kickercatch();
//				caught=true;
				System.out.println("CATCHING");
				}
				break;
			case KICK:
//				if (caught)
				{
				kick();
//				caught=false;
				System.out.println("KICKING");
				}
				break;
			case MOVE:
				moveDistance(curcommand.firstArg,curcommand.secondArg);
				break;
			case TURN:
				turnAngle(curcommand.firstArg);
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
		  Motor.C.setSpeed(desiredSpeed);
		  // more motors needed
	}



	private void moveDistance(int distance, int angle) //Move forward the distance at desiredSpeed
	{
	//data points:
	//0/100% motors sideway/forward=0 degrees
	//50/50 motors sideway/forward = 45 degrees
	//100%/0% motors sideway/forward = 90 degrees
	//setSpeed(desiredSpeed);
	//setSpeed(speed);

	
	
	
	
    if (angle>=0 &&angle <=90)
    {
	float tempangle= ((float)angle)/90;//gives percentage of distribution	
	Motor.A.setSpeed(speed*tempangle);
	Motor.B.setSpeed(speed*(1-tempangle));
	Motor.C.setSpeed(speed*(1-tempangle));
	float percentangle = (float)Math.abs(0.5-tempangle);
	Motor.A.rotate((int)((tempangle)*(distance*(1.5-(percentangle)))*distConstant),true); // move forward, do not wait for it to finish, so move is simultaneous and does not turn a robot.
	Motor.B.rotate((int)((1-tempangle)*(distance*(1.5-(percentangle)))*distConstant),true); 
	Motor.C.rotate((int)((1-tempangle)*(distance*(1.5-(percentangle)))*distConstant),false);
    }
    else if(angle>0)
    {
    	angle=angle-90;
    	float tempangle= ((float)angle)/90;//gives percentage of distribution	
    	Motor.A.setSpeed(speed*(1-tempangle));
    	Motor.B.setSpeed(speed*tempangle);
    	Motor.C.setSpeed(speed*tempangle);
    	float percentangle = (float)Math.abs(0.5-tempangle);
    	Motor.A.rotate((int)((tempangle)*(distance*(1.5-(percentangle)))*distConstant),true); // move forward, do not wait for it to finish, so move is simultaneous and does not turn a robot.
    	Motor.B.rotate((int)(-(1-tempangle)*(distance*(1.5-(percentangle)))*distConstant),true); 
    	Motor.C.rotate((int)(-(1-tempangle)*(distance*(1.5-(percentangle)))*distConstant),false);
    }
    else if(angle<-90)
    {
    	angle=Math.abs(angle)-90;
    	float tempangle= ((float)angle)/90;//gives percentage of distribution	
    	Motor.A.setSpeed(speed*(1-tempangle));
    	Motor.B.setSpeed(speed*tempangle);
    	Motor.C.setSpeed(speed*tempangle);
    	float percentangle = (float)Math.abs(0.5-tempangle);
    	Motor.A.rotate((int)(-(1-tempangle)*(distance*(1.5-(percentangle)))*distConstant),true); // move forward, do not wait for it to finish, so move is simultaneous and does not turn a robot.
    	Motor.B.rotate((int)(-(tempangle)*(distance*(1.5-(percentangle)))*distConstant),true); 
    	Motor.C.rotate((int)(-(tempangle)*(distance*(1.5-(percentangle)))*distConstant),false);
 
    }
    else 
    {
    	angle=Math.abs(angle);
    	float tempangle= ((float)angle)/90;//gives percentage of distribution	
    	Motor.A.setSpeed(speed*(1-tempangle));
    	Motor.B.setSpeed(speed*tempangle);
    	Motor.C.setSpeed(speed*tempangle);
    	float percentangle = (float)Math.abs(0.5-tempangle);
    	System.out.println(percentangle);
    	System.out.println(-(1-tempangle)*(distance*(1.5-(percentangle))));
    	System.out.println((tempangle)*(distance*(1.5-(percentangle))));
    	
    	
    	Motor.A.rotate((int)(-(1-tempangle)*(distance*(1.5-(percentangle)))*distConstant),true); // move forward, do not wait for it to finish, so move is simultaneous and does not turn a robot.
    	Motor.B.rotate((int)((tempangle)*(distance*(1.5-(percentangle)))*distConstant),true); 
    	Motor.C.rotate((int)((tempangle)*(distance*(1.5-(percentangle)))*distConstant));
    }
	}
	
	private void turnAngle( int turnAngle) // turn the angle
	{
		    //setSpeed(desiredSpeed);
		    setSpeed(75);
		   // turnAngle= (turnAngle*360/256)-180;//get an angle between -180 and 180
		    Motor.B.rotate((int)(turnAngle*3),true);
		    Motor.C.rotate((int)(-turnAngle*3),false);
	

		    
		  
	}

}
