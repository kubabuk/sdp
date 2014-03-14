package strategy;

import geometry.Point;
import geometry.Vector;
import commands.*;
import world.Robot;
import world.World;

public class MoveD {

	public static void makeCommands(World w, Goal goal, Queue aq) {
		System.out.println("MoveD is called");
		// Changeable variables for our robot and enemy defender sizes
		int robotsize = 40;
		int enemyrobotsize = 40;
		int ballsize = 20;

		// Extract information from Goal
		commands.CommandNames name = goal.getMove();
		Point point = goal.getGoal();

		// Initialise Variables
		Robot robot = w.getDefender();
		Point robotpos = robot.getPos();
//		Robot enemydefender = w.getOtherDefender();
//		Point enemydefenderpos = enemydefender.getPos();
		Point ball = w.getBall().getPos();

		double robotori = robot.getDir().getOrientationDegrees();
		double robotx = robotpos.getX();
		double dtemp = 0;
		double dtemp2 = 0;

		Vector robottopoint;
		Vector robottoball;
		Vector balltogoal;
		Vector vtemp;
		Vector vtemp2;
		Vector vtemp3;

		Command cmd;

		boolean rightq = false;

		// Initialise Attacking points
		Point lowerleft = new Point(306, 76);
		Point upperleft = new Point(306, 152);
		Point lowerright = new Point(146, 76);
		Point upperright = new Point(146, 152);

		// Initialise Goals
		Point leftgoal = new Point(474, 114);
		Point rightgoal = new Point(0, 114);

		// Find Attacker Quadrant
		// If in the right quadrant
		if (robotx > 0 && robotx < 97) {
			rightq = true;
		}

		// If Goal is null, do nothing.
		if (goal.isNull()) {
			System.out.println("Null");
			return;
		}

		// If Goal is outside boundary, do nothing.
		// Hard Boundary check
		if ((rightq && !hardboundarycheckright(w, point))
				|| (!rightq && !hardboundarycheckleft(w, point))) {
			System.out
					.println("Hard Boundary Check: Goal point outside boundary");
			aq.add(new Command(CommandNames.DONOTHING, 0, 0));
			return;
		}
		System.out.println("Hard Boundary Check Passed");

		// Main If-Else to determine path
		if (goal.getAbort()) {
			cmd = new Command(commands.CommandNames.ABORT, 0, 0);
			System.out.println("Abort Command: Abort put into stack");
			aq.add(cmd);
		}

		if (name.equals(CommandNames.CATCH)) {
			robottoball = new Vector(robotpos, point);
			Vector movetopoint = movetoball(robottoball, robotsize, ballsize, robot);
			if (!rightq && !softboundarycheckleft(w,movetopoint.getDestination())) {
				aq.add(new Command(CommandNames.DONOTHING, 0, 0));
				System.out.println("");
				return;
			} else if (rightq && !softboundarycheckright(w,movetopoint.getDestination())) {
				aq.add(new Command(CommandNames.DONOTHING, 0, 0));
				return;
			} else {
				
				cmd = new Command(CommandNames.CHANGEANGLE, 0,
						(int) (robottoball.getOrientationDegrees() - robotori));

				System.out.println("Change Angle towards the ball "
						+ (robottoball.getOrientationDegrees() - robotori));
				aq.add(cmd);
				cmd = new Command(CommandNames.MOVE,(int)movetopoint.getMagnitude(),0);
				System.out.println("Move Command: Move to ball distance "
						+ cmd.getDistance());
				aq.add(cmd);
				cmd = new Command(CommandNames.CATCH, 0, 0);
				w.getBall().setCaught(true);
				System.out.println("Catch Command: Catch the ball ");
				aq.add(cmd);
			}
		}

		// Main Kicking Algorithm
		else if (name.equals(CommandNames.KICK)) {
			robottopoint = new Vector(robotpos,goal.getGoal());
			cmd = new Command(CommandNames.CHANGEANGLE, 0,
					(int) (robottopoint.getOrientationDegrees() - robotori));
			aq.add(cmd);
			cmd = new Command(CommandNames.KICK,0,0);
			aq.add(cmd);
			return;
			
		} else if (name.equals(CommandNames.MOVE)) {
			robottopoint = new Vector(robotpos, point);
			cmd = movetopoint(robottopoint, robot);
			System.out.println("Moving to (" + goal.getGoal().getX() + ","
					+ goal.getGoal().getY() + ")");
			aq.add(cmd);
			System.out.println(cmd.getCommand() + ", " + cmd.getDistance()
					+ ", " + cmd.getAngle());
			return;
		} else {
			System.out.println("The code is not working");
		}
	}

	// Boundary Checks

	public static boolean hardboundarycheckright(World w, Point point) {
		double pointx = point.getX();
		double pointy = point.getY();
		double maxy = w.getMaxY();

		// pointy restraints so robot doesn't run into wall
		if (pointx > 0 && pointx < 97 && pointy > 20 && pointy < maxy - 20) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean softboundarycheckright(World w, Point point) {
		System.out.println("Left Quad");
		double pointx = point.getX();
		// double pointy = point.getY();
		// pointy restraints so robot doesn't run into wall
		if (pointx > 20 && pointx < 77/* && pointy > 20 && pointy < 208 */) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean hardboundarycheckleft(World w, Point point) {
		double pointx = point.getX();
		double pointy = point.getY();
		double maxy = w.getMaxY();
		// pointy restraints so robot doesn't run into wall
		if (pointx > 377 && pointx < 454 && pointy > 20 && pointy < maxy - 20) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean softboundarycheckleft(World w, Point point) {
		System.out.println("Right Quad");
		double pointx = point.getX();
		// double pointy = point.getY();
		// pointy restraints so robot doesn't run into wall
		if (pointx > 397 && pointx < 434/* && pointy > 20 && pointy < 208 */) {
			return true;
		} else {
			return false;
		}
	}

	// Methods to easily call from main function
	public static Command movetopoint(Vector robottopoint, Robot robot) {
		int distance = (int) robottopoint.getMagnitude();
		int angle = (int) robottopoint.getOrientationDegrees();
		System.out.println("Robot Position = " + robottopoint.getOrigin());
		System.out.println("Angle robottopoint = " + angle);
		System.out.println("Robot Orientation = "
				+ robot.getDir().getOrientationDegrees());
		Command cmd = new Command(CommandNames.MOVE, distance,
				(int) (angle - robot.getDir().getOrientationDegrees()));
		return cmd;
	}

	// Method used to move towards ball, accounting in the robotsize so it does
	// not push the ball away.
	public static Vector movetoball(Vector robottoball, int robotsize,
			int ballsize, Robot robot) {
		double distance = robottoball.getMagnitude();
		double sizeadjustments = robotsize + ballsize;
		double ratio = (distance - sizeadjustments) / distance;
		double x = robottoball.getX() * ratio;
		double y = robottoball.getY() * ratio;
		Point newpoint = new Point(x,y);
		Vector newvector = new Vector(robot.getPos(),newpoint);
		return newvector;
	}

	// Calculates the range of interception the enemy defender robot can
	// intercept by not moving
	// Returns a Vector -- The origin, the left boundary and the destination the
	// right boundary.
	// Takes in the point of where the ball is, the enemy defender, the goal and
	// the size of the enemy robot (range)
	public static Vector getInterceptionRange(Point ball,
			Point enemydefenderpos, Point goal, int range) {
		// Initialise Variables
		double enemyrobotx = enemydefenderpos.getX();
		double enemyroboty = enemydefenderpos.getY();
		double leftrange = enemyroboty - (range / 2);
		double rightrange = enemyroboty + (range / 2);
		Point leftboundary = new Point(enemyrobotx, leftrange);
		Point rightboundary = new Point(enemyrobotx, rightrange);
		Vector lefttrajectoryboundary = new Vector(ball, leftboundary);
		Vector righttrajectoryboundary = new Vector(ball, rightboundary);
		double leftori = lefttrajectoryboundary.getOrientationDegrees();
		double rightori = righttrajectoryboundary.getOrientationDegrees();
		Vector defenderrange = new Vector(new Point(enemyrobotx, leftori),
				new Point(enemyrobotx, rightori));
		// Main function -- Lulzno everything's already computed
		return defenderrange;
	}
}