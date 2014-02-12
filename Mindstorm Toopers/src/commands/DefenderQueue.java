package commands;

import java.util.ArrayList;
import java.util.ArrayList;
import geometry.*;
import comms.CommandNames;

//TODO: Filter out repeating commands.

public class DefenderQueue {

	private ArrayList<Command> commandList;    
    private static int MAX = 900;
    private double previousAngle;
    private Vector lastVector;

	
    public DefenderQueue(double initialAngle) {
        commandList = new ArrayList<Command>();
        previousAngle = initialAngle;
    }
    
    public void add(Vector v) {
        //commandList.add(translate(v));
        //System.out.println("new vector added to the queue");
        
        CommandNames e;

        int speed = MAX; 
        int distAngle;
        //if(v.getMagnitude()!=previousAngle){
        //e = CommandNames.CHANGEANGLE;
        double angle = Angle.toRange2PI(v.getOrientation());
        if(angle<=Math.PI){
        	//angle = Angle.toRange2PI(angle-Math.PI/2);
        	//distAngle = (int) Angle.to255(angle);
            //commandList.add(new Command(e, speed, distAngle));
            if (v.getMagnitude() != 0) { 
                e = CommandNames.MOVEFORWARD;
                distAngle = (int)(v.getMagnitude()/1.25);
                commandList.add(new Command(e, speed, distAngle));
            }
            
        }else
        	//angle = Angle.toRange2PI(angle+Math.PI/2);
        	//distAngle = (int) Math.toDegrees(v.getMagnitude());
        	//commandList.add(new Command(e, speed, distAngle));
        	if (v.getMagnitude() != 0) { 
                e = CommandNames.MOVEBACKWARD;
                distAngle = (int)(v.getMagnitude()/1.25);
                commandList.add(new Command(e, speed, distAngle));
            }
        //}
        
       
        this.lastVector = v;
        
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
    	//System.out.println("pull");
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
