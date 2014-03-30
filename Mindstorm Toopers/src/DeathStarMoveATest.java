import geometry.Angle;
import geometry.Point;
import geometry.Vector;

import java.io.IOException;
import java.nio.ByteBuffer;
import commands.*;
import strategy.*;
import comms.*;
import world.*;

public class DeathStarMoveATest {

	public static void main (String[] args) throws InterruptedException, IOException{
		boolean color = true;
		boolean direction = true;
		boolean flag = true;
		Goal goal;
		
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
		
		// Test Null goal rejection
		goal = new Goal(new Point(universe.getSecondSectionBoundary() + 100 ,175), CommandNames.MOVE, false, true);
		MoveA.makeCommands(universe,goal,aq);
		
		// Test check for moving outside of boundaries
		goal = new Goal(new Point(universe.getFirstSectionBoundary() + 100 ,20),CommandNames.MOVE,false, false);
		MoveA.makeCommands(universe,goal,aq);
		
		// Test moving within boundaries
		System.out.println(universe.getFirstSectionBoundary());
		goal = new Goal(new Point(universe.getFirstSectionBoundary() + 10 ,20),CommandNames.MOVE,false, false);
		MoveA.makeCommands(universe,goal,aq);
		
		// Test Abort
//		goal = new Goal(new Point(universe.getFirstSectionBoundary() + 10 ,100),CommandNames.MOVE,false, false);
//		MoveA.makeCommands(universe,goal,aq);
//		goal = new Goal(new Point(universe.getFirstSectionBoundary() + 10 ,20),CommandNames.MOVE,false, false);
//		MoveA.makeCommands(universe,goal,aq);
//		goal = new Goal(new Point(universe.getFirstSectionBoundary() + 10 ,100),CommandNames.MOVE,false, false);
//		MoveA.makeCommands(universe,goal,aq);
//		goal = new Goal(new Point(universe.getFirstSectionBoundary() + 10 ,20),CommandNames.MOVE,false, false);
//		MoveA.makeCommands(universe,goal,aq);
//		goal = new Goal(new Point(universe.getFirstSectionBoundary() + 10 ,100),CommandNames.MOVE,false, false);
//		MoveA.makeCommands(universe,goal,aq);;
//		goal = new Goal(new Point(universe.getFirstSectionBoundary() + 10 ,20),CommandNames.MOVE,false, false);
//		MoveA.makeCommands(universe,goal,aq);
//		goal = new Goal(new Point(universe.getFirstSectionBoundary() + 10 ,100),CommandNames.MOVE,false, false);
//		MoveA.makeCommands(universe,goal,aq);
//		
//		goal = new Goal(new Point(300,20),CommandNames.MOVE,true, false);
//		MoveA.makeCommands(universe,goal,aq);
		
		int count = 0;
		
		while (aq.size() != 0) {
			Command cmd = aq.pull();
			theForceAttack.sendMessage(cmd.getCommand(), 
					cmd.getDistance(), cmd.getAngleDirec(), cmd.getAngle());
		}	
			

	}
}
