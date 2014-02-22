import lejos.nxt.Motor;
import lejos.robotics.navigation.OmniPilot;

import com.mydomain.Commands.CommandNames;

public class tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ControllerHolo nxt = new ControllerHolo();
		

		nxt.addCommand(new Commands(CommandNames.TURN,40,15,315));
		nxt.addCommand(new Commands(CommandNames.MOVE,40,30,270));
		nxt.addCommand(new Commands(CommandNames.TURN,100,360,0));
		nxt.run();

	}

}
