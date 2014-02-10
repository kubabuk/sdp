import strategy.State;
import strategy.Strategy;



public class CommandCenter() {
    
    

    public static void main(String[] args) {
        String colour = args[0];

        Communicator attack = new MainComm();
        Communicator defend = new MainComm();        
        
        AI ai = new AI();        
        
        State attackerstate = new State(0);
    	State defenderstate = new State(0);
    	
        boolean flag = true;

        while(flag) {        
        	
        	attackerstate.update();
        	defenderstate.update();
        	
        	StrategyA.getAction(attackerstate,w);
        	StrategyD.getAction(defenderstate,w);
        	//w is the world object
        	
        	//
        	//
        	
        	attack.sendmessage();
            
            attack.send(commandAttack.pull(blah));
            defend.send(commandDefend.pull(blah));            
            
            if (something)
                flag = false;
        }
    }

}
