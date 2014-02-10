package strategy;

import commands.Command;
import commands.Queue;

import world.World;

public class AI {
	// This is an AI object which works with vision and generate two queues of commands as output
	
	private World w;
	private State lastattackerstate, lastdefenderstate, currentattackerstate, currentdefenderstate;
	private Queue aq,dq;
	
	
	public AI()
	{
		this.lastattackerstate = new State(1);
		this.lastdefenderstate = new State(0);
		this.currentattackerstate = new State(1);
		this.currentdefenderstate = new State(0);
		
		this.w = new World();
		this.aq = new Queue(w.getAttacker().getDir().getOrientation());
		this.dq = new Queue(w.getDefender().getDir().getOrientation());
	}
	
	public void update()
	{
		this.currentattackerstate.update(w);
		this.currentdefenderstate.update(w);
		
		if (currentattackerstate != lastattackerstate) aq = StrategyA.getAction(currentattackerstate,w);
		if (currentdefenderstate != lastdefenderstate) dq = StrategyD.getAction(currentdefenderstate,w);
		
	
		
		this.lastattackerstate = this.currentattackerstate;
		this.lastdefenderstate = this.currentdefenderstate;
	}
	
	
	
	public Command attackerpull()
	{
		
		return aq.pull();
	}
	
	public Command defenderpull()
	{
		return dq.pull();
	}
	
}
