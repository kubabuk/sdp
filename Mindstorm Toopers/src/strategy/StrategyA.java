package strategy;

import geometry.Point;
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
		this.State = State;
	}
	
	public int getState()
	{
		return this.State;
	}
		//State     |Movement
		//0         |intercept the ball
		//1         |catch the ball
		//2         |kick the ball
	
	
	
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
			Point r = w.getAttackerPos();
			Point gp = new Point(r.getX(),b.getY());
			
			System.out.println("The ball is at "+b.toString());
			System.out.println("The attacker is at "+r.toString());
			
			if (Point.pointDistance(r, b)<30)
			{
				this.State = 1;
				break;
			}
			
			g = new Goal(gp, CommandNames.MOVE,false,false);
			
			break;
		}
		case 1:
		{
			//if the ball is caught, switch to state 3
			/*if(w.getBall().iscaught()){
			//g = new Goal(b, CommandNames.DONOTHING,false,false);
			this.State=2;
			break;
			}
			*/
			Point b = w.getBall().getPos();
			
			//do catch0
			g = new Goal(b, CommandNames.CATCH,false,false);
			
			//if the ball is caught, switch to state 3s
			break;
			
		}
		case 2:
		{
			Point r = w.getAttackerPos();

			g = new Goal(r, CommandNames.KICK,false,false);
			//if the ball status changed to not caught. change the state to 1
			
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
