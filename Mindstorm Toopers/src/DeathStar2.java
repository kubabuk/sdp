import geometry.Angle;
import geometry.Point;
import geometry.Vector;

import java.io.IOException;
import java.nio.ByteBuffer;
import commands.*;
import strategy.*;
import comms.*;
import world.*;

public class DeathStar2 {

	public static void main (String[] args) throws InterruptedException, IOException{
		boolean color = true;
		boolean direction = true;
		boolean flag = true;
		
		// Instantiate arguments for AI - World, Queue (for attacker), Queue (for defender)
		World universe = new World(color, direction);
		Queue aq = new Queue();
		Queue dq = new Queue();
		
		AI emperor = new AI(universe, aq, dq);
		
		// Allow vision time to instantiate
		while (!universe.getReady()){
			Thread.sleep(100);
		}

//		MainComm theForceDef = new MainComm(1);
		MainComm theForceAttack = new MainComm(2);
		
		int count = 0;
			
		while (flag) {
			// Update the AI with new actions for the robots. Store them in the queue.
			emperor.update();
				
			System.out.println("The ball position is " + universe.getBall().getPos().getX()+" , "+universe.getBall().getPos().getY());
				
			// Retrieve commands from the queue.
			Command cmdAttack = aq.pull();
			//Command cmdDefend = dq.pull();

//			if(!cmdDefend.isNothing()){
//				theForceDef.sendMessage(cmdDefend.getCommand(), cmdDefend.getDistance(), cmdDefend.getAngle());
//			}

			if(!cmdAttack.isNothing()){
				theForceAttack.sendMessage(cmdAttack.getCommand(), cmdAttack.getDistance(), cmdAttack.getAngle());
			}
			count++;
			//if (count == 4) {	flag = false;}
		}

		
	}




}
