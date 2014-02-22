package strategy;

import commands.*;
import comms.CommandNames;

import world.World;

public class AI {
	// This is an AI object which works with vision and generate two queues of commands as output
	
	private World w;
	private StrategyA a;
	private StrategyD d;

	public boolean flag;
	
	public AI(World w, Queue aq, Queue dq)
	{
		this.w = w;
		this.flag = false;
		this.a = new StrategyA(w);
	}
	
	
	public void update()
	{
		// get goal from Strategy
		// feed it to Judge
		// if it passes send it to Move
		// Move adds Commands to the queue
	}
	
	
}
