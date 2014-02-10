package strategy;
import geometry.Vector;


public class StrategyD {
	//strategy for defender
	
	public Queue getAction(State s, World w)
	{
		// this functions takes the state and decide what robots should do
		Action a = new Action();
		
		//       State         ||       Situation   
		// 		 	0						*
		//			1				ball goes to defender    -- milestone 3
		//			2				defender near the ball *
		//			3				defender got the ball
		//			4						*
		//          The states below are for attacker
		//			5				ball goes to attacker    -- milestone 3 
		//			6				attacker near the ball   -- milestone 3
		//			7				attacker got the ball  *
		//			8						*
		//			9						*		
		// There may be more states if needed, state with * are states that can possibly be removed
		
		
		
		switch (s.getState())
		{
		case 0:
		{
			
		}
		case 1:
		{
			//get the coordinate of the ball
			
			//return command that make the robot goes to the horizontal place
			
		}
		case 2:
		{
			
		}
		case 3:
		{
			
		}
		case 4:
		{
			
		}
		default:
		{
			
		}
		
		}
		
		
		
		return a;
	}
	
	
	
}
