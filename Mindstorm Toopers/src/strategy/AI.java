package strategy;

import commands.*;
import world.World;

public class AI {
	// This is an AI object which works with vision and generate two queues of commands as output
	
	private World w;
	private StrategyA a;
	private StrategyD d;
	private Goal ag,dg,lastag,lastdg; //goals and last goals
	private Queue aq,dq;
		
	public boolean flag;
	
	public AI(World w, Queue aq, Queue dq)
	{
		this.w = w;
		this.flag = false;
		this.a = new StrategyA(w);
		this.aq = aq;
		this.dq = dq;
	}
	
	
	public void update()
	{
		
		// get goal from Strategy
		this.ag = a.getGoal();
		
		// feed it to Judge
		if (JudgeA.judge(lastag,ag))
		{
			MoveA.makeCommands(this.w, ag, aq);
			this.lastag = ag;
		}
		// if it passes send it to Move
		// Move adds Commands to the queue
		
		
		
	}
	
	
}
