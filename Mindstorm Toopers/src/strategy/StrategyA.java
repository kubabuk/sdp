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
			Point b = w.getBall().getPos();
			Point r = w.getAttackerPos();
			Point gp = new Point(r.getX(),r.getY());
			
			if (Point.pointDistance(r, b)<30)
			{
				this.State = 1;
				break;
			}
			
			g = new Goal(gp, CommandNames.MOVE,false,false);
		}
		case 1:
		{
			
			Point b = w.getBall().getPos();
			
			//do catch
			
			//if the ball is caught, switch to state 3
			g = new Goal(b, CommandNames.DONOTHING,false,false);
		}
		case 2:
		{
			Point r = w.getAttackerPos();
			
			g = new Goal(r, CommandNames.KICK,false,false);
			//if the ball status changed to not caught. change the state to 1
		}
		default:
		{
			g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
		}
		
		}
		
		
		
		
		
		
		return judge(lastgoal,g);
	}
	
	
	public static Goal judge(Goal currentgoal, Goal newgoalG)
	{
		Goal newgoal = newgoalG;
		if (newgoal.getAbort())
		{
			//send abort command and pass the new goal
			return newgoal;
		}
		
		else if (newgoal.isNull())
		{
			
			
			return newgoal;
		}
		
		else 
		{
			// do judge and decide if we should send the command
			if (currentgoal.getMove() != newgoal.getMove())
			{
				return newgoal;
			}
			else
			{
				if (Point.pointDistance(currentgoal.getGoal(), newgoal.getGoal())< 10)
				{
					newgoal.setNull(true);
					return newgoal;
				}
				else
				{
					newgoal.setAbort(true);
					return newgoal;
				}
			}
				
		}
		

	}
	
}
