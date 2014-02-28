package comms;



import java.io.IOException;




import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Iterator;

import commands.CommandNames;


import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
//import message listener

/**
 *
 * This is the interface that will make the connection from the PC to the device
 *
 * Don't forget to set up the MAC of the brick here!
 *
 * @author andrewleith
 *
 */
public class MainComm implements Communicator {

	// password and mac settings
	private static final int max_retries = 5; // how many times to try connecting to brick before quitting
	private static final String friendly_name_Bot_1 = "GRP6 Bot 1";
	private static final String mac_of_brick_Bot_1 = "00:16:53:0A:96:91";
	private static final String friendly_name_Bot_2 = "GRP 6 Bot 2";
	private static final String mac_of_brick_Bot_2 = "00:16:53:08:DA:0F";

	//private static short[] old_operate = null;



	// variables
	private ArrayList<MessageListener> mListener = new ArrayList<MessageListener>();
	private NXTComm mComm;
	private OutputStream os;
	private InputStream is;
	private boolean connection_open = false;

	/**
	 * Opens connection with robot.
	 * @param listener the listener that will receive updates from the robot
	 * @throws NXTCommException if a connection cannot be established
	 */
	public MainComm(int r) throws IOException {
		try {
			mComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
		} catch (Exception e) {
			System.out.println("Cannot open Bluetooth.");
		}
		//Check if we are connecting to robot 1 or robot 2. This is the code for robot 1.
		if (r == 1) {
			NXTInfo info = new NXTInfo(NXTCommFactory.BLUETOOTH,friendly_name_Bot_1, mac_of_brick_Bot_1);
			System.out.println("Opening connection...");
			boolean repeat = true;
			int tries = 0;
			while (repeat && tries < max_retries) {
				tries++;
				try {
					mComm.open(info);
					repeat = false;
				} catch (Exception e) {
					System.out.println("Cannot establish connection. Is device on? Retry in 4 s. ("+(max_retries-tries)+" attempts left)");
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e1) {}
				}
			}
			if (repeat)
				throw new IOException("Cannot connect to brick!");
			System.out.println("Getting output stream...");
			os = mComm.getOutputStream();
			System.out.println("Getting input stream...");
			is = mComm.getInputStream();
			connection_open = true;
			// handle incoming connections in a new thread
			System.out.println("Starting listener...");
			new Thread() {
				public void run() {
					while (connection_open) {
						try {
							readNextMessage();
						} catch (IOException e) {
							System.out.println("Connection with device lost. Waiting 2 s and trying again...");
							try {
								sleep(2000);
							} catch (InterruptedException e1) {	}
						}
					}
				};
			}.start();
			System.out.println("Ready");
		}
		//This is the code for robot 2.
		if (r == 2) {
			NXTInfo info = new NXTInfo(NXTCommFactory.BLUETOOTH,friendly_name_Bot_2, mac_of_brick_Bot_2);
			System.out.println("Opening connection...");
			boolean repeat = true;
			int tries = 0;
			while (repeat && tries < max_retries) {
				tries++;
				try {
					mComm.open(info);
					repeat = false;
				} catch (Exception e) {
					System.out.println("Cannot establish connection. Is device on? Retry in 4 s. ("+(max_retries-tries)+" attempts left)");
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e1) {}
				}
			}
			if (repeat)
				throw new IOException("Cannot connect to brick!");
			System.out.println("Getting output stream...");
			os = mComm.getOutputStream();
			System.out.println("Getting input stream...");
			is = mComm.getInputStream();
			connection_open = true;
			// handle incoming connections in a new thread
			System.out.println("Starting listener...");
			new Thread() {
				public void run() {
					while (connection_open) {
						try {
							readNextMessage();
						} catch (IOException e) {
							System.out.println("Connection with device lost. Waiting 2 s and trying again...");
							try {
								sleep(2000);
							} catch (InterruptedException e1) {	}
						}
					}
				};
			}.start();
			System.out.println("Ready");
		}

	}
	/**
	 * Reads the next message from the input stream.
	 * @throws IOException
	 */
	private void readNextMessage() throws IOException {
		int b =is.read();
		if (b == -1)
			throw new IOException("ERROR");
		CommandNames op = CommandNames.values()[b];
		int args1 = is.read();
		int args2 = is.read();
		// notify all listeners
		Iterator<MessageListener> ml = mListener.iterator();
		while (ml.hasNext()) {
			ml.next().receiveMessage(op, args1, args2, MainComm.this);	
		}
	}
	//@Override
	public synchronized void sendMessage(CommandNames op, int args1, int args2) throws IOException {

	System.out.println(op +" sent command PEAK"+ args2);


	// send a message to device
	os.write(op.ordinal()); // write opcode
	os.write(args1);
	os.write(args2 );
	os.flush(); // send message
	}



	/**
	 * Send a message over bluetooth to the brick
	 * @param op the opcode of the mssage
	 * @param args the arguments
	 */
	//@Override
	public synchronized void sendMessage(CommandNames op, int args) throws IOException {

	}
	/**
	 * Gracefully close connection with brick
	 */
	@Override
	public void close() {
		try {
			connection_open = false;
			is.close();
			os.close();
			mComm.close();
		} catch (IOException e) {
			System.out.println("Error while closing connection with brick");
			e.printStackTrace();
		}

	}

	/**
	 * Registers listeners
	 */
	/*
	@Override
	public void registerListener(MessageListener listener) {
	if (!mListener.contains(listener))
	mListener.add(listener);

	}
	 */

}
