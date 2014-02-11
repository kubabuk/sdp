package commands;

import comms.CommandNames;

public class Command {
    private int speed;
    private int distAngle;
    private CommandNames command;
    
    public Command(CommandNames c, int speed, int distAngle) {
        this.command = c;
        this.speed = speed;
        this.distAngle = distAngle;
    }

    public CommandNames getCommand() {
        return this.command;
    }
    
    public int getSpeed() {
    	System.out.println("Speed set at " + speed);
        return this.speed;
    }
    
    public int getDistAngle() {
    	System.out.println("Dist/Angle set as " + distAngle);
        return this.distAngle;
    }
    public boolean isNothing(){
    	return this.command.equals(CommandNames.DONOTHING);
    }
    
    
}
