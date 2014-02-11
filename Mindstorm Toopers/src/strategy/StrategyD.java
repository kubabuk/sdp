package strategy;


import world.*;
import geometry.*;
import commands.*;
import vision.*;
import comms.*;


public class StrategyD {
	//strategy for attacker
	

	public static Queue getAction(State s , World w)
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
		
		
		int milestone3 = 1;
		switch (milestone3)//s.getState())
		{
		
		case 1:
			{
			
		
			// get the coordinate of the ball and the attacker
			Ball ball = w.getBall();
			Point b = ball.getPos();
			Robot ourDef = w.getDefender();
			Point r = ourDef.getPos();
			Queue dq = new Queue(ourDef.getDir().getOrientation());
			Area myArea = w.getOurDefenderArea();
			
			while(ball.isMoving()){
				if(!ourDef.getDir().isParallel(ourDef.getPos().longtitude())){
					//Correct robot orientation
					if(ourDef.getPos().getY()>0){
						Vector turn = new Vector(ourDef.getPos(),0, 3*Math.PI/2.0);
						dq.add(turn);
						
					}
					else{
						Vector turn = new Vector(ourDef.getPos(),0, Math.PI/2.0);
						dq.add(turn);
					}
					
				}
				//Find the Point where the robot will intercept the Ball 
				Point target = ball.getDir().intersectLong(ourDef.getPos().longtitude());
				if(target.isIn(myArea) && !ourDef.getPos().isColinear(ball.getDir())){
					//Find the Trajectory in which the robot should move
					Vector trajectory = new Vector(ourDef.getPos(),target);
					//Move one step towards the target
					Vector step = new Vector(ourDef.getPos(),1, trajectory.getOrientation());
					dq.add(step);
				}
				else{
					dq.doNothing();
				}
				
			}
				return dq;
			}
		
		default:
			{
				return new Queue(0.0);
			}		
		}		
	}	
}
