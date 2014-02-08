

//Import all sorts of files from vision and communication



public class Main {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		State curentstate = new State(0);
		State laststate = new State(0);
		
		
		while (true)
		{
			//detect which state we are at now
			laststate = curentstate;
			curentstate.update();
			
			//decide the Actionw we are going to take
			Action action = Strategy.getAction(curentstate);
			
			//send action to the robot
			
			
			//do this again
			
			//"maybe" set a end state (goal) to break this loop
		}
				
		
	}

}
