package strategy;

import commands.CommandNames;

import geometry.Point;

public class Goal {
	private Point goal;
	private CommandNames move; 
	private boolean abort,isNull;
	
	public Goal(Point goal, CommandNames move, boolean abort, boolean isNull)
	{
		this.goal = goal;
		this.move = move;
		this.abort = abort;
		this.isNull = isNull;
	}
	
	public Goal(Point goal, CommandNames move, boolean abort)
	{
		this.goal = goal;
		this.move = move;
		this.abort = abort;
		this.isNull = false;
	}
	
	public Point getGoal()
	{
		return this.goal;
	}
	
	public CommandNames getMove()
	{
		return this.move;
	}
	
	public boolean getAbort()
	{
		return this.abort;
	}
	
	public boolean isNull()
	{
		return this.isNull;
	}
	
}