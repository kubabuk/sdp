package strategy;

import geometry.Point;
import geometry.Vector;
import commands.CommandNames;

import world.World;



public class StrategyD {
	//strategy for attacker
	private World w;
	private int State;
	
	public StrategyD(World w)
	{
		this.w = w;
		this.State = 0;
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
	
	
	
	public Goal getGoal(Goal lastgoal)
	{
		Goal g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
		switch (this.State)
		{
		case 0:
		{
			if(!w.getBall().isMoving()){
				this.State = 1;
				break;
			}
			Point b = w.getBall().getPos();
			Point r = w.getDefenderPos();
			
			Point gp = new Point(r.getX(),b.getY());
			


			System.out.println("The ball is at "+b.toString());
			System.out.println("The attacker is at "+r.toString());
			
			if (Point.pointDistance(r, b)<30) {
				g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
				this.State = 1;
				
				break;
			}
			
			g = new Goal(gp, CommandNames.MOVE,false,false);
			
			break;
			
			
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
			Vector rb = new Vector(r, 20, v.getOrientation());
			Point bc = new Point(r.getX()+rb.getX(),r.getY()+rb.getY());
			boolean iscaught = Point.pointDistance(b, bc) < 10;
			
			
			//
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
			Point kp;
			if  (w.getDirection())
			{
				if (Point.pointDistance(w.getOtherDefenderPos(), new Point(350,50))<60)
				{
					kp = new Point(70,50);
					g = new Goal(kp, CommandNames.MOVE,false,false);
				}
				else
				{
					kp = new Point(70,170);
					g = new Goal(kp, CommandNames.MOVE,false,false);
				}
			
			}
			else
			{
				if (Point.pointDistance(w.getOtherDefenderPos(), new Point(50,50))<60)
				{
					kp = new Point(370,50);
					g = new Goal(kp, CommandNames.MOVE,false,false);
				}
				else
				{
					kp = new Point(370,170);
					g = new Goal(kp, CommandNames.MOVE,false,false);
				}
			}
			
			if (Point.pointDistance(r, kp)<40)
			{
				this.State = 3;
			}
			
			break;
		}
		case 3:
		{
			//The point is the point we want to kick the ball to.
			Point goal;
			if (w.getDirection())
			{
				goal = new Point (474,114);
			}
			else
			{
				goal = new Point (0,114);
			}

			g = new Goal(goal, CommandNames.KICK,false,false);
			w.getBall().setCaught(false);
			this.State = 0;
			
			break;
			
			
		}
		default:
		{
			g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,true);
			System.out.println("going default");
			break;
		}
		
		}
		
		
		System.out.println("Goal before judge is "+g.toString());
		
		
		Goal output = judge(lastgoal,g);
		System.out.println("The state is " + this.State);
		System.out.println(output.toString());
		return output;
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
			if (currentgoal.getMove() != newgoal.getMove())
			{
				System.out.println("This is set to be an abort goal");
				newgoal.setAbort(true);
				return newgoal;
			}
			else
			{
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
