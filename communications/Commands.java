package communications;
import commands.*;

public class Commands {

	public CommandNames commandName;
	public int firstArg;
	public int secondArg;
	public Commands(CommandNames name, int firstArg, int secondArg)
	{
		this.commandName= name;
		this.firstArg = firstArg;
		this.secondArg = secondArg;
	}


}

