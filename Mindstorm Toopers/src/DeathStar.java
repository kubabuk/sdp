import geometry.Angle;
import geometry.Point;
import geometry.Vector;

import java.io.IOException;
import java.nio.ByteBuffer;
import commands.*;
import strategy.*;
import comms.*;
import world.*;

public class DeathStar {

	public static void main (String[] args) throws InterruptedException, IOException{
		boolean color = args[0].equals("yellow");
		boolean direction = args[1].equals("right");

		
		World universe = new World(color, direction);
		Thread.sleep(7000);
		Queue aq = new Queue();//universe.getAttacker().getDir().getOrientation());
		Queue dq = new Queue();

		
		AI emperor = new AI(universe,aq,dq);
		boolean flag = true;
		

		//MainComm theForceDef = new MainComm(1);
		MainComm theForceAttack = new MainComm(2);


			
			
			
		while (flag) {
			emperor.update();
				
			System.out.println("The ball position is " + universe.getBall().getPos().getX()+" , "+universe.getBall().getPos().getY());
				
//				System.out.println("The ball is at "+universe.getBall().toString());
//				System.out.println("The attacker is at "+universe.getAttacker().toString());
//				System.out.println("The defender is at "+universe.getDefender().toString());
//				System.out.println("The max X,Y are " + universe.getMinX() + " , " + universe.getMinY());
				
				

			Command cmdAttack = aq.pull();
			System.out.println("command pulled");
			//System.out.println(cmdDefend.getCommand());

			//sendArgumentsDef[0] = cmdDefend.getSpeed();
			//sendArgumentsDef[1] = cmdDefend.getDistAngle();
			//if(!cmdDefend.isNothing()){

			//	theForceDef.sendMessage(cmdDefend.getCommand(), );

			//}

			if(!cmdAttack.isNothing()){
				theForceAttack.sendMessage(cmdAttack.getCommand(), cmdAttack.getDistance(), cmdAttack.getAngle());
			}
				
		
//				theForceAttack.sendMessage(cmdAttack.getCommand(), sendArgumentsAttk);

//				theForceAttack.sendMessage(cmdAttack.getCommand(), 
//						cmdAttack.getSpeed(), cmdAttack.getDistAngle());

				//flag = false;
			}

		
	}




}
