import java.io.IOException;
import java.nio.ByteBuffer;
import comms.CommandNames;
import comms.*;
import strategy.*;
import commands.*;
import world.*;
import geometry.*;

public class DeathStar2 {
	
	public static void main (String[] args){
		boolean color = args[0].equals("yellow");
		boolean direction = args[0].equals("right");
		
		World universe = new World(color, direction);
		AttackerQueue aq = new AttackerQueue(universe.getAttacker().getDir().getOrientation());
		DefenderQueue dq = new DefenderQueue(universe.getDefender().getDir().getOrientation());
		
		AI emperor = new AI(universe,aq,dq);
		boolean flag = true;
		int n = 0;
				
		
		try {
			MainComm theForceDef = new MainComm(1);
//			MainComm theForceAttack = new MainComm(2);
			while(n<=500){
				n++;
			}
			n=0;
			int init = (int) Angle.to255(Angle.toRange2PI(universe.getAttackerDir().getOrientation()));
			theForceDef.sendMessage(CommandNames.UPDATEANGLE, init, 0);
			
			while (flag) {
				//if(n%10==0){
				emperor.update();
				emperor.notInitial();
				//Command cmdDefend = emperor.defenderpull();
				Command cmdDefend = dq.pull();
//				Command cmdAttack = emperor.attackerpull();
				
				
				//System.out.println(n);
				if(!cmdDefend.isNothing() ){
					if(cmdDefend.getCommand()!=CommandNames.DONOTHING){
					System.out.println(cmdDefend.getCommand());
					theForceDef.sendMessage(cmdDefend.getCommand(), 100, cmdDefend.getDistAngle());
					}
				}
				
				//sendArgumentsDef[0] = cmdDefend.getSpeed();
				//sendArgumentsDef[1] = cmdDefend.getDistAngle();
				//if(!cmdDefend.isNothing()){
					
					//theForceDef.sendMessage(cmdDefend.getCommand(), sendArgumentsDef);
					
				//}
				
				/*theForceDef.sendMessage(CommandNames.MOVEFORWARD, 200, 5);
				
				theForceDef.sendMessage(CommandNames.KICK,250, 10);
				
				theForceDef.sendMessage(CommandNames.CHANGEANGLE, 300, 5);
				
				theForceDef.sendMessage(CommandNames.EXIT, 300, 5);
				
				*/
				
//				sendArgumentsAttk[0] = cmdAttack.getSpeed();
//				sendArgumentsAttk[1] = cmdAttack.getDistAngle();
				
				
				
//				theForceAttack.sendMessage(cmdAttack.getCommand(), sendArgumentsAttk);

//				theForceAttack.sendMessage(cmdAttack.getCommand(), 
//						cmdAttack.getSpeed(), cmdAttack.getDistAngle());
				//n++;
			}
			//}
			theForceDef.sendMessage(CommandNames.EXIT, 0, 0);
			theForceDef.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	


}
