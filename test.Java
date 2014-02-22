
import com.mydomain.Commands.CommandNames;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Controller nxt = new Controller();
		
		nxt.addCommand(new Commands(CommandNames.MOVE,100,100,45));
		nxt.addCommand(new Commands(CommandNames.MOVE,0,100,-90));
		nxt.addCommand(new Commands(CommandNames.MOVE,0,100,-130));
		nxt.addCommand(new Commands(CommandNames.TURN,0,100,45));

		
		//nxt.addCommand(new Commands(CommandNames.TURN,0,90,0));
		nxt.run();
	}

}
