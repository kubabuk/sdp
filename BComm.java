package com.mydomain;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.mydomain.Commands.CommandNames;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;


/**
 * This class is the handles sending/receiving messages from the PC.
 *
 * Keep in mind that first the Brick must be running before JControl connects to BControl.
 *
 * @author martinmarinov
 *
 */
public class BComm implements Communicator {

	// private variables
	private static InputStream is;
	private static OutputStream os;
	private static boolean running = false;
	private static int messages_so_far = 0;
	private static NXTConnection connection;

	/**
	 * Initializes the contorller with a listener
	 * @param listener
	 */
	public BComm(final MessageListener listener) {
		LCD.clear();
		LCD.drawString("Waiting for", 0, 0);
		LCD.drawString("Bluetooth...", 0, 1);
		// itnialize connection
		connection = Bluetooth.waitForConnection();
		LCD.clear(0);
		LCD.clear(1);
		LCD.drawString("Connection",0,0);
		LCD.drawString("established!",0,1);
		is = connection.openInputStream();
		os = connection.openOutputStream();
		running = true;
		// start the listener thread
		new Thread() {

			public void run() {
				while (running) {
					try {
						// wait for an instruction
						CommandNames op = CommandNames.values()[is.read()];
						int args1 = is.read();
						int args2 = is.read();
						int args3 = is.read();
						listener.receiveMessage(op, args1, args2,args3, BComm.this);
	
					} catch (IOException e) {
						LCD.clear();
						LCD.drawString("Lost!",0,0);
						e.printStackTrace();
						try {
							sleep(2000);
						} catch (InterruptedException e1) {	}
					}
				}

			};
		}.start();

	}

	/**
	 * Send a message to the PC
	 * @param op the opcode
	 * @param args the arguments
	 */
	@Override
	public void sendMessage(CommandNames op, int args1, int args2) throws IOException {
		os.write(op.ordinal()); // write opcode
		os.write(args1); // write number of args
		os.write(args2); // write args
		os.flush(); // send message
	}

	/**
	 * Close the stream and connection gracefully
	 */
	@Override
	public void close() {
		try {
			running = false;
			is.close();
			os.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
