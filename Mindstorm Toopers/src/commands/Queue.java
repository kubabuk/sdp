package commands;

import java.util.ArrayList;
import geometry.*;


public class Queue{

    private ArrayList<Command> commandList;    
	
    public Queue() {
        commandList = new ArrayList<Command>();
    }
    
    public void add(Command cmd) {
        //commandList.add(translate(v));
        if (cmd.getCommand() == CommandNames.ABORT) {
            System.out.println("Clearing queue");
            commandList = new ArrayList<Command>();
            commandList.add(new Command(CommandNames.ABORT, 0, 0, 0));
        }
        else {
            System.out.println("New command added to the queue");
            commandList.add(cmd);
        }
    }
    

    public Command pull() {
    	//System.out.println("pull");
    	
    	if(!commandList.isEmpty()) {
    		Command c = commandList.get(0);
    		commandList.remove(0);
    		return c;
        }
    	else {
    		return new Command(CommandNames.DONOTHING,0,0,0);
    	}
        
    }
    
    public boolean isEmpty(){
    	return commandList.isEmpty();
    }
    
}
    
