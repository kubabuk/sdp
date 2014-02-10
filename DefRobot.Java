import com.mydomain.Robot.CommandNames;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;


public class DefRobot {


        	
            public static void main(String[] args) {
            final Robot nxt = new Robot(true);
            
            
    // add handling here. Bluetooth listener with all the command kicks. 
    // example (not sure about the next line)
    // smth.addBluetoothListener(new BluetoothListener){ // depends on how bluetooth works
    // public void messageReceived(Commands command (or CommandNames code, int arg1, int arg2)
    // {
    // nxt.addCommand(command); depending on how message is received this or line below;
    // nxt.addCommand(newCommands(code,arg1,arg2));
    // can add smth extra to print on screen when command is received.
    // sending message back should go here
    // });
    // }
    // 
            
            
            
            
            	Button.ENTER.addButtonListener(new ButtonListener() {
            		public void buttonPressed(Button b) {
            	
            		}

            		@Override
            		public void buttonReleased(Button b) { 
            			//nxt.addCommand(new Commands(CommandNames.KICK,900,5));
            			//nxt.addCommand(new Commands(CommandNames.MOVEBACKWARD,200,50));
                		nxt.addCommand(new Commands(CommandNames.CHANGEANGLE,75,360));
                	 	System.out.println('+');
            		}
            	});
            
        	nxt.run();                             
            
        	
            
        }

}
