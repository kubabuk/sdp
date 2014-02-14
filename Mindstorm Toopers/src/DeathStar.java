import geometry.Angle;
import geometry.Vector;

import java.io.IOException;
import java.nio.ByteBuffer;
import comms.CommandNames;
import comms.*;
import strategy.*;
import commands.*;
import world.*;

public class DeathStar {

	public static void main (String[] args) throws InterruptedException{
		boolean color = args[0].equals("yellow");
		boolean direction = args[1].equals("right");


		World universe = new World(color, direction);
		Thread.sleep(7000);
		
		Vector od;
		double o = 0;
		for (int i = 1; i < 10; i++)
		{
			Thread.sleep(50);
			od = universe.getAttacker().getDir();
			o += od.getOrientation();
		}
		o = o/10;
		
		if (o>1.5)
		{
			o = 3.14;
		}
		else
		{
			o = 0;
		}
		universe.setIO(o);
		AttackerQueue aq = new AttackerQueue(o);//universe.getAttacker().getDir().getOrientation());
		DefenderQueue dq = new DefenderQueue(universe.getDefender().getDir().getOrientation());

		AI2 emperor = new AI2(universe,aq,dq);
		boolean flag = true;


		try {
			//MainComm theForceDef = new MainComm(1);
			MainComm theForceAttack = new MainComm(2);
			int init = (int) Angle.to255(Angle.toRange2PI(universe.getAttackerDir().getOrientation()));
			System.out.println("The initial angle is set to be "+init);
			theForceAttack.sendMessage(CommandNames.UPDATEANGLE, init, 0);
			
			System.out.println("initial command sent");
			while (flag) {
				emperor.update();
				
				System.out.println("The ball position is " + universe.getBall().getPos().getX()+" , "+universe.getBall().getPos().getY());
				
//				System.out.println("The ball is at "+universe.getBall().toString());
//				System.out.println("The attacker is at "+universe.getAttacker().toString());
//				System.out.println("The defender is at "+universe.getDefender().toString());
//				System.out.println("The max X,Y are " + universe.getMinX() + " , " + universe.getMinY());
				
				
				//Command cmdDefend = emperor.defenderpull();
				//Command cmdDefend = dq.pull();
				Command cmdAttack = aq.pull();
				System.out.println("command pulled");
				//System.out.println(cmdDefend.getCommand());

				//sendArgumentsDef[0] = cmdDefend.getSpeed();
				//sendArgumentsDef[1] = cmdDefend.getDistAngle();
				//if(!cmdDefend.isNothing()){

				//	theForceDef.sendMessage(cmdDefend.getCommand(), );

				//}

				if(!cmdAttack.isNothing()){
					theForceAttack.sendMessage(cmdAttack.getCommand(), 100, cmdAttack.getDistAngle());
				}
				
				//theForceDef.sendMessage(CommandNames.MOVEFORWARD, 200, 5);

				//theForceDef.sendMessage(CommandNames.KICK,250, 10);

				//theForceDef.sendMessage(CommandNames.CHANGEANGLE, 300, 5);

				//theForceDef.sendMessage(CommandNames.EXIT, 300, 5);



//				sendArgumentsAttk[0] = cmdAttack.getSpeed();
//				sendArgumentsAttk[1] = cmdAttack.getDistAngle();



//				theForceAttack.sendMessage(cmdAttack.getCommand(), sendArgumentsAttk);

//				theForceAttack.sendMessage(cmdAttack.getCommand(), 
//						cmdAttack.getSpeed(), cmdAttack.getDistAngle());

				//flag = false;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}




}
