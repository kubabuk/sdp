import geometry.Angle;
import geometry.Point;
import geometry.Vector;
import java.lang.Math;

import java.io.IOException;

import strategy.AI;
import world.World;

import commands.AttackerQueue;
import commands.Command;
import commands.DefenderQueue;
import comms.CommandNames;
import comms.MainComm;


public class DeathStar3 {
	public static void main (String[] args) throws InterruptedException{
		boolean color = args[0].equals("yellow");
		boolean direction = args[1].equals("right");
		boolean withinBoundaries = true;
		boolean ballWillMove = true;
		boolean ballWillMoveY = true;
		int mult = 1;
		if (direction)
			mult = -1;
			
		
		World universe = new World(color, direction);	
		Thread.sleep(5000);
		boolean flag = true;
		int n = 0;
				
		
		try {
			MainComm theForceDef = new MainComm(1);

			n=0;
			int init = (int) Angle.to255(Angle.toRange2PI(universe.getAttackerDir().getOrientation()));
			theForceDef.sendMessage(CommandNames.UPDATEANGLE, init, 0);
			Vector lastInstruction = new Vector(new Point(0,0),0,0);
			double lastPoint = 0;
			Point ballPos = universe.getBallPos();
			Point robotPos = universe.getDefenderPos();
			Point lastBall = new Point (ballPos.getX()+5,0);
			while(ballWillMove){
				while (Math.abs(ballPos.getY()-robotPos.getY())>=10) {
					
					Point target = new Point(robotPos.getX(), ballPos.getY()); 
					if (target.getY() > 184 || target.getY() < 67) {
						withinBoundaries = false;
						//System.out.println("Hi");
					}
					else {
						//System.out.println("Bye");
						withinBoundaries = true;
					}
					Vector v = new Vector(robotPos,target);
					Vector step = new Vector(robotPos, v.getMagnitude()/2, mult*v.getOrientation());
					Command cmd= new Command(CommandNames.DONOTHING,0,0);
					if (n>0){
						System.out.println(ballPos.getY());
						System.out.println(n);
						if(lastPoint != ballPos.getY() && withinBoundaries){
							cmd = DefenderQueue.translate(step);
						}
					}
					else{
						if(withinBoundaries)
							cmd = DefenderQueue.translate(step);
					}
					lastInstruction = step;
					lastPoint = ballPos.getY();
					
					if(cmd.getCommand()!=CommandNames.DONOTHING){
						System.out.println(cmd.getCommand());
						theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					}
					lastBall = ballPos;
					ballPos = universe.getBallPos();
					robotPos = universe.getDefenderPos();
					n++;
				}
				if (Math.abs(ballPos.getX() - robotPos.getX()) <= 10)
					ballWillMove = false;
			
			}
			lastPoint = ballPos.getY()
			theForceDef.sendMessage(CommandNames.EXIT, 0, 0);
			theForceDef.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
