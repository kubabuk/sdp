package strategy;

public class JudgeA {

	public static boolean judge(Goal currentgoal, Goal newgoal)
	{
		if (newgoal.getAbort())
		{
			//send abort command and pass the new goal
		}
		
		else if (newgoal.isNull())
		{
			//do nothing
		}
		
		else 
		{
			// do judge and decide if we should send the command
		}
		
		
		
		
	}
}
