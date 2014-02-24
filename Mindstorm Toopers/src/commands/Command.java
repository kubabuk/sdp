package commands;

public class Command {
    private int distance;
    private int angle;
    private CommandNames command;
    
    public Command(CommandNames c, int distance, int angle) {
        this.command = c;
        this.distance = distance;
        this.angle = (int)((double)angle*255.0/360.0);
    }

    public CommandNames getCommand() {
        return this.command;
    }
    
    public int getDistance() {
    	//System.out.println("Speed set at " + speed);
        return this.distance;
    }
    
    public int getAngle() {
    	//System.out.println("Dist/Angle set as " + distAngle);
        return this.angle;
    }
    public boolean isNothing(){
    	return this.command.equals(CommandNames.DONOTHING);
    }
    
    
}
