package strategy;

import geometry.Point;
import geometry.Vector;
import commands.*;
import comms.CommandNames;
import world.Robot;
import world.World;

public class MoveA {
	
	public static void makeCommands(World w, Goal goal, Queue aq) {
		//Please input size of robot
		int robotsize = 40;
		
		//Extract information from Goal
		commands.CommandNames name = goal.getMove();
		Point point = goal.getGoal();
		
		//Initialise Variables
		Robot robot = w.getAttacker();
		Point robotpos = robot.getPos();
		Point ball = w.getBall().getPos();
		double robotori = robot.getDir().getOrientation();
		double robotx = robotpos.getX();
		double roboty = robotpos.getY();
		double pointx = point.getX();
		double pointy = point.getY();
		double diffx = robotx - pointx;
		double diffy = roboty - pointy;
		int distance = (int) Math.sqrt(diffx*diffx + diffy*diffy);
		Vector robottopoint = new Vector(robotpos,point);
		Vector robottoball = new Vector(robotpos,ball);
		Vector balltogoal;
		Vector temp;
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
			cmd = new Command(CommandNames.ABORT,0,0);
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
		else if (name.equals(CommandNames.KICK)) {
			if (leftq) { temp = new Vector(robotpos,lowerleft); System.out.println("Moving to Lower Left Quadrant");}
			else { temp = new Vector(robotpos,lowerright); System.out.println("Moving to Lower Right Quadrant");}
			cmd = movetopoint(temp);
			aq.add(cmd);
			if (leftq) { balltogoal = new Vector(robotpos,leftgoal); System.out.println("Change Angle to face left goal");} //If the attacker is on the left
			else { balltogoal = new Vector(robotpos,rightgoal); System.out.println("Change Angle to face right goal");} //If the attacker is on the right
			cmd = new Command(CommandNames.CHANGEANGLE,0,(int) balltogoal.getOrientation());
			aq.add(cmd);
			if (PLACEHOLDER/*TODO If enemy defender blocks trajectory of ball*/) {
				temp = new Vector(robotpos,upperleft);
				cmd = movetopoint(temp);
				aq.add(cmd);
				return;
			}
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
	
	public static boolean softboundarycheckleft(Point point) {
		double pointx = point.getX();
		if (pointx > 256 && pointx < 356) { return true; }
		else { return false; }
	}
	
	public static boolean hardboundarycheckright(Point point) {
		double pointx = point.getX();
		if (pointx > 96 && pointx < 196) { return true; }
		else { return false; }
	}
	
	public static boolean softboundarycheckright(Point point) {
		double pointx = point.getX();
		if (pointx > 116 && pointx < 176) { return true; }
		else { return false; }
	}
	
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
}