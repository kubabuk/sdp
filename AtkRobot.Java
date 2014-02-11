


import com.mydomain.Robot.CommandNames;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;




public class AtkRobot {        	
		 private static Communicator mCont;
	     public static void main(String[] args) {
	     final Robot nxt = new Robot(false);

			mCont = new BComm(new MessageListener() {
     	public void receiveMessage(CommandNames op, int arg1, int arg2, Communicator controler)
     	{
     	nxt.addCommand(new Commands(op,arg1,arg2));
     	
      	 	System.out.println(op);
     	}
		});




 	nxt.run();                                 
	            
	        	
	            
	        }

	}
