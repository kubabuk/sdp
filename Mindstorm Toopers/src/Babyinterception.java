import geometry.Point;
import geometry.Vector;
import world.Robot;
import world.World;
import commands.Command;
import commands.CommandNames;
import commands.Queue;

public class Babyinterception {
	public static void intercept(World w, Queue dq) {
		Command cmd;
		if (w.getBall().getPos().getY() != w.getDefender().getPos().getY()) {
			double defenderx = w.getDefender().getPos().getX();
			double bally = w.getBall().getPos().getY();
			if (bally < 45) { bally = 45; }
			System.out.println("Ball Y: " + bally);
			if (bally > 210) { bally = 210; }
			System.out.println("Changed Y" + bally);
			Point robottopoint = new Point(defenderx,bally);
			cmd = movetopoint(new Vector(w.getDefender().getPos(),robottopoint), w.getDefender());
			dq.add(cmd);
			System.out.println("Intercepting to point: " + defenderx + ',' + bally);
			return;
		}
	}
	
	public static Command movetopoint(Vector robottopoint, Robot robot) {
		int distance = (int) robottopoint.getMagnitude();
		int angle = (int) robottopoint.getOrientationDegrees();
		System.out.println("Robot Position = " + robottopoint.getOrigin());
		System.out.println("Angle robottopoint = " + angle);
		System.out.println("Robot Orientation = " + robot.getDir().getOrientationDegrees());
		Command cmd = new Command(CommandNames.MOVE,(distance*255/360),angle);
		return cmd;
	}
}
