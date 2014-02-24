package strategy;

import geometry.Point;
import commands.CommandNames;

import world.Ball;
import world.Robot;
import world.World;



public class StrategyD {
	//strategy for attacker
	private static World w;
	private int State;
	
	public StrategyD(World w)
	{
		this.w = w;
		this.State = 0;
	}
		
	public void setState(int State)
	{
		this.State = State;
	}
	
	public int getState()
	{
		return this.State;
	}
	
	public static Goal getGoal()
	{
		Robot defRobot = w.getDefender();
		Ball ball = w.getBall();
		
		Point goalTop = w.getGoalTop();
		Point goalBottom = w.getGoalBottom();
		Point destination;
		CommandNames cmd;
		boolean ballInFront;
		
		// right side of pitch from cameras perspective
		if (!w.getDirection()) {
			// if robot is between ball and own goal and robot is facing the opposite goal.
			if ((ball.getPos().getX() > defRobot.getPos().getX()) && Math.abs(defRobot.getDir().getOrientation() - 180) <= 10) {
				if (ball.getPos().getY() < goalBottom.getY()) {
					destination = new Point(defRobot.getPos().getX(), goalBottom.getY() + 10*2.2);
					cmd = CommandNames.MOVE;
				}
				else if (ball.getPos().getY() > goalTop.getY()) {
					destination = new Point(defRobot.getPos().getX(), goalTop.getY() - 10*2.2);
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
			}
			// if ball is between robot and own goal and robot is facing ball
			else if ((ball.getPos().getX() < defRobot.getPos().getX()) && Math.abs(defRobot.getDir().getOrientation() - 10) <= 10) {
				destination = new Point(50, ball.getPos().getY());
				cmd = CommandNames.MOVE;
			}
			// if ball is between robot and own goal and robot is not facing ball
			else {
				destination = ball.getPos();
			}
		}
		else {
			// if robot is between ball and own goal and robot is facing the opposite goal.
			if ((ball.getPos().getX() < defRobot.getPos().getX()) && Math.abs(defRobot.getDir().getOrientation() - 180) <= 10) {
				if (ball.getPos().getY() < goalBottom.getY()) {
					destination = new Point(defRobot.getPos().getX(), goalBottom.getY() + 10*2.2);
					cmd = CommandNames.MOVE;
				}
				else if (ball.getPos().getY() > goalTop.getY()) {
					destination = new Point(defRobot.getPos().getX(), goalTop.getY() - 10*2.2);
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
			}
			// if ball is between robot and own goal and robot is facing ball
			else if ((ball.getPos().getX() > defRobot.getPos().getX()) && Math.abs(defRobot.getDir().getOrientation() - 10) <= 10) {
				destination = new Point(50, ball.getPos().getY());
				cmd = CommandNames.MOVE;
			}
			// if ball is between robot and own goal and robot is not facing ball
			else {
				destination = ball.getPos();
			}
		}
		
		
		
		//Goal g = new Goal(new Point(0,0), CommandNames.MOVE,false,false); 
		return new Goal(destination, cmd, false);
		
	}
	
}
