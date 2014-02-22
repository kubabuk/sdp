package strategy;

import geometry.Point;

public class JudgeA {

	public static boolean judge(Goal currentgoal, Goal newgoal)
	{
		if (newgoal.getAbort())
		{
			//send abort command and pass the new goal
			return true;
		}
		
		else if (newgoal.isNull())
		{
			//do nothing
			return false;
		}
		
		else 
		{
			// do judge and decide if we should send the command
			if (currentgoal.getMove() != newgoal.getMove())
			{
				return true;
			}
			else
			{
				if (Point.pointDistance(currentgoal.getGoal(), newgoal.getGoal())< 20)
				{
					return false;
				}
				else
				{
					return true;
				}
			}
				
		}
		
		
		
		
	}
}
