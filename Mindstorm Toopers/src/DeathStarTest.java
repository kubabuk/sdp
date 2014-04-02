import geometry.Angle;
import geometry.Point;
import geometry.Vector;

import java.io.IOException;
import java.nio.ByteBuffer;
import commands.*;
import strategy.*;
import comms.*;
import world.*;

public class DeathStarTest {

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

		//MainComm theForceDef = new MainComm(1);
		MainComm theForceAttack = new MainComm(1);
		
//		theForceAttack.sendMessage(CommandNames.MOVE, 40, 0,0); // Forwards
//		theForceAttack.sendMessage(CommandNames.MOVE, 40, 0,180); // Backwards
//		theForceAttack.sendMessage(CommandNames.MOVE, 40, 0,90); // Right
//		theForceAttack.sendMessage(CommandNames.MOVE, 40, 1,90); // Left
//		theForceAttack.sendMessage(CommandNames.MOVE, 40, 0,45); // Forward Left
//		theForceAttack.sendMessage(CommandNames.MOVE, 40, 1,135); // Backwards Right
//		theForceAttack.sendMessage(CommandNames.MOVE, 40, 1,45); // Forward Right
//		theForceAttack.sendMessage(CommandNames.MOVE, 40, 0,135); // Backwards Left
//		theForceAttack.sendMessage(CommandNames.TURN, 0, 1 ,90); // Turn 90
//		theForceAttack.sendMessage(CommandNames.TURN, 1, 90 ,180); // Turn 180
//		theForceAttack.sendMessage(CommandNames.TURN, 0, 0, 90); // Turn 270
		
		theForceAttack.sendMessage(CommandNames.CATCH, 0, 0, 0);
		Thread.sleep(2000);
		theForceAttack.sendMessage(CommandNames.KICK, 0, 0, 0);
		
		//		theForceAttack.sendMessage(CommandNames.MOVE, 25, 1,90);
//		theForceAttack.sendMessage(CommandNames.MOVE, 25, 1,90);
//		theForceAttack.sendMessage(CommandNames.MOVE, 25, 1,90);
//		theForceAttack.sendMessage(CommandNames.MOVE, 25, 1,90);
//		theForceAttack.sendMessage(CommandNames.MOVE, 25, 1,90);
//		theForceAttack.sendMessage(CommandNames.MOVE, 25, 1,90);
//		theForceAttack.sendMessage(CommandNames.MOVE, 25, 1,90);
		
		int count = 0;
			

	}
}
