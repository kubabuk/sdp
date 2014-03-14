package strategy;

import geometry.Point;
import geometry.Vector;
import commands.CommandNames;

import world.World;



public class StrategyA {
	//strategy for attacker
	private World w;
	private int State;
	
	public StrategyA(World w)
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
//			if(!w.getBall().isMoving()){
//				this.State = 1;
//				break;
//			}
			Point b = w.getBall().getPos();
			Point r = w.getAttackerPos();
			Point gp;
			
			//intercept at the boundary strategy
			if (w.getDirection())
			{
				if (b.getX()>r.getX())
				{
					gp = new Point(256,b.getY());
				}
				else
				{
					gp = new Point(316,b.getY());
				}
				System.out.println("The ball is at "+b.toString());
				System.out.println("The attacker is at "+r.toString());
				
				if (b.getX()>236&&b.getX()<336) {
					g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
					this.State = 1;
					
					break;
				}
				
				g = new Goal(gp, CommandNames.MOVE,false,false);
				
				break;
				
			}
			else
			{
				if (b.getX()>r.getX())
				{
					gp = new Point(116,b.getY());
				}
				else
				{
					gp = new Point(176,b.getY());
					
				}
				System.out.println("The ball is at "+b.toString());
				System.out.println("The attacker is at "+r.toString());
				
				if (b.getX()>96&&b.getX()<176) {
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
			Point r = w.getAttackerPos();
			Point kp;
			System.out.println("kick The attacker is at "+w.getAttacker().toString());
			if  (w.getDirection())
			{
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
			
			if (Point.pointDistance(r, kp)<30)
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
