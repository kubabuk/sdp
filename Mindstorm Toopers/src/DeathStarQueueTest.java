import geometry.Angle;
import geometry.Point;
import geometry.Vector;

import java.io.IOException;
import java.nio.ByteBuffer;
import commands.*;
import strategy.*;
import comms.*;
import world.*;

public class DeathStarQueueTest {

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
		MainComm theForceAttack = new MainComm(2);
		
		aq.add(new Command(CommandNames.MOVE, 25, 0)); // Forwards
		aq.add(new Command(CommandNames.MOVE, 25, 180)); // Backwards
		aq.add(new Command(CommandNames.MOVE, 25, 20));
		aq.add(new Command(CommandNames.MOVE, 25, -160));
		aq.add(new Command(CommandNames.MOVE, 25, 90)); // Right
		aq.add(new Command(CommandNames.MOVE, 25, -90)); // Left
		aq.add(new Command(CommandNames.MOVE, 25, -45)); // Forward Left
		aq.add(new Command(CommandNames.MOVE, 25, 135)); // Backwards Right
		aq.add(new Command(CommandNames.MOVE, 25, 45)); // Forward Right
		aq.add(new Command(CommandNames.MOVE, 25, -135)); // Backwards Left
		
//		aq.add(new Command(CommandNames.TURN, 0, 90)); // Turn 90 Right
//		aq.add(new Command(CommandNames.TURN, 0, -90)); // Turn 90 Left
//		aq.add(new Command(CommandNames.TURN, 0, 180)); // Turn 180
//		aq.add(new Command(CommandNames.TURN, 0, -180)); // Turn 180
		
		
		while (aq.size() != 0) {
			Command cmd = aq.pull();
			theForceAttack.sendMessage(cmd.getCommand(), 
					cmd.getDistance(), cmd.getAngleDirec(), cmd.getAngle());
		}	

	}
}
