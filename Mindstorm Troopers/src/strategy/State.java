package strategy;

public class State {
	//This class represents which state the robot is in,
	//i.e. how the environment is like and what our robots are doing
	
<<<<<<< HEAD
	private int state;
	
	//       State         ||       Situation   
	// 		 	0				initial situation
	//			1				ball goes to defender
	//			2				defender near the ball *
	//			3				defender got the ball
	//			4				ball goes to attacker
	//			5				ball near the attacker
	//			6				attacker got the ball
	//			7						*
	//			8						*
	//			9				End state*		
	// There may be more states if needed, state with * are states that can possibly be removed
	
	public State (int initialstate)
	{
		this.state = initialstate;
=======
	private int state,mode;
	
	//       State         ||       Situation   
	// 		 	0						*
	//			1				ball goes to defender    -- milestone 3
	//			2				defender near the ball *
	//			3				defender got the ball
	//			4						*
	//          The states below are for attacker
	//			5				ball goes to attacker
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
		
>>>>>>> AI
	}
	
	public int getState()
	{
		return this.state;
	}
	
	
	public void update()
	{
		// This function calculates and updates the current state 
		int newstate=0;
		//
		
<<<<<<< HEAD
=======
		if (mode == 0)
		{
			//defender
			
		}
		else
		{
			//attacker
			
		}
>>>>>>> AI
		
		
		
		
		
		
		this.state = newstate;
	}
}
