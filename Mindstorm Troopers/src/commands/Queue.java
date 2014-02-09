import java.util.ArrayList;

public class Queue {

    /* Need to: 
	    - Finish implementing Kick
	    - Finish calculation of angle
	    - Have someone check enums
	*/

    private ArrayList<Command> commandList;    
    private static int MAX = 900;
    private double previousAngle;
	
	
    public Queue(double initialAngle) {
        commandList = new ArrayList<Command>();
        previousAngle = initialAngle;
    }
    
    public void add(Vector v) {
        commandList.add(translate(v));
    }
    
    public Command pull() {
        return commandList.remove(0);
    }
    
    public Command translate(Vector v) {
        CommandNames e;
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
        
        return new Command(e, speed, distAngle);
        
    }
}
