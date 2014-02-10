<<<<<<< HEAD
import java.util.ArrayList;

=======
package commands;

import java.util.ArrayList;
import geometry.*;
>>>>>>> AI
public class Queue {

    /* Need to: 
	    - Finish implementing Kick
	    - Finish calculation of angle
	    - Have someone check enums
	*/

    private ArrayList<Command> commandList;    
    private static int MAX = 900;
    private double previousAngle;
<<<<<<< HEAD
	
=======
	private Vector lastVector;
>>>>>>> AI
	
    public Queue(double initialAngle) {
        commandList = new ArrayList<Command>();
        previousAngle = initialAngle;
    }
    
    public void add(Vector v) {
        commandList.add(translate(v));
    }
    
<<<<<<< HEAD
=======
    public void addKick(){
    	commandList.add(new Command(CommandNames.KICK,MAX,0));
    }
<<<<<<< HEAD
>>>>>>> AI
=======
    public void doNothing(){
    	
    }
>>>>>>> AI
    public Command pull() {
        return commandList.remove(0);
    }
    
    public Command translate(Vector v) {
        CommandNames e;
<<<<<<< HEAD
        int speed;
        int distAngle;
        
        if (v.getMagnitude() == 0) {
            e = CHANGEANGLE;
            distAngle = 0; //To do
            previousAngle = 0; //To do
        }
        else if (1/*something representing a kick*/) {
            e = KICK;
            speed = MAX;
        }
        else {
            e = MOVEFORWARD;
            distAngle = (int)(v.getMagnitude()/1.25);
        }
        
=======
        int speed = MAX;
        int distAngle;
        
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
>>>>>>> AI
        return new Command(e, speed, distAngle);
        
    }
}
