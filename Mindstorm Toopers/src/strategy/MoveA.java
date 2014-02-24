package strategy;

import geometry.Point;
import geometry.Vector;
import commands.*;
import world.Robot;
import world.World;

public class MoveA {
	
	public static void makeCommands(World w, Goal goal, Queue aq) {
		//Changeable variables for our robot and enemy defender sizes
		int robotsize = 40;
		int enemyrobotsize = 40;
		
		//Extract information from Goal
		commands.CommandNames name = goal.getMove();
		Point point = goal.getGoal();
		
		//Initialise Variables
		Robot robot = w.getAttacker();
		Point robotpos = robot.getPos();
		Robot enemydefender = w.getOtherDefender();
		Point enemydefenderpos = enemydefender.getPos();
		Point ball = w.getBall().getPos();
		double robotori = robot.getDir().getOrientation();
		double robotx = robotpos.getX();
		double roboty = robotpos.getY();
		double pointx = point.getX();
		double pointy = point.getY();
		double diffx = robotx - pointx;
		double diffy = roboty - pointy;
		double dtemp = 0;
		double dtemp2 = 0;
		double interceptionrange = 2;
		int distance = (int) Math.sqrt(diffx*diffx + diffy*diffy);
		Vector robottopoint = new Vector(robotpos,point);
		Vector robottoball = new Vector(robotpos,ball);
		Vector balltogoal;
		Vector vtemp;
		Vector vtemp2;
		Vector vtemp3;
		Command cmd = movetopoint(robottopoint);
		Command donothing = new Command(CommandNames.DONOTHING,0,0);
		int angle = (int) Math.abs(robotori - robottopoint.getOrientation());
		boolean leftq = false;
		
		//Initialise Attacking points
		Point lowerleft = new Point(306,76);
		Point upperleft = new Point(306,152);
		Point lowerright = new Point(146,76);
		Point upperright = new Point(146,152);
		
		//Initialise Goals
		Point leftgoal = new Point(474,114);
		Point rightgoal = new Point(0,114);
		
		//DEBUG VARIABLE
		boolean PLACEHOLDER = true;
		
		//Find Attacker Quadrant
		if (robotx > 118 && robotx < 188) { leftq = true; }
		
		//If Goal is outside boundary, do nothing.
		if ((leftq && !hardboundarycheckleft(point)) || (!leftq && !hardboundarycheckright(point))) {
			System.out.println("Goal point outside boundary: Do Nothing put into stack");
			aq.add(donothing);
			return;
		}
		//Main If-Else to determine path
		if (goal.getAbort()) {
			cmd = new Command(commands.CommandNames.ABORT,0,0);
			System.out.println("Abort Command: Abort put into stack");
			aq.add(cmd);
		}
		else if (name.equals(CommandNames.EXIT)) {
			cmd = new Command(CommandNames.EXIT,0,0);
			System.out.println("Exit Command: Exit put into stack");
			aq.add(cmd);
			return;
		}
		else if (name.equals(CommandNames.CATCH)) {
			System.out.println("Move Command: Move to ball");
			cmd = movetoball(robottoball,robotsize);
			aq.add(cmd);
			cmd = new Command(CommandNames.CATCH,0,0);
			System.out.println("Catch Command: Catch the ball");
			aq.add(cmd);
		}
		//Main Kicking Algorithm
		else if (name.equals(CommandNames.KICK)) {
			//Check goal position and choose appropriate point
			if (leftq) { vtemp = new Vector(robotpos,lowerleft); System.out.println("Moving to Lower Left Quadrant");}
			else { vtemp = new Vector(robotpos,lowerright); System.out.println("Moving to Lower Right Quadrant");}
			//Move to chosen kicking position
			cmd = movetopoint(vtemp);
			aq.add(cmd);
			//Check goal position and change to appropriate angle
			if (leftq) { balltogoal = new Vector(robotpos,leftgoal); System.out.println("Change Angle to face left goal");} //If the attacker is on the left
			else { balltogoal = new Vector(robotpos,rightgoal); System.out.println("Change Angle to face right goal");} //If the attacker is on the right
			cmd = new Command(CommandNames.CHANGEANGLE,0,(int) balltogoal.getOrientation());
			aq.add(cmd);
			//Algorithm to check if the ball would be intercepted if defender doesn't move
			//Check goal position and get a Vector defining the interception range
			if (leftq) { vtemp = getInterceptionRange(ball,enemydefenderpos,leftgoal,enemyrobotsize); }
			else { vtemp = getInterceptionRange(ball,enemydefenderpos,rightgoal,enemyrobotsize); }
			//Calculations if the ball would be intercepted
			vtemp2 = new Vector(balltogoal.getOrigin(),vtemp.getOrigin());
			vtemp3 = new Vector(balltogoal.getOrigin(),vtemp.getDestination());
			dtemp = vtemp3.getOrientation() - balltogoal.getOrientation();
			dtemp2 = vtemp2.getOrientation() - balltogoal.getOrientation();
			//If the ball would be intercepted
			if (dtemp2 < 0 && dtemp > 0) {
				//Move to new point
				vtemp = new Vector(robotpos,upperleft);
				cmd = movetopoint(vtemp);
				aq.add(cmd);
				return;
			}
			//Else kick
			else {
				cmd = new Command(CommandNames.KICK,0,0);
				aq.add(cmd);
				return;
			}
			return;
		}
		else if (name.equals(CommandNames.MOVE)) {
			System.out.println("Move Command: Move put into stack");
			aq.add(cmd);
			return;
		}
	}
	
	public static boolean hardboundarycheckleft(Point point) {
		double pointx = point.getX();
		if (pointx > 236 && pointx < 376) { return true; }
		else { return false; }
	}
	
//	Unused Code
//	public static boolean softboundarycheckleft(Point point) {
//		double pointx = point.getX();
//		if (pointx > 256 && pointx < 356) { return true; }
//		else { return false; }
//	}
	
	public static boolean hardboundarycheckright(Point point) {
		double pointx = point.getX();
		if (pointx > 96 && pointx < 196) { return true; }
		else { return false; }
	}
	
//	Unusued Code
//	public static boolean softboundarycheckright(Point point) {
//		double pointx = point.getX();
//		if (pointx > 116 && pointx < 176) { return true; }
//		else { return false; }
//	}
	
	public static Command movetopoint(Vector robottopoint) {
		int distance = (int) robottopoint.getMagnitude();
		int angle = (int) robottopoint.getOrientation();
		Command cmd = new Command(CommandNames.MOVE,distance,angle);
		return cmd;
	}
	
	public static Command movetoball(Vector robottoball, int robotsize) {
		int distance = (int) robottoball.getMagnitude();
		int angle = (int) robottoball.getOrientation();
		Command cmd = new Command(CommandNames.MOVE,(distance - robotsize),angle);
		return cmd;
	}
	
	//Calculates the range of interception the enemy defender robot can intercept by not moving
	//Returns a Vector -- The origin, the left boundary and the destination the right boundary.
	//Takes in the point of where the ball is, the enemy defender, the goal and the size of the enemy robot (range)
	public static Vector getInterceptionRange(Point ball, Point enemydefenderpos, Point goal, int range) {
		//Initialise Variables
		double enemyrobotx = enemydefenderpos.getX();
		double enemyroboty = enemydefenderpos.getY();
		double leftrange = enemyroboty - (range/2);
		double rightrange = enemyroboty + (range/2);
		Point leftboundary = new Point(enemyrobotx,leftrange);
		Point rightboundary = new Point(enemyrobotx,rightrange);
		Vector lefttrajectoryboundary = new Vector(ball,leftboundary);
		Vector righttrajectoryboundary = new Vector(ball,rightboundary);
		double leftori = lefttrajectoryboundary.getOrientation();
		double rightori = righttrajectoryboundary.getOrientation();
		Vector defenderrange = new Vector(new Point(enemyrobotx,leftori),new Point(enemyrobotx,rightori));
		//Main function -- Lulzno everything's already computed
		return defenderrange;
	}
}