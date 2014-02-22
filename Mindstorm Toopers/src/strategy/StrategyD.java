package strategy;

import geometry.Point;
import commands.CommandNames;

import world.World;



public class StrategyD {
	//strategy for attacker
	private World w;
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
		
		Goal g = new Goal(new Point(0,0), CommandNames.MOVEFORWARD,false,false);
		return g;
	}
	
}
