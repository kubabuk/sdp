import java.io.IOException;

import comms.*;
import strategy.*;
import commands.*;
import world.*;

public class DeathStar {
	
	public static void main (String[] args){
		boolean color = args[0].equals("yellow");
		boolean direction = args[0].equals("right");
		
		World universe = new World(color, direction);
		AI emperor = new AI(universe);
		boolean flag = true;
		
		//ANDY HELP
		try {
			MainComm theForceDef = new MainComm();
			MainComm theForceAttack = new MainComm();
			
			while (flag) {
				emperor.update();
				
				Command cmdDefend = emperor.defenderpull();
				Command cmdAttack = emperor.attackerpull();
				
				theForceDef.sendMessage(cmdDefend.getCommand(), 
						cmdDefend.getSpeed(), cmdDefend.getDistAngle());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	


}
