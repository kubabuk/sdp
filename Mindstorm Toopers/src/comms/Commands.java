package comms;
import commands.CommandNames;

public class Commands {

	public CommandNames commandName;
	public int firstArg;
	public int secondArg;
	public int thirdArg;
	public Commands(CommandNames name, int firstArg, int secondArg, int thirdArg)
	{
		this.commandName= name;
		this.firstArg = firstArg;
		this.secondArg = secondArg;
		this.thirdArg = thirdArg;
	}


}
