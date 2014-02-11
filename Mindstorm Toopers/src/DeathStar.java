import java.io.IOException;
import java.nio.ByteBuffer;

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
		
		int[] sendArgumentsDef;
		sendArgumentsDef = new int[1];
		int[] sendArgumentsAttk;
		sendArgumentsAttk = new int[1];
		
		//ANDY HELP
		try {
			MainComm theForceDef = new MainComm(1);
//			MainComm theForceAttack = new MainComm(2);
			
			while (flag) {
				emperor.update();
				
				Command cmdDefend = emperor.defenderpull();
//				Command cmdAttack = emperor.attackerpull();
				//System.out.println(cmdDefend.getCommand());
				if(!cmdDefend.isNothing()){
					
					theForceDef.sendMessage(cmdDefend.getCommand(), 
							cmdDefend.getSpeed(), cmdDefend.getDistAngle());
					
				}
				
<<<<<<< HEAD
				sendArgumentsDef[0] = cmdDefend.getSpeed();
				sendArgumentsDef[1] = cmdDefend.getDistAngle();
				
				sendArgumentsAttk[0] = cmdAttack.getSpeed();
				sendArgumentsAttk[1] = cmdAttack.getDistAngle();
				
				theForceDef.sendMessage(cmdDefend.getCommand(), sendArgumentsDef);
				
				theForceAttack.sendMessage(cmdAttack.getCommand(), 
						sendArgumentsAttk);
=======
				
//				theForceAttack.sendMessage(cmdAttack.getCommand(), 
//						cmdAttack.getSpeed(), cmdAttack.getDistAngle());
>>>>>>> e7ef3b331d8a8c8fe739af78c04733966ada7b78
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	


}
