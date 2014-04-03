package strategy;

import geometry.Point;
import geometry.Vector;
import commands.CommandNames;

import world.World;



public class StrategyA {
	//strategy for attacker
	private World w;
	private int State;
	private int strictfrontboundary;
	private int softfrontboundary;
	private int strictbackboundary;
	private int softbackboundary;
	
	
	
	
	public StrategyA(World w)
	{
		this.w = w;
		this.State = 0; // Should be 0 for gameplay
		
		if (w.getDirection())
		{
			strictfrontboundary = w.getThirdBoundary() - 40;
			softfrontboundary = w.getThirdBoundary();
			strictbackboundary = w.getSecondBoundary() + 40;
			softbackboundary = w.getSecondBoundary();
		}
		else
		{
			strictfrontboundary = w.getFirstBoundary() + 40;
			softfrontboundary = w.getFirstBoundary();
			strictbackboundary = w.getSecondBoundary() - 40;
			softbackboundary = w.getSecondBoundary();
		}
//		System.out.println("Boundaries:");
//		System.out.println(w.getFirstBoundary());
//		System.out.println(w.getSecondBoundary());
//		System.out.println(w.getThirdBoundary());
	}
		
	public void setState(int State)
	{
		this.State=State;
	}
	
	public int getState()
	{
		return this.State;
	}
		//State     |Movement
		//0         |intercept the ball
		//1         |catch the ball
		//2         |move to the kick point
		//3			|kick
		//4 		|pass the ball
	
	
	
	public Goal getGoal(Goal lastgoal)
	{
		Goal g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
		switch (this.State)
		{
		case 0:
		{
			Point b = w.getBall().getPos();
			Point r = w.getAttackerPos();
			Point gp;
			
			//vision bug tolerance
			if ((b.getX()==0&&b.getY()==0)||(r.getX()==0&&r.getY()==0))
			{
				g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,true);
				break;
			}
			
			//intercept at the boundary strategy
			if (w.getDirection())
			{
				//facing right
				//go to the boundary on the far side of the ball in the attacker zone
				if (b.getX()>r.getX())
				{
					gp = new Point(strictbackboundary,b.getY());
				}
				else
				{
					gp = new Point(strictfrontboundary,b.getY());
				}
				
//				System.out.println("The ball is at "+b.toString());
//				System.out.println("The attacker is at "+r.toString());
				
				if (b.getX()>softbackboundary&&b.getX()<softfrontboundary) {
					g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
					this.State = 1;
					
					break;
				}
				
				g = new Goal(gp, CommandNames.MOVE,false,false);
				
				break;
				
			}
			else
			{
				// facing left
				if (b.getX()>r.getX())
				{
					gp = new Point(strictfrontboundary,b.getY());
				}
				else
				{
					gp = new Point(strictbackboundary,b.getY());
					
				}
				System.out.println("The ball is at "+b.toString());
				System.out.println("The attacker is at "+r.toString());
				
				if (b.getX()>softfrontboundary && b.getX()<softbackboundary) 
				{
					g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
					this.State = 1;
					
					break;
				}
				
				g = new Goal(gp, CommandNames.MOVE,false,false);
				
				break;				
			}
		
		}
		case 1:
		{
			//if the ball is caught, switch to state 2
			
			//if(w.getBall().iscaught()) 
			//The iscaught can not be implemented now
			//so I wrote a function here for it
			Point r = w.getAttackerPos();
			Vector v = w.getAttackerDir();
			Point b = w.getBallPos();
			
			//vision bug tolerance
			
			//temporary function for iscaught
			//when the real one is done, please remove the code below
			Vector rb = new Vector(r, 30, v.getOrientation());
			Point bc = new Point(r.getX()+rb.getX(),r.getY()+rb.getY());
			boolean iscaught = Point.pointDistance(b, bc) < 10;
			
			
			//to here
			
			System.out.println(w.getDirection());
			if ((b.getX()==0&&b.getY()==0)||(r.getX()==0&&r.getY()==0))
			{
				g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,true);
				break;
			}
			
			// if the ball is not in the attacker zone, switch back to interception mode
			if (w.getDirection())
			{
				//facing right
				//go to the boundary on the far side of the ball in the attacker zone

//				System.out.println("The ball is at "+b.toString());
//				System.out.println("The attacker is at "+r.toString());
				
				if (!(b.getX()>softbackboundary&&b.getX()<softfrontboundary)) {
					g = new Goal(bc, CommandNames.KICK,false,false);
					this.State = 0;
					
					break;
				}
				
				
			}
			else
			{
				// facing left
			
//				System.out.println("The ball is at "+b.toString());
//				System.out.println("The attacker is at "+r.toString());
		
				if (!(b.getX()>softfrontboundary&&b.getX()<softbackboundary)) {
					g = new Goal(bc, CommandNames.KICK,false,false);
					this.State = 0;
					
					break;
				}
				
				
			}
			
			
			
			if (iscaught)
			{
				g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
				this.State=2;
				break;
			}
			
			//Point b = w.getBall().getPos();
			//w.getBall().setCaught(true);
			//do catch0
			
			
			
			g = new Goal(b, CommandNames.CATCH,false,false);
			
			
			break;
			
		}
		case 2:
		{		
			//We now have 2 options for kick positions
			Point r = w.getAttackerPos();
			Point b = w.getBallPos();
			Vector v = w.getAttackerDir();
			
			//vision bug tolerance
			if ((b.getX()==0&&b.getY()==0)||(r.getX()==0&&r.getY()==0))
			{
				g = new Goal(new Point(0,0), CommandNames.DONOTHING,true,false);
				break;
			}
			
			//if ball is not caught, switch back to state 1
			//temporary function for iscaught
			//when the real one is done, please remove the code below
			Vector rb = new Vector(r, 30, v.getOrientation());
			Point bc = new Point(r.getX()+rb.getX(),r.getY()+rb.getY());
			boolean iscaught = Point.pointDistance(b, bc) < 10;
			
			if (!iscaught)
			{
				g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
				this.State=1;
				break;
			}
			
			
			Point kp;
//			System.out.println("kick The attacker is at "+w.getAttacker().toString());
			if  (w.getDirection())
			{
				//facing right
				kp = new Point(w.getThirdBoundary() - 40, w.getMaxY() / 2);
				g = new Goal(kp, CommandNames.MOVE,false,false);
				if (Point.pointDistance(w.getOtherDefenderPos(), new Point(350,50))<60)
				{
					kp = new Point(300,50);
					g = new Goal(kp, CommandNames.MOVE,false,false);
					
				}
				else
				{
					kp = new Point(300,170);
					g = new Goal(kp, CommandNames.MOVE,false,false);
					
				}
			
			}
			else
			{
				//facing left
				kp = new Point(w.getFirstBoundary() + 40, w.getMaxY() / 2);
				g = new Goal(kp, CommandNames.MOVE,false,false);
				if (Point.pointDistance(w.getOtherDefenderPos(), new Point(50,50))<60)
				{
					kp = new Point(150,50);
					g = new Goal(kp, CommandNames.MOVE,false,false);
					
				}
				else
				{
					kp = new Point(150,170);
					g = new Goal(kp, CommandNames.MOVE,false,false);
					
				}
			}
			
			// when the robot is close enough to the kick point
			// and has the ball
			// then switch to the kick mode
			// if not, switch back to catch mode
			// and open the catcher
//			if (iscaught&&Point.pointDistance(r, kp)<30)
			if (Point.pointDistance(r, kp)<50)
			{
				this.State = 3;
			}	
			else if (!iscaught)
			{
				this.State = 1;
				g = new Goal(new Point(0,0), CommandNames.KICK,false,false);
			}
			this.State = 3;
			break;
		}
		case 3:
		{
			Point r = w.getAttackerPos();
			Point b = w.getBallPos();
			Vector v = w.getAttackerDir();
			//vision bug tolerance
			if ((b.getX()==0&&b.getY()==0)||(r.getX()==0&&r.getY()==0))
			{
				g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,true);
				break;
			}
			
			//The point is the point we want to kick the ball to.
			Point goal;
			if (w.getDirection())
			{
				//facing right
				goal = new Point (474,114);
			}
			else
			{
				//facing left
				goal = new Point (0,114);
			}
			
			g = new Goal(goal, CommandNames.KICK,false,false);
			
			//temporary function for iscaught
			//when the real one is done, please remove the code below
			Vector rb = new Vector(r, 30, v.getOrientation());
			Point bc = new Point(r.getX()+rb.getX(),r.getY()+rb.getY());
			boolean iscaught = Point.pointDistance(b, bc) < 10;

			if (!iscaught)
			{
				this.State = 0;
			}
			break;
			
			
		}
		case 4:
		{
			/* In state 4 the attacker waits to receive the ball from the defender. 
			 * If the ball is moving (the defender has already kicked it) then move to intercept.
			 * Otherwise wait for the ball to start moving.*/
			
			if(w.getBall().isMoving()){
				this.State = 0;
				g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,true);
				break;
			}
			else{
				g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,true);
				break;
			}
		}
		default:
		{   
			// generally won't be called
			// in special situation this will not be called either
			
			System.out.println("ERROR: State not recognised, revert to default behaviour. ERROR! This state should not be reached!");
			g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,true);
//			System.out.println("going default");
			this.State = 0;
			break;
		}
		
		}
		
		
//		System.out.println("Goal before judge is "+g.toString());
		
		
		Goal output = judge(lastgoal,g);
		System.out.println("The state is " + this.State);
		System.out.println(output.toString());
		return output;
	}
	
	private Point getKickPoint(){
		//Returns the point towards which we want to kick the ball.
		//I made it private since it should only be called from within StrategyA
		//This is called in case 3 of the switch statement.
		Point goal;
		Point defPos = w.getOtherDefenderPos();
		Point ourPos = w.getAttackerPos();
		
		if (w.getDirection())
		{
			//facing right
			Point goalCenter = new Point (474,114); //the center of the GoalPost
			//the trajectory vector if we kick the ball to the center of the goalpost.
			Vector defaultKick = new Vector(ourPos,goalCenter);
			//The point where the ball might be intercepted by the defender. 
			Point counter = defaultKick.intersectLong(defPos.longtitude());
			
			if(Point.pointDistance(counter,defPos)<15){
				//if the ball will be intercepted then...
				//TODO: calibrate constant 15 with the radius of the robot (should correspond to 10cm )
				if(defPos.getY()<114){
					//if the opponent defender is closer to the bottom then shoot high
					//TODO: Calibrate the Y coordinate of the goal. This implementation is very naive. A better implementation will calculate goal based on defPos and with the possibility of kicking off the wall.
					goal = new Point(474,140);
				}else{
					//else shoot low
					//TODO: Calibrate the Y coordinate of the goal. See comment above.
					goal = new Point(474,90);
				}
			}else{
				//if the ball isn't going to be intercepted by the opponent shoot towards the center of the goalpost.
				goal = goalCenter;
			}
			
		}
		else
			//Symmetrical implementation for the other side of the pitch. Exactly as above. See comments above.
		{
			//facing left
			Point goalCenter = new Point (0,114);
			Vector defaultKick = new Vector(ourPos,goalCenter);
			Point counter = defaultKick.intersectLong(defPos.longtitude());
			if(Point.pointDistance(counter,defPos)<15){
				if(defPos.getY()<114){
					goal = new Point(0,140);
				}else{
					goal = new Point(0,90);
				}
			}else{
				goal = goalCenter;
			}
		}
		return goal;
	}
	
	public static Goal judge(Goal currentgoal, Goal newgoalG)
	{
		Goal newgoal = newgoalG;
		
		if (currentgoal.getMove()==CommandNames.CATCH && !newgoal.isNull())
		{
			return newgoal;
		}
		
		
		
		if (newgoal.getAbort())
		{
			//send abort command and pass the new goal
//			System.out.println("This is an abort goal");
			return newgoal;
		}
		
		else if (newgoal.isNull())
		{
			
//			System.out.println("This is a null goal");
			return newgoal;
		}
		
		else 
		{
			// do judge and decide if we should send the command
			// when the command name is different from the last one (except donothing command)
			if (currentgoal.getMove() != newgoal.getMove())
			{
//				System.out.println("This is set to be an abort goal, a new command goal will be executed.");
				System.out.println("In judge: " + currentgoal.getGoal().toString() + 
						", " + newgoal.getGoal().toString());
				newgoal.setAbort(true);
				return newgoal;
			}
			else
			{
				// when the command has the same move as the last one, 
				// we compare the goal point where we want the robot to go
				// if that's a close point to the old one
				// do not change the command
				// if not
				// update the old goal
				if (Point.pointDistance(currentgoal.getGoal(), newgoal.getGoal())< 10)
				{
//					System.out.println("old goal " + currentgoal.toString());
//					System.out.println("new goal " + newgoal.toString());
//					System.out.println("This is set to be a null goal");
					
					newgoal.setNull(true);
					return newgoal;
				}
				else
				{
					System.out.println("This is set to be an abort goal");
					newgoal.setAbort(true);
					return newgoal;
				}
			}
				
		}
		

	}
	
}
