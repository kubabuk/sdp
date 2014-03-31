package strategy;

import geometry.Point;
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
		this.lastag = new Goal(new Point(0,0),CommandNames.DONOTHING,false,false) ;
		this.lastdg = new Goal(new Point(0,0),CommandNames.DONOTHING,false,false) ;
	}
	
	
	public void update()
	{
		
		// get goal from Strategy
//		System.out.println("The last goal before feeding is " + this.lastag.toString());
		
		this.ag = this.a.getGoal(this.lastag);
//		System.out.println("will try if statement");
		// feed it to Judge
		if (!this.ag.isNull())
		{	
//			System.out.println("The Goal in AI is not null");
			MoveA.makeCommands(this.w, this.ag, this.aq);
			this.lastag = this.ag;
		}
		// if it passes send it to Move
		// Move adds Commands to the queue
		
		
		
	}
	
	
}
