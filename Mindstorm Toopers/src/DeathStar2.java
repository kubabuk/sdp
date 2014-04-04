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
		int count = 0;

		// Allow vision time to instantiate
		while(true){
	
			MainComm theForceDef = new MainComm(1);
			MainComm theForceAttack = new MainComm(2);

			

			
			while (universe.getReady()) {
				// Update the AI with new actions for the robots. Store them in the queue.
				
				if (count % 7 == 1) {
					Command cmd = new Command(CommandNames.KICK, 0, 0);
					theForceAttack.sendMessage(cmd.getCommand(), cmd.getDistance(),cmd.getAngleDirec(), cmd.getAngle());
					theForceDef.sendMessage(cmd.getCommand(), cmd.getDistance(),cmd.getAngleDirec(), cmd.getAngle());

				}
				if (count % 13 == 1) {
					Command cmd = new Command(CommandNames.CATCH, 0, 0);
					theForceAttack.sendMessage(cmd.getCommand(), cmd.getDistance(),cmd.getAngleDirec(), cmd.getAngle());
					theForceDef.sendMessage(cmd.getCommand(), cmd.getDistance(),cmd.getAngleDirec(), cmd.getAngle());

				}
				
				emperor.update();
					
					
				// Retrieve commands from the queue.
				Command cmdAttack = aq.pull();
				Command cmdDefend = dq.pull();
	

				if(!cmdAttack.isNothing()){
					System.out.println("Attempting to send message");
					if (cmdAttack.getAngle()>180){
						System.out.println(cmdAttack.getCommand().toString());					
					}
					else theForceAttack.sendMessage(cmdAttack.getCommand(), cmdAttack.getDistance(),cmdAttack.getAngleDirec(), cmdAttack.getAngle());
				}
					

				if(!cmdDefend.isNothing()){
				System.out.println("Attempting to send message");
				if (cmdDefend.getAngle()>180){
					System.out.println(cmdDefend.getCommand().toString());					
				}
				else theForceDef.sendMessage(cmdDefend.getCommand(), cmdDefend.getDistance(),cmdDefend.getAngleDirec(), cmdDefend.getAngle());
				}
				count++;
//				if (count == 10) {	flag = false;}
				Thread.sleep(2000);
			}
		}
	}
}
