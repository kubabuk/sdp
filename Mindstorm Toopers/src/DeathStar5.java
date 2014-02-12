import geometry.Point;
import geometry.Vector;

import java.io.IOException;

import commands.Command;
import commands.DefenderQueue;
import comms.CommandNames;
import comms.MainComm;

import world.World;


public class DeathStar5 {

	public static void main (String [] args) throws InterruptedException {
		boolean color = args[0].equals("yellow");
		boolean direction = args[1].equals("right");
		boolean withinBoundaries = true;
		boolean ballWillMove = true;
		boolean ballWillMoveY = true;
		double lastPoint = 0;
		int mult = 1;
		if (direction)
			mult = -1;
			
		
		World universe = new World(color, direction);	
		Thread.sleep(5000);
		boolean flag = true;
		int n = 0;
		Point ballPos = universe.getBallPos();
		Point robotPos = universe.getDefenderPos();
		Point lastBall = new Point (ballPos.getX()+5,0);
		
		try {
			MainComm theForceDef = new MainComm(1);
			
			Point target = new Point(robotPos.getX(), ballPos.getY());
			Vector v = new Vector(robotPos,target);
			Vector step = new Vector(robotPos, v.getMagnitude()/2, mult*v.getOrientation());
			Command cmd= new Command(CommandNames.DONOTHING,0,0);
			
			while (Math.abs(ballPos.getY()-robotPos.getY())>=10) {
				step = new Vector(robotPos, v.getMagnitude()/2, mult*v.getOrientation());
				if(lastPoint != ballPos.getY()){
					cmd = DefenderQueue.translate(step);
				}
				lastPoint = ballPos.getY();
				Thread.sleep(500);
			}
			theForceDef.sendMessage(CommandNames.EXIT, 0, 0);
			theForceDef.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
