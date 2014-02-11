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
		Queue aq = new Queue(universe.getAttacker().getDir().getOrientation());
		Queue dq = new Queue(universe.getDefender().getDir().getOrientation());
		
		AI emperor = new AI(universe,aq,dq);
		boolean flag = true;
		
		int[] sendArgumentsDef;
		sendArgumentsDef = new int[1];
		int[] sendArgumentsAttk;
		sendArgumentsAttk = new int[1];
		

		try {
			MainComm theForceDef = new MainComm(1);
//			MainComm theForceAttack = new MainComm(2);
			
			while (flag) {
				emperor.update();
				
				//Command cmdDefend = emperor.defenderpull();
				Command cmdDefend = dq.pull();
//				Command cmdAttack = emperor.attackerpull();
				System.out.println(cmdDefend.getCommand());
				
				sendArgumentsDef[0] = cmdDefend.getSpeed();
				sendArgumentsDef[1] = cmdDefend.getDistAngle();
				if(!cmdDefend.isNothing()){
					
					theForceDef.sendMessage(cmdDefend.getCommand(), sendArgumentsDef);
					
				}
				

				
				
//				sendArgumentsAttk[0] = cmdAttack.getSpeed();
//				sendArgumentsAttk[1] = cmdAttack.getDistAngle();
				
				
				
//				theForceAttack.sendMessage(cmdAttack.getCommand(), sendArgumentsAttk);

//				theForceAttack.sendMessage(cmdAttack.getCommand(), 
//						cmdAttack.getSpeed(), cmdAttack.getDistAngle());

			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	


}
