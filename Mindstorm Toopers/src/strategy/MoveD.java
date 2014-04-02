package strategy;

import geometry.Vector;
import commands.*;
import world.Robot;
import world.World;

public class MoveD {

	public static void makeCommands(World w, Goal goal, Queue aq) {

		Robot robot = w.getAttacker();

		CommandNames name = goal.getMove();

		Vector robottopoint;
		Vector robottoball;

		double robotori;

		int robotsize = 40;
		int ballsize = 0;

		Command cmd;
		
		if (goal.isNull()) {
//			System.out.println("Null Goal, returning nothing");
			return;
		}
		
		if (goal.getAbort()) {
			cmd = new Command(commands.CommandNames.ABORT,0,0);
//			System.out.println("Abort Command: Abort put into stack");
			aq.add(cmd);
		}
		
		if (name.equals(CommandNames.KICK)) {

			// Creates a new Vector for the orientation
			
			robottoball = new Vector(robot.getPos(), goal.getGoal());

			// Gets the orientation of the robot

			robotori = robot.getDir().getOrientationDegrees();

			// Face towards the ball

			cmd = new Command(CommandNames.TURN, 0,
					(int) anglerecalculation(robottoball.getOrientationDegrees() - robotori));

//			System.out.println("Changing angle by: " + cmd.getAngle());

			aq.add(cmd);

			// Then Kick

			cmd = new Command(CommandNames.KICK, 0, 0);

//			System.out.println("Kick");

			aq.add(cmd);
			return;
		}
		
		if (!boundarycheck(w,goal)) {
//			System.out.println("Goal outside boundary");
			return;
		}
		
		if (goal.getMove().equals(CommandNames.MOVE)) {
			System.out.println("Sending move command.");
			// Gets point of Robot and where it wants to move to and makes it
			// into a vector
//			System.out.println("ROBOTS POS: " + robot.getPos());
			robottopoint = new Vector(robot.getPos(), goal.getGoal());

			// Assigns the Command via movetopoint method

			cmd = movetopoint(robottopoint, robot);

			// Add the command onto the queue.

			aq.add(cmd);

			// Prints out onto terminal for debugging purposes and then ends the
			// MoveA instance.
			
//			System.out.println("Robot Direction: " + robot.getDir().getOrientationDegrees());
//			System.out.println("Robot Position: " + robot.getPos().toString());
//			System.out.println("Goal Position: " + goal.getGoal().toString());
//			System.out.println("Moving distance: " + cmd.getDistance());
//			System.out.println("Angle to move in: " + cmd.getAngle());

			return;
		}

		if (name.equals(CommandNames.CATCH)) {

			// Creates a new Vector for the orientation

			robottoball = new Vector(robot.getPos(), goal.getGoal());

			// Gets the orientation of the robot

			robotori = robot.getDir().getOrientationDegrees();

			// Face towards the ball
			int angle = (int) anglerecalculation(robottoball.getOrientationDegrees() - robotori);
//			System.out.println("Turning angle: " + angle);
			cmd = new Command(CommandNames.TURN, 0, angle);

//			System.out.println("Change Angle towards the ball "
//					+ anglerecalculation(robotori - robottoball.getOrientationDegrees()));
			aq.add(cmd);

			// Calls movetoball which is a modified movetopoint but takes into
			// consideration the size of robot and ball

			cmd = movetoball(robottoball, robotsize, ballsize, robot);
//			System.out.println("Moving Distance: " + robottoball.getMagnitude());
			aq.add(cmd);

			cmd = new Command(CommandNames.CATCH, 0, 0);
			w.getBall().setCaught(true);
//			System.out.println("Catch");
			aq.add(cmd);
		}
	}

		

	// Calculates and returns a Command for moving from the current position to
	// a specified point
	public static Command movetopoint(Vector robottopoint, Robot robot) {
		int distance = (int) robottopoint.getMagnitude();
		int angle = (int) robottopoint.getOrientationDegrees();
//		System.out.println("Robot Position = " + robottopoint.getOrigin());
//		System.out.println("Angle robottopoint = " + angle);
//		System.out.println("Robot Orientation = "
//				+ robot.getDir().getOrientationDegrees());
		Command cmd = new Command(CommandNames.MOVE, distance,
				(int) anglerecalculation(angle - robot.getDir().getOrientationDegrees()));
		return cmd;
	}

	// Same as movetopoint, but takes into consideration the size of the robot
	// and the ball
	public static Command movetoball(Vector robottoball, int robotsize,
			int ballsize, Robot robot) {

		// This method assumes you face towards the ball first, hence a 0 angle

		int distance = (int) robottoball.getMagnitude();
		int sizeadjustments = robotsize + ballsize;
//		System.out.println("Robot Position = " + robot.getPos().toString());
		Command cmd = new Command(CommandNames.MOVE,
				distance - sizeadjustments, 0);
		return cmd;
	}

	public static double anglerecalculation(double angle) {
		if (angle > 180) {
//			return (-(angle - 180));
			return angle - 360;
		} else if (angle < -180) {
//			return (-(angle + 180));
			return angle + 360;
		}
		else
			return angle;
	}
	
	public static boolean boundarycheck(World w, Goal goal) {
		//dir = True; Right -- dir = False; Left
		boolean dir = w.getDirection();
		boolean check = false;
		double maxy = w.getMaxY();
		double miny = w.getMinY();
		double goaly = goal.getGoal().getY();
		double goalx = goal.getGoal().getX();
		
		
		if (goaly < maxy && goaly > miny) {
			
			
			System.out.println("Dir: " + dir);
			System.out.println("Goal X: " + goalx);
			System.out.println("First Boundary: " + w.getFirstBoundary());
			System.out.println("Second Boundary: " + w.getSecondBoundary());
			System.out.println("Third Boundary: " + w.getThirdBoundary());

			//Check for Left direction
			if (!dir && goalx  > w.getThirdBoundary() && goalx < w.getMaxX()) {
				check = true;
			}
			
			//Check for Right direction
			else if (dir && goalx < w.getFirstBoundary() && goalx > w.getMinX()) {
				check = true;
			}
			
			//Should be simple enough to figure out
			
			else {
//				System.out.println("This shouldn't be happening!");
			}
//			check = true;
		}
		return check;
	}
}