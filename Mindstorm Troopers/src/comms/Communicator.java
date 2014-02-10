import java.io.IOException;

/**
* This is a communicator. It may be used for abstractly passing messages via bluetooth or virtual
* devices.
*
* @author andrewleith
*
*/
public interface Communicator {



/**
* Asynchronously send a message to a device.
* @param op the opcode {@link opcode}
* @param args the arguments
* @throws IOException in case of error in the connection
*/
public void sendMessage(CommandNames op, byte ... args) throws IOException;


public void close();


}