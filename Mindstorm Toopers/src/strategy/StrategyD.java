package strategy;

import geometry.Point;
import geometry.Vector;
import commands.CommandNames;

import world.World;



public class StrategyD {
	//strategy for attacker
	private World w;
	private int State;
	private int strictfrontboundary;
	private int softfrontboundary;
	private int strictbackboundary;
	private int softbackboundary;
	
	
	
	
	public StrategyD(World w)
	{
		this.w = w;
		this.State = 0;
		
		if (w.getDirection())
		{
			strictfrontboundary = w.getFirstBoundary() - 40;
			softfrontboundary =  w.getFirstBoundary();
			strictbackboundary = (int) w.getMinX() + 40;
			softbackboundary = (int) w.getMinX();
		}
		else
		{
			strictfrontboundary = w.getThirdBoundary() + 40;
			softfrontboundary = w.getThirdBoundary() ;
			strictbackboundary = (int) w.getMaxX() - 40;
			softbackboundary = (int) w.getMaxX();
		}
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
		System.out.println("The current state is: " + this.State);
		Goal g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
		switch (this.State)
		{
		case 0:
		{
			Point b = w.getBall().getPos();
			Point r = w.getDefenderPos();
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
				
				System.out.println("The ball is at "+b.toString());
				System.out.println("The attacker is at "+r.toString());
				
				if (b.getX()>softbackboundary && b.getX()<softfrontboundary && w.getBall().isMoving()) {
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
				
				if (b.getX()>softfrontboundary && b.getX()<softbackboundary) {
					g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
					this.State = 1;
					
					break;
				}
				
				if (gp.getY()>150)
				{
					gp = new Point(gp.getX(), 150);
				}
				else if (gp.getY()<70)
				{
					gp = new Point(gp.getX(), 70);
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
			Point r = w.getDefenderPos();
			Vector v = w.getDefenderDir();
			Point b = w.getBallPos();
			
			//vision bug tolerance
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

				System.out.println("The ball is at "+b.toString());
				System.out.println("The attacker is at "+r.toString());
				
				if (!(b.getX()>softbackboundary&&b.getX()<softfrontboundary)) {
					g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
					this.State = 0;
					
					break;
				}
				
				
			}
			else
			{
				// facing left
			
				System.out.println("The ball is at "+b.toString());
				System.out.println("The attacker is at "+r.toString());
		
				if (!(b.getX()>softfrontboundary && b.getX()<softbackboundary)) {
					g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
					this.State = 0;
					
					break;
				}
				
				
			}
			
			
			//temporary function for iscaught
			//when the real one is done, please remove the code below
			Vector rb = new Vector(r, 20, v.getOrientation());
			Point bc = new Point(r.getX()+rb.getX(),r.getY()+rb.getY());
			boolean iscaught = Point.pointDistance(b, bc) < 10;
			
			
			//to here
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
			Point r = w.getDefenderPos();
			Point b = w.getBallPos();
			Vector v = w.getDefenderDir();
			
			//vision bug tolerance
			if ((b.getX()==0&&b.getY()==0)||(r.getX()==0&&r.getY()==0))
			{
				g = new Goal(new Point(0,0), CommandNames.DONOTHING,true,false);
				break;
			}
			
			//if ball is not caught, switch back to state 1
			//temporary function for iscaught
			//when the real one is done, please remove the code below
			Vector rb = new Vector(r, 20, v.getOrientation());
			Point bc = new Point(r.getX()+rb.getX(),r.getY()+rb.getY());
			boolean iscaught = Point.pointDistance(b, bc) < 10;
			
			if (!iscaught)
			{
				g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
				this.State=1;
				break;
			}
			
			
			Point kp;
			System.out.println("kick The defender is at "+w.getDefender().toString());
			if  (w.getDirection())
			{
				//facing right
				if (Point.pointDistance(w.getOtherAttackerPos(), new Point(150,50))<60)
				{
					kp = new Point(50,50);
					g = new Goal(kp, CommandNames.MOVE,false,false);
					
				}
				else
				{
					kp = new Point(50,170);
					g = new Goal(kp, CommandNames.MOVE,false,false);
					
				}
			
			}
			else
			{
				//facing left
				if (Point.pointDistance(w.getOtherAttackerPos(), new Point(300,50))<60)
				{
					kp = new Point(350,50);
					g = new Goal(kp, CommandNames.MOVE,false,false);
					
				}
				else
				{
					kp = new Point(350,170);
					g = new Goal(kp, CommandNames.MOVE,false,false);
					
				}
			}
			
			// when the robot is close enough to the kick point
			// and has the ball
			// then switch to the kick mode
			// if not, switch back to catch mode
			// and open the catcher
			if (Point.pointDistance(r, kp)<50)
			{
				this.State = 4;
			}
			else if (!iscaught)
			{
				this.State = 1;
				g = new Goal(new Point(0,0), CommandNames.KICK,false,false);
			}
			
			break;
		} // TODO: I think it's OK to skip a case if this doesn't work when testing add an " empty " case 3: (i.e. same as default.
		case 4:
		{
			Point r = w.getDefenderPos();
			Point b = w.getBallPos();
			Vector v = w.getDefenderDir();
			//vision bug tolerance
			if ((b.getX()==0&&b.getY()==0)||(r.getX()==0&&r.getY()==0))
			{
				g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,true);
				break;
			}
			
			//The point is the point we want to kick the ball to.
			Point pass = getPassPoint();

			g = new Goal(pass, CommandNames.KICK,false,false);
			
			//temporary function for iscaught
			//when the real one is done, please remove the code below
			Vector rb = new Vector(r, 20, v.getOrientation());
			Point bc = new Point(r.getX()+rb.getX(),r.getY()+rb.getY());
			boolean iscaught = Point.pointDistance(b, bc) < 10;
			if (!iscaught)
			{
				this.State = 0;
			}
			
			// TODO: The following line might be necessary to get out of this state. Uncomment it if you see that it gets stuck at state 4.
			// this.State = 0;
			break;
			
			
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
		
		
		System.out.println("Goal before judge is "+g.toString());
		
		
		Goal output = judge(lastgoal,g);
		System.out.println("The state is " + this.State);
		System.out.println(output.toString());
		return output;
	}
	
	private Point getPassPoint(){
		//Returns the point towards which we want to pass the ball.
		//I made it private since it should only be called from within StrategyD
		//This is called in case 4 of the switch statement.
		Point pass;
		Point opponentPos = w.getOtherAttackerPos();
		Point ourPos = w.getDefenderPos();
		Point allyPos = w.getAttackerPos();
		Vector defaultPass = new Vector(ourPos,allyPos);
		//The point where the ball might be intercepted by the defender. 
		Point counter = defaultPass.intersectLong(opponentPos.longtitude());
		
		if(Point.pointDistance(counter,opponentPos)<15){
			//if the ball will be intercepted then...
			//TODO: calibrate constant 15 with the radius of the robot (should correspond to 10cm )
			if(opponentPos.getY()< w.getMaxY()/2){
				//if the opponent attacker is closer to the bottom then shoot high
				//TODO: Calibrate the 40 constant. 40 should actually be fine but just to be sure we want sth slightly larger than the robot radius, maybe corresponding to 15-20cm. The idea is that the ball will pass 40 units above the opponent. 
				//This implementation is very naive. A better implementation will consider the possibility of kicking off the wall.
				pass = new Point(opponentPos.getX(),opponentPos.getY() + 40);
			
			}else{
					//else shoot low
					//TODO: Calibrate the 40 constant. See comment above.
					pass = new Point(opponentPos.getX(),opponentPos.getY() - 40);
				}
		}else{
				//if the ball isn't going to be intercepted by the opponent shoot towards the position of our Attacker.
				pass = allyPos;
		}

		return pass;
	}
	
	public static Goal judge(Goal currentgoal, Goal newgoalG)
	{
		Goal newgoal = newgoalG;
		if (newgoal.getAbort())
		{
			//send abort command and pass the new goal
			System.out.println("This is an abort goal");
			return newgoal;
		}
		
		else if (newgoal.isNull())
		{
			
			System.out.println("This is a null goal");
			return newgoal;
		}
		
		else 
		{
			// do judge and decide if we should send the command
			// when the command name is different from the last one (except donothing command)
			if (currentgoal.getMove() != newgoal.getMove())
			{
				System.out.println("This is set to be an abort goal, a new command goal will be executed.");
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
					System.out.println("old goal " + currentgoal.toString());
					System.out.println("new goal " + newgoal.toString());
					System.out.println("This is set to be a null goal");
					
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
