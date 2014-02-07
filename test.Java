import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.comm.BTConnection;;

public class Test {


        	
            public static void main(String[] args) {
            final Robot nxt = new Robot();
            	Button.ENTER.addButtonListener(new ButtonListener() {
            		public void buttonPressed(Button b) {
            	
            		}

            		@Override
            		public void buttonReleased(Button b) { // change the commands below to test different things
            			nxt.addCommand(new Commands(CommandNames.KICK,200,5));
                		nxt.addCommand(new Commands(CommandNames.MOVEBACKWARD,200,5));
                	 	nxt.addCommand(new Commands(CommandNames.MOVEFORWARD,200,5));
                		System.out.println('+');
            		}
            	});
        	try{
        	Thread.sleep(2000);
        	}
        	catch (Exception e){};
        	nxt.run();                             
            
        	
            
        }

}
