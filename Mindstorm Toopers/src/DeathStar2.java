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
		while (!universe.getReady()){
			Thread.sleep(100);
		}
		Queue aq = new Queue();
		Queue dq = new Queue();
		AI emperor = new AI(universe, aq, dq);
		
		// Allow vision time to instantiate
		while(true){
	
	//		MainComm theForceDef = new MainComm(1);
			MainComm theForceAttack = new MainComm(2);
	//		theForceAttack.sendMessage(CommandNames.MOVE, 25, 0,0);
	//		theForceAttack.sendMessage(CommandNames.MOVE, 25, 0,180);
	//		theForceAttack.sendMessage(CommandNames.MOVE, 25, 0,90);
	//		theForceAttack.sendMessage(CommandNames.MOVE, 25, 1,90);
	//		Goal goal = new Goal(new Point(300,20),CommandNames.MOVE,false);
	//		MoveA.makeCommands(universe,goal,aq);
			
			int count = 0;
				
			while (universe.getReady()) {
				// Update the AI with new actions for the robots. Store them in the queue.
				emperor.update();
					
				//System.out.println("The ball position is deathstar " + universe.getBall().getPos().getX()+" , "+universe.getBall().getPos().getY());
					
				// Retrieve commands from the queue.
				Command cmdAttack = aq.pull();
				//Command cmdDefend = dq.pull();
	
	//			if(!cmdDefend.isNothing()){
	//				theForceDef.sendMessage(cmdDefend.getCommand(), cmdDefend.getDistance(), cmdDefend.getAngle());
	//			}
	
	//			if (cmdAttack.getAngle()>180)
	//				theForceAttack.sendMessage(CommandNames.MOVE, 50,50, 50);
				if(!cmdAttack.isNothing()){
//					System.out.println("Attempting to send message");
					if (cmdAttack.getAngle()>180){
//						System.out.println(cmdAttack.getCommand().toString());					
					}
					else theForceAttack.sendMessage(cmdAttack.getCommand(), cmdAttack.getDistance(),cmdAttack.getAngleDirec(), cmdAttack.getAngle());
//					System.out.println(cmdAttack.getDistance());
//					System.out.println(cmdAttack.getAngle());
					//theForceAttack.sendMessage(cmdAttack.getCommand(), cmdAttack.getDistance(),cmdAttack.getAngleDirec(), cmdAttack.getAngle()-180);
					
				}
				count++;
				if (count == 10) {	flag = false;}
				Thread.sleep(3000);
			}
		}
	}
}
