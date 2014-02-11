
package commands;

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
        return this.speed;
    }
    
    public int getDistAngle() {
        return this.distAngle;
    }
}
