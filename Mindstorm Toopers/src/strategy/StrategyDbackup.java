package strategy;

import geometry.Point;
import commands.CommandNames;

import world.Ball;
import world.Robot;
import world.World;



public class StrategyDbackup {
	//strategy for attacker
	private static World w;
	private int State;
	private Point leftgoal;
	private Point rightgoal;
	private static double goalTop;
	private static double goalBottom;
	
	public StrategyDbackup(World w)
	{
		this.w = w;
		this.State = 0;
		this.leftgoal = new Point(474,114);
		this.rightgoal = new Point(0,114);
		this.goalTop = leftgoal.getY()+31.5;
		this.goalBottom = rightgoal.getY()+31.5;
	}
		
	public void setState(int State)
	{
		this.State = State;
	}
	
	public int getState()
	{
		return this.State;
	}
	
	public static Goal getGoal(Goal lastgoal)
	{
		Robot defRobot = w.getDefender();
		Ball ball = w.getBall();
		Point destination;
		CommandNames cmd;
		boolean ballInFront;
		
		// right side of pitch from cameras perspective
		if (!w.getDirection()) {
			// if robot is between ball and own goal and robot is facing the opposite goal.
			if ((ball.getPos().getX() > defRobot.getPos().getX()) && Math.abs(defRobot.getDir().getOrientation() - 180) <= 10) {
				if (ball.getPos().getY() < goalBottom) {
					destination = new Point(defRobot.getPos().getX(), goalBottom + 10*2.2);
					cmd = CommandNames.MOVE;
				}
				else if (ball.getPos().getY() > goalTop) {
					destination = new Point(defRobot.getPos().getX(), goalTop - 10*2.2);
					cmd = CommandNames.MOVE;
				}
				else {
					destination = new Point(defRobot.getPos().getX(), ball.getPos().getY());
					cmd = CommandNames.MOVE;
				}	
			}
			// if robot is between ball and own goal and robot is facing its own goal
			else if ((ball.getPos().getX() > defRobot.getPos().getX()) && Math.abs(0 - defRobot.getDir().getOrientation())<= 10) {
				destination = ball.getPos();
				cmd = CommandNames.MOVE;
			}
			// if ball is between robot and own goal and robot is facing ball
			else if ((ball.getPos().getX() < defRobot.getPos().getX()) && Math.abs(defRobot.getDir().getOrientation() - 10) <= 10) {
				destination = new Point(50, ball.getPos().getY());
				cmd = CommandNames.MOVE;
			}
			// if ball is between robot and own goal and robot is not facing ball
			else {
				destination = ball.getPos();
				cmd = CommandNames.MOVE;
			}
		}
		else {
			// if robot is between ball and own goal and robot is facing the opposite goal.
			if ((ball.getPos().getX() < defRobot.getPos().getX()) && Math.abs(defRobot.getDir().getOrientation() - 180) <= 10) {
				if (ball.getPos().getY() < goalBottom) {
					destination = new Point(defRobot.getPos().getX(), goalBottom + 10*2.2);
					cmd = CommandNames.MOVE;
				}
				else if (ball.getPos().getY() > goalTop) {
					destination = new Point(defRobot.getPos().getX(), goalTop - 10*2.2);
					cmd = CommandNames.MOVE;
				}
				else {
					destination = new Point(defRobot.getPos().getX(), ball.getPos().getY());
					cmd = CommandNames.MOVE;
				}	
			}
			// if robot is between ball and own goal and robot is facing its own goal
			else if ((ball.getPos().getX() > defRobot.getPos().getX()) && Math.abs(0 - defRobot.getDir().getOrientation())<= 10) {
				destination = ball.getPos();
				cmd = CommandNames.MOVE;
			}
			// if ball is between robot and own goal and robot is facing ball
			else if ((ball.getPos().getX() > defRobot.getPos().getX()) && Math.abs(defRobot.getDir().getOrientation() - 10) <= 10) {
				destination = new Point(50, ball.getPos().getY());
				cmd = CommandNames.MOVE;
			}
			// if ball is between robot and own goal and robot is not facing ball
			else {
				destination = ball.getPos();
				cmd = CommandNames.MOVE;
			}
		}
		
		
		
		//Goal g = new Goal(new Point(0,0), CommandNames.MOVE,false,false); 
		Goal g = new Goal(destination, cmd, false);
		
		return judge(lastgoal,g);
	}
	
	
	public static Goal judge(Goal currentgoal, Goal newgoalG)
	{
		Goal newgoal = newgoalG;
		if (newgoal.getAbort())
		{
			//send abort command and pass the new goal
			return newgoal;
		}
		
		else if (newgoal.isNull())
		{
			
			
			return newgoal;
		}
		
		else 
		{
			// do judge and decide if we should send the command
			if (currentgoal.getMove() != newgoal.getMove())
			{
				return newgoal;
			}
			else
			{
				if (Point.pointDistance(currentgoal.getGoal(), newgoal.getGoal())< 10)
				{
					newgoal.setNull(true);
					return newgoal;
				}
				else
				{
					newgoal.setAbort(true);
					return newgoal;
				}
			}
				
		}
		

	}
}
