package strategy;

import geometry.Point;
import geometry.Vector;
import commands.*;
import comms.CommandNames;
import world.Robot;
import world.World;

//import geometry.Point;
//import geometry.Vector;
//import commands.*;
//import comms.CommandNames;
//import world.Ball;
//import world.Robot;
//import world.World;

public class MoveA {
	
	public static void makeCommands(World w, Goal goal, Queue aq) {
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
		Vector balltogoal;
		Vector temp;
		Command cmd = moveto(robottopoint);
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
		else if (name.equals(CommandNames.KICK)) {
			if (leftq) { temp = new Vector(robotpos,lowerleft); System.out.println("Moving to Lower Left Quadrant");}
			else { temp = new Vector(robotpos,lowerright); System.out.println("Moving to Lower Right Quadrant");}
			cmd = moveto(temp);
			aq.add(cmd);
			if (leftq) { balltogoal = new Vector(robotpos,leftgoal); System.out.println("Change Angle to face left goal");} //If the attacker is on the left
			else { balltogoal = new Vector(robotpos,rightgoal); System.out.println("Change Angle to face right goal");} //If the attacker is on the right
			cmd = new Command(CommandNames.CHANGEANGLE,0,(int) balltogoal.getOrientation());
			aq.add(cmd);
			if (PLACEHOLDER/*TODO If enemy defender blocks trajectory of ball*/) {
				temp = new Vector(robotpos,upperleft);
				cmd = moveto(temp);
				aq.add(cmd);
				return;
			}
			else {
				cmd = new Command(CommandNames.KICK,0,0);
				System.out.println("Kick Command: Kick put into stack");
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
	
	public static Command moveto(Vector robottopoint) {
		int distance = (int) robottopoint.getMagnitude();
		int angle = (int) robottopoint.getOrientation();
		Command cmd = new Command(commands.CommandNames.MOVE,distance,angle);
		return cmd;
	}
//		//Initialise Variables
//		Robot robot = w.getAttacker();
//		Ball ball = w.getBall();
//		double temp = 0;
//		Vector vtemp;
//		boolean withcatcher = false;
//		//Point goal = w.getGoal().getPos();
//		double robotx = robot.getPos().getX();
//		double roboty = robot.getPos().getY();
//		double robotori = robot.getDir().getOrientation();
//		double ballx = ball.getPos().getX();
//		double bally = ball.getPos().getY();
//		double goalx = goal.getX();
//		double goaly = goal.getY();
//		//Variables for Angle Calculations
//		double opposite = 1.0;
//		double adjacent = 1.0;
//		int changeangle = 0;
//		int move = 0;
//		//Variables for Ball Interception Calculations
//		double ballspeed = 0;
//		double balldirection = 0;
//		double ballentrylocation = 0; //Y Co-ordinates
//		double ballxspeed = 0;
//		double ballyspeed = 0;
//		double timetohit = 0;
//		boolean PLACEHOLDER = true;
		//Point enemydefender = w.getEnemyDefender().getPos();
		
//		Command cmd = new Command(CommandNames.DONOTHING,0,0);
//		if (goal.getAbort()) {
//			cmd = new Command(CommandNames.ABORT,0,0);
//			aq.add(cmd);
//			return;
//		}
//		else {
//			//Intercept -- If ball not in quadrant -- Basically Defender basic movements
//			if (PLACEHOLDER/*If the ball is not in the attacker's quadrant*/) {
//				ballxspeed = ball.getDir().getX();
//				ballyspeed = ball.getDir().getY();
//				timetohit = Math.abs(roboty - bally) * ballyspeed;
//				move = (int) (timetohit * ballyspeed);
//				//Go Right
//				if (ballyspeed > 0) { cmd = new Command(CommandNames.MOVEFORWARD,move,(int) (90 - robotori)); }
//				//Go Left
//				else { cmd = new Command(CommandNames.MOVEBACKWARD,move,(int) (270 - robotori)); }
//				aq.add(cmd);
//				return;
//			}
//			//Catch or Manoeuvre -- If ball in quadrant && ball not moving
//			else if (PLACEHOLDER/*Ball in Quadrant and not moving (After Interception) within a few px*/) {
//				if (withcatcher/*Aligned towards the ball with 27.5px distance*/) {
//					cmd = new Command(CommandNames.CATCH,0,0);
//					aq.add(cmd);
//					return;
//				}
//				else if (withcatcher/*Not Aligned towards the ball*/) {
//					vtemp = new Vector(robot.getPos(),ball.getPos());
//					changeangle = (int) (vtemp.getOrientation() - robotori);
//					cmd = new Command(CommandNames.CHANGEANGLE,0,changeangle);
//					aq.add(cmd);
//					return;
//				}
//				else { //Without catcher
//					//Move somehow behind to ball to align with goal to kick??
//				}
//			}
//			//Aligned with goal, Aligned with Ricochet to Goal, Unaligned -- Ball Caught || ball not moving
//			else if (!ball.isMoving() || Caught/*Ball not moving/Caught*/) {
//				if (PLACEHOLDER/*Robot aligned to Ricochet into Goal*/) {
//					cmd = new Command(CommandNames.KICK,0,0);
//					aq.add(cmd);
//					return;
//				}
//				else if (PLACEHOLDER/*Robot aligned to Goal and no enemy blocking way*/) {
//					cmd = new Command(CommandNames.KICK,0,0);
//					aq.add(cmd);
//					return;
//				}
//				else /*Robot aligned to Goal and enemy blocking way*/ {
//					//Align robot to ricochet into goal
//					opposite = Math.abs(w.getAttackerFieldLeft().getPos().getY() - roboty);
//					adjacent = (Math.abs(goalx - robotx)) / 2;
//					changeangle = (int) Math.tan(opposite/adjacent);
//					if (PLACEHOLDER/*On Left of Field*/) { cmd = new Command(CommandNames.CHANGEANGLE,0,-changeangle); }
//					else /*On Right of Field*/ { cmd = new Command(CommandNames.CHANGEANGLE,0,changeangle); }
//					aq.add(cmd);
//					return;
//				}
//			}
			
			
			
			
//			if (PLACEHOLDER/*Robot has ball*/) {
//				if (PLACEHOLDER/*Robot aligned to ricochet into goal*/) {
//					cmd = new Command(CommandNames.KICK,0,0);
//					aq.add(cmd);
//					return;
//				}
//				if (PLACEHOLDER/*Robot aligned towards enemy goal*/) {
//					if (PLACEHOLDER/*Enemy Robot on same side as attacker*/) {
//						//Align robot to ricochet into goal
//						opposite = Math.abs(w.getAttackerFieldLeft().getPos().getY() - roboty);
//						adjacent = (Math.abs(goalx - robotx)) / 2;
//						changeangle = (int) Math.tan(opposite/adjacent);
//						if (PLACEHOLDER/*On Left of Field*/) { cmd = new Command(CommandNames.CHANGEANGLE,0,-changeangle); }
//						else /*On Right of Field*/ { cmd = new Command(CommandNames.CHANGEANGLE,0,changeangle); }
//						aq.add(cmd);
//						return;
//					}
//					else /*Enemy Robot not blocking*/ {
//						cmd = new Command(CommandNames.KICK,0,0);
//						aq.add(cmd);
//						return;
//					}
//				}
//				if (PLACEHOLDER/*Enemy Robot on same side as attacker)*/) {
//					//Move towards other side of quadrant
//					//Assuming split quadrant to left and right side
//					//Need size position of quadrant
//				}
//				else /*Enemy Robot on opposite side as attacker*/ {
//					opposite = Math.abs(robotx - goalx);
//					adjacent = Math.abs(roboty - goaly);
//					changeangle = (int) Math.tan(opposite/adjacent);
//					cmd = new Command(CommandNames.CHANGEANGLE,0,changeangle);
//				}
//			}
//			else /*Robot doesn't have ball*/ {
//				if (!withcatcher) {
//					//Manoeuvre around ball and kick
//				}
//				if (roboty == bally && (Math.abs(robotx - ballx) < 2.5) && withcatcher) /*2.5px (1cm) away from ball so it doesn't get hit.*/ {
//					//TODO cmd = new Command(CommandNames.CATCH,0,0);
//					aq.add(cmd);
//					return;
//				}
//				else /*Not near the ball*/ {
//					
//					opposite = Math.abs(roboty - bally);
//					adjacent = Math.abs(robotx - ballx) - 27.5; //25px from center of robot, 2.5px from ball so it doesn't get hit.
//					changeangle = (int) Math.tan(opposite/adjacent);
//					move = (int) Math.sqrt(Math.pow(opposite,2) + Math.pow(adjacent,2));
//					cmd = new Command(CommandNames.MOVEFORWARD,move,changeangle);
//					aq.add(cmd);
//					return;
//				}
//			}
	}