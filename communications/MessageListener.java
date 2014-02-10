package communications;

import commands.*;
/**
* Implement this interface if you want to receive messages from a {@link Communicator}
*
* @author andrew 
*
*/
public interface MessageListener {

/**
* This method will be called when a new message is available.
* @param op the opcode of the message
* @param args the arguments of the message
* @param controller which controller is returning the result
*/
public void receiveMessage(CommandNames op, byte[] args, Communicator controller);

}

