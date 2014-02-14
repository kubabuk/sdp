package commands;

import java.util.ArrayList;
import java.util.ArrayList;
import geometry.*;
import comms.CommandNames;

public class AttackerQueue{

    /* Need to: 
	    - Finish implementing Kick
	    - Finish calculation of angle
	    - Have someone check enums
	*/

    private ArrayList<Command> commandList;    
    private static int MAX = 900;
    private double previousAngle;
    private Vector lastVector;

	
    public AttackerQueue(double initialAngle) {
        commandList = new ArrayList<Command>();
        previousAngle = initialAngle;
    }
    
    public void add(Vector v) {
        //commandList.add(translate(v));
        System.out.println("new vector added to the queue");
        
        CommandNames e;

        int speed = MAX; 
        int distAngle;
        
        if(v.getOrientation()!=previousAngle){
        	e = CommandNames.CHANGEANGLE;
        	distAngle = (int) Angle.to255(Angle.toRange2PI(v.getOrientation()));
        	System.out.println(distAngle);
        	commandList.add(new Command(e, speed, distAngle));
        }
        
        if (v.getMagnitude() != 0) { //TODO:: Decide when to MOVEFORWARD and when to move BACKWARD}
            e = CommandNames.MOVEFORWARD;
            distAngle = (int)(v.getMagnitude()/1.25);
            commandList.add(new Command(e, speed, distAngle));
        }
       
        
        
        this.lastVector = v;
        this.previousAngle = v.getOrientation();
    }
    

    public void addKick(){
    	commandList.add(new Command(CommandNames.KICK,MAX,0));
    }

    public void doNothing(){
    	
    }
    public void abort(){
    	commandList.add(new Command(CommandNames.ABORT,0,0));
    }
    public Command pull() {
    	System.out.println("pull");
    	if(!commandList.isEmpty()){
        Command c = commandList.get(0);
        commandList.remove(0);
        return c;}
    	else
    	{
    		return new Command(CommandNames.DONOTHING,0,0);
    	}
        

    }
    
    public boolean isEmpty(){
    	return commandList.isEmpty();
    }
    
    public Command translate(Vector v) {
        CommandNames e;

        int speed = MAX; 
        int distAngle;
        
        if (v.getMagnitude() == 0) {
            e = CommandNames.CHANGEANGLE;
            distAngle = (int) Math.toDegrees(v.getMagnitude()); //To do
            previousAngle = 0; //To do
        }
        else {
            e = CommandNames.MOVEFORWARD;
            distAngle = (int)(v.getMagnitude()/1.25);
        }
       
        this.lastVector = v;

        return new Command(e, speed, distAngle);
        
    }
}
