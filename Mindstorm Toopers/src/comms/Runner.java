package comms;
import java.io.IOException;
import java.io.OutputStream;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;


public class Runner {
	public static void main(String[] args) throws IOException {
		
		Communicator comms;
		comms = new MainComm();
		byte m = 0;
		
		comms.sendMessage(CommandNames.PLAY_SOUND, m);
	}
		
		
		
		
						
	}
	
	
	
	

