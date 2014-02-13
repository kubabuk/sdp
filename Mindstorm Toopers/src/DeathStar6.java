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


public class DeathStar6 {
	public static void main (String[] args) throws InterruptedException{
		boolean color = args[0].equals("yellow");
		boolean direction = args[1].equals("right");

		World universe = new World(color, direction);		
		boolean flag = true;
		int n = 0;

		Thread.sleep(5000);
		try {
			MainComm theForceDef = new MainComm(1);

			n=0;
			int init = (int) Angle.to255(Angle.toRange2PI(universe.getDefenderDir().getOrientation()));
			theForceDef.sendMessage(CommandNames.UPDATEANGLE, init, 0);
			Point ballPos = universe.getBallPos();
			Point robotPos = universe.getDefenderPos();
			double target = ballPos.getY();
			double source = robotPos.getY();
			//while (Math.abs(ballPos.getY()-robotPos.getY())>=10){// && !lastBall.equals(ballPos)) {
			while(true)	{
				ballPos = universe.getBallPos();
				robotPos = universe.getDefenderPos();
				target = ballPos.getY();
				source = robotPos.getY();
				System.out.println(target);
				System.out.println(source);
				Command cmd= new Command(CommandNames.DONOTHING,0,0);

				if(target<(source-5)){
					cmd = new Command(CommandNames.MOVEFORWARD, 100, 10);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
//					while (target < source){
//						
//						ballPos = universe.getBallPos();
//						robotPos = universe.getDefenderPos();
//						target = ballPos.getY();
//						source = robotPos.getY();
//						System.out.println("1");
//						System.out.println(ballPos.getY());
//						System.out.println(robotPos.getY());
//						
//					}
				}
				else if (target > (source+5)){
					cmd = new Command(CommandNames.MOVEBACKWARD, 100, 10);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
//					while (target > source){
//						System.out.println("2");
//						ballPos = universe.getBallPos();
//						robotPos = universe.getDefenderPos();
//						target = ballPos.getY();
//						source = robotPos.getY();
//						System.out.println(ballPos.getY());
//						System.out.println(robotPos.getY());
//					}
				}
				else {
//					cmd= new Command(CommandNames.DONOTHING,0,0);
//					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
//					while (Math.abs(target - source) <= 10){
//					
//						ballPos = universe.getBallPos();
//						robotPos = universe.getDefenderPos();
//						target = ballPos.getY();
//						source = robotPos.getY();
//						System.out.println("else");
//						System.out.println(ballPos.getY());
//						System.out.println(robotPos.getY());
//					}
				}
				Thread.sleep(500);
			} 
//			theForceDef.sendMessage(CommandNames.EXIT, 0, 0);
//			theForceDef.close();
		}
				
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
		}

	}
}