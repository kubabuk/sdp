import com.mydomain.Robot.CommandNames;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;


public class DefRobot {

	 private static Communicator mCont;
     public static void main(String[] args) {
     final Robot nxt = new Robot(true);
 
     			mCont = new BComm(new MessageListener() {
            	public void receiveMessage(CommandNames op, int [] args, Communicator controler)
            	{
            	nxt.addCommand(new Commands(op,args[0],5));
   	      	 	System.out.println(op);
            	}
     		});

     

    
        	nxt.run();                             
            
        	
            
        }

}
