package com.mydomain;



public class Commands {
	
	public enum CommandNames{
		 MOVE, KICK, CATCH, TURN, ABORT, EXIT
		}

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
