package strategy;

import commands.*;
import comms.CommandNames;

import world.World;

public class AI {
	// This is an AI object which works with vision and generate two queues of commands as output
	
	private World w;
	private State lastattackerstate, lastdefenderstate, currentattackerstate, currentdefenderstate;
	public AttackerQueue aq;
	public DefenderQueue dq;
	public boolean flag;
	
	public AI(World w, AttackerQueue aq, DefenderQueue dq)
	{
		//this.lastattackerstate = new State(1);
		this.lastdefenderstate = new State(0);
		//this.currentattackerstate = new State(1);
		this.currentdefenderstate = new State(0);
		
		this.w = w;
		//this.aq = new Queue(w.getAttacker().getDir().getOrientation());
		//this.dq = new Queue(w.getDefender().getDir().getOrientation());
		this.dq = dq;
		this.flag = false;
	}
	
	public void update()
	{
		//this.currentattackerstate.update(w);
		this.currentdefenderstate.update(w);
		
		//StrategyA.getAction(currentattackerstate,w, aq);
		StrategyD.getAction(currentdefenderstate,w, dq, flag);
		
		System.out.println("second call");
		
	
		
		//this.lastattackerstate = this.currentattackerstate;
		this.lastdefenderstate = this.currentdefenderstate;
	}
	
	
	/*
	public Command attackerpull()
	{
		
		return aq.pull();
	}
	*/
	public Command defenderpull()
	{
		if(dq.isEmpty()){
			return new Command(CommandNames.DONOTHING,0,0);
		}else{
			return dq.pull();
		}
		
	}
	
	public void notInitial(){
		this.flag = true;
	}
	
}
