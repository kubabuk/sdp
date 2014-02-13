package strategy;


import world.*;
import geometry.*;
import commands.*;
import vision.*;
import comms.*;


public class StrategyD {
	//strategy for attacker
private static Vector lastInstruction;

	public static void getAction(State s , World w, DefenderQueue dq, boolean notInitial)
	{
		// this functions takes the state and decide what robots should do
		
		//       State         ||       Situation   
		// 		 	0						*
		//			1				ball goes to defender    -- milestone 3
		//			2				defender near the ball *
		//			3				defender got the ball
		//			4						*
		//          The states below are for attacker
		//			5				ball goes to attacker    -- milestone 3 
		//			5				ball goes to attacker    -- milestone 3
		//			6				attacker near the ball   -- milestone 3
		//			7				attacker got the ball  *
		//			8						*
		//			9						*		
		// There may be more states if needed, state with * are states that can possibly be removed
		
		//System.out.println("getAction Defender was called");
		int milestone3 = 3;
		double  stepSize = 5.0;
		double distFromWall = 5.0;
		switch (milestone3)//s.getState())
		{
		
		case 1:
			{
			
		
			// get the coordinate of the ball and the attacker
			Ball ball = w.getBall();
//			Point b = ball.getPos();
			Robot ourDef = w.getDefender();
//			Point r = ourDef.getPos();
			Area myArea = w.getOurDefenderArea();
			//System.out.println("step");
			boolean flag=true;
			while(flag)   //(ball.isMoving())
				{
				/*if(!ourDef.getDir().isParallel(ourDef.getPos().longtitude())){
					//Correct robot orientation
					if(ourDef.getPos().getY()>0){
						Vector turn = new Vector(ourDef.getPos(),0, 3*Math.PI/2.0);
						dq.add(turn);
						
					}
					else{
						Vector turn = new Vector(ourDef.getPos(),0, Math.PI/2.0);
						dq.add(turn);
					}
					
				}*/
				//Find the Point where the robot will intercept the Ball 
				System.out.println("step");
				Point target = ball.getDir().intersectLong(ourDef.getPos().longtitude());
				//if(target.isIn(myArea) && !ourDef.getPos().isColinear(ball.getDir())){
				if(flag){
					//Find the Trajectory in which the robot should move
					Vector trajectory = new Vector(ourDef.getPos(),target);
					//Move one step towards the target
					Vector step = new Vector(ourDef.getPos(),stepSize, trajectory.getOrientation());
					dq.add(step);
					System.out.println(step.toString());
					
				}
				else{
					dq.doNothing();
				}
				break;
				
			}
			
			}
		case 2:{
			Point ballPos = w.getBallPos();
			Ball ball = w.getBall();
			Robot defender = w.getDefender();
			Point robotPos = w.getDefenderPos();
			Vector latitude = ballPos.latitude();
			Vector longtitude = robotPos.longtitude();
			Vector orientation = w.getDefenderDir();
			if(w.isOnTheRight()){
				if(!Angle.sameAngle(orientation.getOrientation(), Math.PI)){
					dq.add(new Vector(robotPos,0,Math.PI));
				}
			}else
			{
				if(!Angle.sameAngle(orientation.getOrientation(), 0)){
					dq.add(new Vector(robotPos,0,0));}
			}
			/*if(ballPos.getY()<=distFromWall){
				dq.add(new Vector(robotPos, new Point(robotPos.getX(),distFromWall)));

				System.out.println("Cond 1");
				System.out.println("Ball: " + ballPos.getY());
				System.out.println("Robot: " +robotPos.getX());
				System.out.println("MaxY: " + (w.getMinY()));
			}else */if(ballPos.getY()>=(55-distFromWall)){
				
			
				//dq.add(new Vector(robotPos, new Point(robotPos.getX(),(55-distFromWall))));
				System.out.println("Cond 2");
				System.out.println("Ball: " + ballPos.getY());
				System.out.println("Robot: " +robotPos.getX());
				System.out.println("MaxY: " + (w.getMinY()));
				Point target = new Point(robotPos.getX(),(55-distFromWall));
				System.out.println(target);
				Vector v = new Vector(robotPos,target);
				if (notInitial){
					System.out.println("Cond 5");
					System.out.println(lastInstruction.getDestination());
					
					
					if(!lastInstruction.getDestination().equals(target)){
						dq.add(v);
					}
				}else{
					dq.add(v);
				}
				lastInstruction = v;
	
			}else{ 
				Point target = latitude.intersectLong(longtitude);
				Vector v = new Vector(robotPos,target);
				System.out.println("Cond 4");
				System.out.println("Ball: " + ballPos.toString());
				System.out.println("Ball: " + ball.getPos().toString());
				System.out.println("Robot: " +robotPos.toString());
				System.out.println("Robot: " +defender.getPos().toString());
				System.out.println("orientation: " + orientation.getOrientation());
				System.out.println("orientation: " + defender.getDir().getOrientation());
				if (notInitial){
					System.out.println("Cond 5");
					System.out.println(lastInstruction.getDestination());
					System.out.println(target);
					
					if(!lastInstruction.getDestination().equals(target)){
						dq.add(v);
					}
				}else{
					dq.add(v);
				}
				lastInstruction = v;
			}
		}
			
	//	}
		case 3:{
			Point ballPos = w.getBallPos();
			Point robotPos = w.getDefenderPos();
			Vector latitude = ballPos.latitude();
			Vector longtitude = robotPos.longtitude();
			
			//Point target = latitude.intersectLong(longtitude);
			Point target = new Point(robotPos.getX(),ballPos.getY()); 
			Vector v = new Vector(robotPos,target);
			//System.out.println("Cond 4");
			//System.out.println("Ball: " + ballPos.toString());
			
			//System.out.println("Robot: " +robotPos.toString());
			
			
			
			if (notInitial){
				//System.out.println("Cond 5");
				//System.out.println(lastInstruction.getDestination());
				//System.out.println(target);
				
				if(!lastInstruction.getDestination().equals(target)){
					dq.add(v);
				}
			}else{
				dq.add(v);
			}
			lastInstruction = v;
			
		}
		default:
			{
			
			}		
		}		
	}	
}
