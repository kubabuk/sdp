package strategy;

public class State {
	//This class represents which state the robot is in,
	//i.e. how the environment is like and what our robots are doing
	
	private int state;
	
	//       State         ||       Situation   
	// 		 	0				initial situation
	//			1				
	//			2
	//			3
	//			4
	//			5
	//			6
	//			7
	//			8
	//			9
	
	public State (int initialstate)
	{
		this.state = initialstate;
	}
	
	public int getState()
	{
		// This function calculates and returns the state 
		int newstate=0;
		//
		
		
		
		
		
		
		
		this.state = newstate;
		return this.state;
	}
}
