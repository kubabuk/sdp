package com.mydomain; 

import com.mydomain.Commands.CommandNames;

public class Defender {

	/**
	 * @param args
	 */
	private static Communicator mCont;
	public static void main(String[] args) {
		final Controller nxt = new Controller();
	 	
	
		mCont = new BComm(new MessageListener() {
     	public void receiveMessage(CommandNames op, int arg1, int arg2,int arg3, Communicator controler)
     	{
     	nxt.addCommand(new Commands(op,arg1,arg2,arg3));
     	System.out.println(op + " "+ arg1+" "+arg2+ " "+ arg3);
    
  	 	
     	}
    	});
	 
		   
		nxt.run();  

                              
	
		
	}

}
