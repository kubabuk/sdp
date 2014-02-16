import geometry.Angle;
import geometry.Point;

import java.io.IOException;

import world.World;

import commands.Command;
import comms.CommandNames;
import comms.MainComm;


public class DeathStar7 {
	public static World universe;
	
	public static void main (String[] args) throws InterruptedException{
		boolean color = args[0].equals("yellow");
		boolean direction = args[1].equals("right");
		boolean startFlag = false;
		
		universe = new World(color, direction);
		
		Thread.sleep(5000);
		
		Point ballPos = universe.getBallPos();
		Point robotPos = universe.getDefenderPos();
		
		double target = ballPos.getY();
		double source = robotPos.getY();
		double targetX = ballPos.getX();
		double initialX = ballPos.getX();
	
		
		try {
			MainComm theForceDef = new MainComm(1);
			int init = (int) Angle.to255(Angle.toRange2PI(universe.getDefenderDir().getOrientation()));
			theForceDef.sendMessage(CommandNames.UPDATEANGLE, init, 0);
			ballPos = universe.getBallPos();
			robotPos = universe.getDefenderPos();
			Command cmd= new Command(CommandNames.DONOTHING,0,0);
			
			while (!startFlag){
				targetX = universe.getBallPos().getX();
				System.out.println(targetX);
				System.out.println(initialX);
				if (Math.abs(targetX - initialX) > 20){
					System.out.println("GAH");
					startFlag = true;
				}
			}
			
			if (robotPos.getY() < 100){
				cmd = new Command(CommandNames.MOVEBACKWARD, 400, 90);
				theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
				Thread.sleep(1000);
			} else if (robotPos.getY() > 200){
				cmd = new Command(CommandNames.MOVEFORWARD, 400, 90);
				theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
				Thread.sleep(1000);
			}
			
			while(true)	{
				ballPos = universe.getBallPos();
				robotPos = universe.getDefenderPos();
				target = ballPos.getY();
				source = robotPos.getY();
				System.out.println(target);
				System.out.println(source);

				if(target<(source-50)){
					cmd = new Command(CommandNames.MOVEFORWARD, 400, 50);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(1000);
//					spin(target, source, -10, true);
				} else if(target<(source-40)){
					cmd = new Command(CommandNames.MOVEFORWARD, 400, 40);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(1000);
//					spin(target, source, -10, true);
				} else if(target<(source-30)){
					cmd = new Command(CommandNames.MOVEFORWARD, 400, 30);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(900);
//					spin(target, source, -10, true);
				} else if(target<(source-20)){
					cmd = new Command(CommandNames.MOVEFORWARD, 400, 20);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(500);
//					spin(target, source, -10, true);
				} else if(target<(source-10)){
					cmd = new Command(CommandNames.MOVEFORWARD, 400, 10);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(500);
//					spin(target, source, -10, true);
				} else if (target > (source+50)){
					cmd = new Command(CommandNames.MOVEBACKWARD, 400, 50);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(1000);
//					spin(target, source, 10, false);
				} else if (target > (source+40)){
					cmd = new Command(CommandNames.MOVEBACKWARD, 400, 40);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(1000);
//					spin(target, source, 10, false);
				} else if (target > (source+30)){
					cmd = new Command(CommandNames.MOVEBACKWARD, 400, 30);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(900);
//					spin(target, source, 10, false);
				}
				  else if (target > (source+20)){
					cmd = new Command(CommandNames.MOVEBACKWARD, 400, 20);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(500);
//					spin(target, source, 10, false);
				}
				  else if (target > (source+10)){
					cmd = new Command(CommandNames.MOVEBACKWARD, 400, 10);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(500);
//					spin(target, source, 10, false);
				  }
				else{}
			} 
		}
				
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
		}

	}
	
	public static void spin(double target, double source, double thresh, boolean less) {
		Point ballPos = universe.getBallPos();
		Point robotPos = universe.getDefenderPos();
		
		target = ballPos.getY();
		source = robotPos.getY();
		
		if (less) {
			while (target < (source + thresh)) {
				ballPos = universe.getBallPos();
				robotPos = universe.getDefenderPos();
				target = ballPos.getY();
				source = robotPos.getY();
			}	
		}
		else {
			while (target > (source + thresh)) {
				ballPos = universe.getBallPos();
				robotPos = universe.getDefenderPos();
				target = ballPos.getY();
				source = robotPos.getY();
			}
		}
	}
}