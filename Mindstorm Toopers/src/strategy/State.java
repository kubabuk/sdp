package strategy;



import geometry.Point;
import world.World;

public class State {
	//This class represents which state the robot is in,
	//i.e. how the environment is like and what our robots are doing
	
	private int state,mode;
	
	
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
	
	public State()
	{
		this.state = 0;
		this.mode = 0;
	}
	
	public State (int mode)
	{
		//1 for attacker, 0 for defender
		this.mode = mode;
		
	}
	
	public int getState()
	{
		return this.state;
	}
	
	
	public void update(World w)
	{
		// This function calculates and updates the current state 
		int newstate=0;
		//
		
		if (mode == 0)
		{
			//defender
			
		}
		else
		{
			//attacker
			Point b = w.getBallPos();
			Point r = w.getAttacker().getPos();
			
			if (Point.pointDistance(r, b) < 10 )
			{
				newstate = 5;
			}
			else
			{
				newstate = 6;
			}
			
			
		}
		
		
		
		
		
		// changes the state
		this.state = newstate;
	}
}
