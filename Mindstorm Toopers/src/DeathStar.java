import geometry.Angle;
import geometry.Point;
import geometry.Vector;

import java.io.IOException;
import java.nio.ByteBuffer;
import commands.*;
import strategy.*;
import comms.*;
import world.*;

public class DeathStar {

	public static void main (String[] args) throws InterruptedException, IOException{
		boolean color = args[0].equals("yellow");
		boolean direction = args[1].equals("right");

		
		//World universe = new World(color, direction);
		//Thread.sleep(3000);
		Queue aq = new Queue();//universe.getAttacker().getDir().getOrientation());
		Queue dq = new Queue();

		
		//test code
//		aq.add(new Command(CommandNames.MOVE, 117, 0));
//		aq.add(new Command(CommandNames.CATCH, 10, 15));
//		aq.add(new Command(CommandNames.KICK, 10, 15));
//		
//		aq.add(new Command(CommandNames.CATCH, 10, 15));
//		aq.add(new Command(CommandNames.KICK, 10, 15));
//
//		aq.add(new Command(CommandNames.CATCH, 10, 15));
//		aq.add(new Command(CommandNames.KICK, 10, 15));
		//dq.add(new Command(CommandNames.CATCH, 0, 0));
//		aq.add(new Command(CommandNames.ABORT, 0, 90));
//		aq.add(new Command(CommandNames.MOVE, 100, 60));
		//aq.add(new Command(CommandNames.CHANGEANGLE, 0, -90));
		int count = 0;
		
		
		//test code
		
		//AI emperor = new AI(universe,aq,dq);
		boolean flag = true;
		

		//MainComm theForceDef = new MainComm(1);
		MainComm theForceAttack = new MainComm(1);
			
			
		while (flag) {
			//emperor.update();
				
			//System.out.println("The ball position is " + universe.getBall().getPos().getX()+" , "+universe.getBall().getPos().getY());
				
//				System.out.println("The ball is at "+universe.getBall().toString());
//				System.out.println("The attacker is at "+universe.getAttacker().toString());
//				System.out.println("The defender is at "+universe.getDefender().toString());
//				System.out.println("The max X,Y are " + universe.getMinX() + " , " + universe.getMinY());
				
			count = count + 1;
			if (count == 4)
			{
				aq.add(new Command(CommandNames.MOVE, 50, 0));
				aq.add(new Command(CommandNames.TURN, 0, 120));
				theForceAttack.sendMessage(CommandNames.KICK, 0,0, 0);
			}
			
			
			//dq.add(new Command(CommandNames.CATCH, 0, 0));
			Command cmdAttack = aq.pull();
			//Command cmdDefend = dq.pull();
			//System.out.println("command pulled");
			//System.out.println(cmdDefend.getCommand());

			//sendArgumentsDef[0] = cmdDefend.getSpeed();
			//sendArgumentsDef[1] = cmdDefend.getDistAngle();
			//if(!cmdDefend.isNothing()){

			//	theForceDef.sendMessage(cmdDefend.getCommand(), );

			//}

			if(!cmdAttack.isNothing()){
				System.out.println("Attempting to send message");
				if (cmdAttack.getAngle()>180)
				theForceAttack.sendMessage(cmdAttack.getCommand(), cmdAttack.getDistance(),cmdAttack.getAngleDirec(), cmdAttack.getAngle()-180);
				else theForceAttack.sendMessage(cmdAttack.getCommand(), cmdAttack.getDistance(),cmdAttack.getAngleDirec(), cmdAttack.getAngle());
				
			}
			
				
		
//				theForceAttack.sendMessage(cmdAttack.getCommand(), sendArgumentsAttk);

//				theForceAttack.sendMessage(cmdAttack.getCommand(), 
//						cmdAttack.getSpeed(), cmdAttack.getDistAngle());
			
				if (count == 10) {	flag = false;}
			}

		
	}




}
