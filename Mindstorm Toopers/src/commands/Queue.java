package commands;

import java.util.ArrayList;
import java.util.ArrayList;
import geometry.*;

public class Queue{

    /* Need to: 
	    - Finish implementing Kick
	    - Finish calculation of angle
	    - Have someone check enums
	*/

    private ArrayList<Command> commandList;    
    private static int MAX = 900;
    private double previousAngle;
    private Vector lastVector;

	
    public Queue(double initialAngle) {
        commandList = new ArrayList<Command>();
        previousAngle = initialAngle;
    }
    
    public void add(Vector v) {
        commandList.add(translate(v));
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
        return commandList.remove(0);
    }
    
    public Command translate(Vector v) {
        CommandNames e;

        int speed = MAX;
        int distAngle;
        
        if (v.getMagnitude() == 0) {
            e = CommandNames.CHANGEANGLE;
            distAngle = 0; //To do
            previousAngle = 0; //To do
        }
        else if (true/*something representing a kick*/) {
            e = CommandNames.KICK;
            speed = MAX;
        }
        else {
            e = CommandNames.MOVEFORWARD;
            distAngle = (int)(v.getMagnitude()/1.25);
        }
        
        if (v.getMagnitude() == 0) {
            e = CommandNames.CHANGEANGLE;
            distAngle = 0; //To do
            previousAngle = 0; //To do
        }
        else if (true /* 1 something representing a kick*/) {
            e = CommandNames.KICK;
            speed = MAX;
            distAngle = 0; //TODO
        }
        else {
            e = CommandNames.MOVEFORWARD;
            distAngle = (int)(v.getMagnitude()/1.25);
        }
        this.lastVector = v;

        return new Command(e, speed, distAngle);
        
    }
}
