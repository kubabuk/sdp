package commands;

public class Command {
    private int distance;
    private int angle;
    private int direc;
    private CommandNames command;
    
    public Command(CommandNames c, int distance, int angle) {
        this.command = c;
        this.distance = distance;
        if (angle > 180) {
        	this.angle = 360 - angle;
        	this.direc = 0;
        }
        else {
        	this.angle = angle;
        	this.direc = 1;
        }
    }

    public CommandNames getCommand() {
        return this.command;
    }
    
    public int getDistance() {
    	//System.out.println("Speed set at " + speed);
        return this.distance;
    }
    
    public int getDirection() {
    	return this.direc;
    }
    
    public int getAngle() {
    	//System.out.println("Dist/Angle set as " + distAngle);
        return this.angle;
    }
    public boolean isNothing(){
    	return this.command.equals(CommandNames.DONOTHING);
    }
    
    
}
