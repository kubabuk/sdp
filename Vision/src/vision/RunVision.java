package vision;

import javax.swing.UIManager;

//import strategy.calculations.GoalInfo;
//import vision.gui.VisionGUI;
//import world.state.WorldState;
import au.edu.jcu.v4l4j.V4L4JConstants;

/**
 * The main class used to run the vision system. Creates the control GUI, and
 * initialises the image processing.
 * 
 * @author s0840449
 */
public class RunVision {
        /**
         * The main method for the class. Creates the control GUI, and initialises
         * the image processing.
         * 
         * @param args
         *            Program arguments. Not used.
         */
        public static void main(String[] args) {
                try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                        e.printStackTrace();
                }
                
                String videoDevice = "/dev/video0";
                int width = 640;
                int height = 480;
                int channel = 0;
                int videoStandard = V4L4JConstants.STANDARD_PAL;
                int compressionQuality = 80;

                try {
                        VideoStream vStream = new VideoStream(videoDevice, width, height,
                                        channel, videoStandard, compressionQuality);
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}