import geometry.Angle;
import geometry.Point;

import java.io.IOException;

import world.World;

import commands.Command;
import comms.CommandNames;
import comms.MainComm;


public class DeathStar7 {
	public static void main (String[] args) throws InterruptedException{
		boolean color = args[0].equals("yellow");
		boolean direction = args[1].equals("right");

		World universe = new World(color, direction);

		Thread.sleep(5000);
		try {
			MainComm theForceDef = new MainComm(1);
			int init = (int) Angle.to255(Angle.toRange2PI(universe.getDefenderDir().getOrientation()));
			theForceDef.sendMessage(CommandNames.UPDATEANGLE, init, 0);
			Point ballPos = universe.getBallPos();
			Point robotPos = universe.getDefenderPos();
			double target = ballPos.getY();
			double source = robotPos.getY();
			while(true)	{
				ballPos = universe.getBallPos();
				robotPos = universe.getDefenderPos();
				target = ballPos.getY();
				source = robotPos.getY();
				System.out.println(target);
				System.out.println(source);
				Command cmd= new Command(CommandNames.DONOTHING,0,0);

				if(target<(source-50)){
					cmd = new Command(CommandNames.MOVEFORWARD, 100, 50);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(1200);
				} else if(target<(source-40)){
					cmd = new Command(CommandNames.MOVEFORWARD, 100, 40);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(1100);
				} else if(target<(source-30)){
					cmd = new Command(CommandNames.MOVEFORWARD, 100, 30);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(900);
				} else if(target<(source-20)){
					cmd = new Command(CommandNames.MOVEFORWARD, 100, 20);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(700);
				} else if(target<(source-10)){
					cmd = new Command(CommandNames.MOVEFORWARD, 100, 10);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(500);
				} else if (target > (source+10)){
					cmd = new Command(CommandNames.MOVEBACKWARD, 100, 10);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(500);
				} else if (target > (source+20)){
					cmd = new Command(CommandNames.MOVEBACKWARD, 100, 20);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(700);
				} else if (target > (source+30)){
					cmd = new Command(CommandNames.MOVEBACKWARD, 100, 30);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(900);
				} else if (target > (source+40)){
					cmd = new Command(CommandNames.MOVEBACKWARD, 100, 40);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(1100);
				} else if (target > (source+50)){
					cmd = new Command(CommandNames.MOVEBACKWARD, 100, 50);
					theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
					Thread.sleep(1200);
				}
			} 
		}
				
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
		}

	}
}