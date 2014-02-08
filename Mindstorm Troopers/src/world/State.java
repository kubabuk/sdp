
public class State {
	//This class represents which state the robot is in,
	//i.e. how the environment is like and what our robots are doing
	
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
	}
	
	public void update()
	{
		// This function calculates and updates the current state 
		int newstate=0;
		//
		
		
		
		
		
		
		
		this.state = newstate;
	}
}
