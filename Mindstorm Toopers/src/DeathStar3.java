import geometry.Angle;
import geometry.Point;
import geometry.Vector;

import java.io.IOException;
import java.nio.ByteBuffer;
import commands.*;
import strategy.*;
import comms.*;
import world.*;

public class DeathStar3 {

	public static void main (String[] args) throws InterruptedException, IOException{
		boolean color = true;
		boolean direction = true;
		boolean flag = true;
		
		// Instantiate arguments for AI - World, Queue (for attacker), Queue (for defender)
		World universe = new World(color, direction);
		while (!universe.getReady()){
			Thread.sleep(100);
		}
		Queue aq = new Queue();
		Queue dq = new Queue();
		AI emperor = new AI(universe, aq, dq);
		
		// Allow vision time to instantiate
		while(true){
	
			MainComm theForceDef = new MainComm(1);
	//		MainComm theForceAttack = new MainComm(2);
			int count = 0;
				
			while (universe.getReady()) {
				// Update the AI with new actions for the robots. Store them in the queue.
				emperor.update();
					
				//System.out.println("The ball position is deathstar " + universe.getBall().getPos().getX()+" , "+universe.getBall().getPos().getY());
					
				// Retrieve commands from the queue.
				Command cmdDefend = dq.pull();
	
				if(!cmdDefend.isNothing()){
					System.out.println("Sending command");
					if (cmdDefend.getAngle() > 180) {
						//
					}
					else {
						theForceDef.sendMessage(cmdDefend.getCommand(), cmdDefend.getDistance(), 
								cmdDefend.getAngleDirec(), cmdDefend.getAngle());
					}

				}
	
	//			if (cmdAttack.getAngle()>180)
	//				theForceAttack.sendMessage(CommandNames.MOVE, 50,50, 50);
//				if(!cmdAttack.isNothing()){
////					System.out.println("Attempting to send message");
//					if (cmdAttack.getAngle()>180){
////						System.out.println(cmdAttack.getCommand().toString());					
//					}
//					else theForceAttack.sendMessage(cmdAttack.getCommand(), cmdAttack.getDistance(),cmdAttack.getAngleDirec(), cmdAttack.getAngle());
////					System.out.println(cmdAttack.getDistance());
////					System.out.println(cmdAttack.getAngle());
////					//theForceAttack.sendMessage(cmdAttack.getCommand(), cmdAttack.getDistance(),cmdAttack.getAngleDirec(), cmdAttack.getAngle()-180);
//					
//				}
				count++;
				if (count == 10) {	flag = false;}
				Thread.sleep(1000);
			}
		}
	}
}
