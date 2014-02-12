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
	public static void main (String[] args){
		boolean color = args[0].equals("yellow");
		boolean direction = args[0].equals("right");

		World universe = new World(color, direction);		
		boolean flag = true;
		int n = 0;


		try {
			MainComm theForceDef = new MainComm(1);

			n=0;
			int init = (int) Angle.to255(Angle.toRange2PI(universe.getAttackerDir().getOrientation()));
			theForceDef.sendMessage(CommandNames.UPDATEANGLE, init, 0);
			Point ballPos = universe.getBallPos();
			Point robotPos = universe.getDefenderPos();
			Point lastBall = new Point (ballPos.getX()+5,0);
			//while (Math.abs(ballPos.getY()-robotPos.getY())>=0 && !lastBall.equals(ballPos)) {
			while(flag)	{
				double target = ballPos.getY();
				double source = robotPos.getY();
				Command cmd= new Command(CommandNames.DONOTHING,0,0);

				if(target>source){
					cmd = new Command(CommandNames.MOVEFORWARD, 100, 100);
				}
				else {
					cmd = new Command(CommandNames.MOVEBACKWARD, 100, 100);
				}

				theForceDef.sendMessage(cmd.getCommand(), 100, cmd.getDistAngle());
			} 
			theForceDef.sendMessage(CommandNames.EXIT, 0, 0);
			theForceDef.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}