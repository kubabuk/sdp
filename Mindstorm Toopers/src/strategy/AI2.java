package strategy;

import commands.Command;
import commands.AttackerQueue;
import commands.DefenderQueue;
import comms.CommandNames;

import world.World;

public class AI2 {
	// This is an AI object which works with vision and generate two queues of commands as output
	
	private World w;
	private State lastattackerstate, lastdefenderstate, currentattackerstate, currentdefenderstate;
	public AttackerQueue aq;
	public DefenderQueue dq;
	
	
	public AI2(World w, AttackerQueue aq, DefenderQueue dq)
	{
		this.lastattackerstate = new State(1);
		//his.lastdefenderstate = new State(0);
		this.currentattackerstate = new State(1);
		//this.currentdefenderstate = new State(0);
		
		this.w = w;
		//this.aq = new Queue(w.getAttacker().getDir().getOrientation());
		//this.dq = new Queue(w.getDefender().getDir().getOrientation());
		//this.dq = dq;
		this.aq = aq;
	}
	
	public void update() throws InterruptedException
	{
		this.currentattackerstate.update(w);
		//this.currentdefenderstate.update(w);
		
		StrategyA.getAction(currentattackerstate,w, aq);
		//StrategyD.getAction(currentdefenderstate,w, dq);
		
		System.out.println("second call");
		
	
		
		//this.lastattackerstate = this.currentattackerstate;
		this.lastdefenderstate = this.currentdefenderstate;
	}
	
	
	
	public Command attackerpull()
	{
		
		if(aq.isEmpty()){
			return new Command(CommandNames.DONOTHING,0,0);
		}else{
			return aq.pull();
		}
		
	}
	
	public Command defenderpull()
	{
		if(dq.isEmpty()){
			return new Command(CommandNames.DONOTHING,0,0);
		}else{
			return dq.pull();
		}
		
	}
	
}
