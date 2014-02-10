package strategy;
import geometry.Vector;


public class Strategy {
	
	public Action getAction(State s)
	{
		// this functions takes the state and decide what robots should do
		Action a = new Action();
		
<<<<<<< HEAD
=======
		//       State         ||       Situation   
		// 		 	0				initial situation
		//			1				ball goes to defender
		//			2				defender near the ball *
		//			3				defender got the ball
		//			4				ball goes to attacker
		//			5				attacker near the ball
		//			6				attacker got the ball
		//			7						*
		//			8						*
		//			9				End state*		
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
		case 5:
		{
			// get the coordinate of the ball and the attacker
			
			// get the orientation of the attacker
			
			// return the command that make the robot goes towards the ball
			
			
		}
		case 6:
		{
			
			// adjust the orientation of the robot, make it towards the ball and the goal
			
			// kick
			
			// move back to prepare position *
			
			
		}
		case 7:
		{
			
		}
		case 8:
		{
			
		}
		case 9:
		{
			
		}
		case default:
		{
			
		}
		
		}
		
		
		
>>>>>>> AI
		return a;
	}
	
	
	
}
