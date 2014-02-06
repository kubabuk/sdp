package vision;

import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.security.acl.LastOwnerException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import world.World;

import au.edu.jcu.v4l4j.CaptureCallback;
import au.edu.jcu.v4l4j.Control;
import au.edu.jcu.v4l4j.FrameGrabber;
import au.edu.jcu.v4l4j.V4L4JConstants;
import au.edu.jcu.v4l4j.VideoDevice;
import au.edu.jcu.v4l4j.VideoFrame;
import au.edu.jcu.v4l4j.exceptions.StateException;
import au.edu.jcu.v4l4j.exceptions.V4L4JException;

public class VisionRunner implements CaptureCallback, WindowListener{
	static String device = "/dev/video0";
	static int width = 640;
	static int height = 480;
	static int channel = 0;
	static int std = V4L4JConstants.STANDARD_PAL;
	static int compressionQuality = 80;

    private VideoDevice     videoDevice;
    private FrameGrabber    frameGrabber;
    private ImageProcessor1	imageProcessor;
    
    private int frameCount;
    private int[] boundaries;

    private JLabel          label;
    private JFrame          frame;

    public static void main(String args[]){

//            SwingUtilities.invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
//                            new VisionRunner();
//                    }
//            });
    }

    /**
     * Builds a WebcamViewer object
     * @throws V4L4JException if any parameter if invalid
     */
    public VisionRunner(World world){
            // Initialise video device and frame grabber
            try {
                    initFrameGrabber();
            } catch (V4L4JException e1) {
                    System.err.println("Error setting up capture");
                    e1.printStackTrace();
                    
                    // cleanup and exit
                    cleanupCapture();
                    return;
            }
            
            // create and initialise UI
            initGUI();
            imageProcessor = new ImageProcessor1(world);
            frameCount = 0;
            boundaries = new int[4];
            boundaries[0] = 10;
            boundaries[1] = 10;
            boundaries[2] = 100;
            boundaries[3] = 100;
            // start capture
            try {
                    frameGrabber.startCapture();
            } catch (V4L4JException e){
                    System.err.println("Error starting the capture");
                    e.printStackTrace();
            }
            
            /* this is where the camera settings can be modified;
             * variables are in the following order:
             * contrast -> from 0 up to 127
             * brightness -> from 0 up to 255
             * hue -> from -127 up to +127
             * saturatiom -> from 0 up to 127
             * chroma gain
             */
            try {
                updateVideoSettings(videoDevice,120,200,35,80,100);
            } catch (Exception e) {
            	System.err.println("error updating video settings");
            	e.printStackTrace();
            }
            
    }

    /**
     * Initialises the FrameGrabber object
     * @throws V4L4JException if any parameter if invalid
     */
    private void initFrameGrabber() throws V4L4JException{
            videoDevice = new VideoDevice(device);
            frameGrabber = videoDevice.getJPEGFrameGrabber(width, height, channel, std, 80);
            frameGrabber.setCaptureCallback(this);
            width = frameGrabber.getWidth();
            height = frameGrabber.getHeight();

            System.out.println("Starting capture at "+width+"x"+height);
    }
    
    private void updateVideoSettings(VideoDevice v, int contrast, int brightness,
    		int hue, int saturation, int chroma_gain)
    {
    	try
    	{
	        List<Control> controlList = v.getControlList().getList();
	        for(Control c : controlList)
	        {
	        	if(c.getName().equals("Contrast"))
	        		c.setValue(contrast);
	        	else if(c.getName().equals("Brightness"))
	        		c.setValue(brightness);
	        	else if(c.getName().equals("Hue"))
	        		c.setValue(hue);
	        	else if(c.getName().equals("Saturation"))
	        		c.setValue(saturation);
	        	else if(c.getName().equals("Chroma Gain"))
	        		c.setValue(chroma_gain);
	        	else if(c.getName().equals("Chroma AGC"))
	        		c.setValue(1);
	        }
    	} catch(V4L4JException e) {
    		System.err.println("Cannot update video settings: " + e.getMessage());
    		e.printStackTrace();
    	}
    	
    	v.releaseControlList();
    }

    /** 
     * Creates the UI components and initialises them
     */
    private void initGUI(){
            frame = new JFrame();
            label = new JLabel();
            frame.getContentPane().add(label);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.addWindowListener(this);
            frame.setVisible(true);
            frame.setSize(width, height);       
    }
    
    /**
     * this method stops the capture and releases the frame grabber and video device
     */
    private void cleanupCapture() {
            try {
                    frameGrabber.stopCapture();
            } catch (StateException ex) {
                    // the frame grabber may be already stopped, so we just ignore
                    // any exception and simply continue.
            }

            // release the frame grabber and video device
            videoDevice.releaseFrameGrabber();
            videoDevice.release();
    }

    /**
     * Catch window closing event so we can free up resources before exiting
     * @param e
     */
    public void windowClosing(WindowEvent e) {
            cleanupCapture();

            // close window
            frame.dispose();            
    }

    @Override
    public void exceptionReceived(V4L4JException e) {
            // This method is called by v4l4j if an exception
            // occurs while waiting for a new frame to be ready.
            // The exception is available through e.getCause()
            e.printStackTrace();
    }

    @Override
    public void nextFrame(VideoFrame frame) {
            // This method is called when a new frame is ready.
            // Don't forget to recycle it when done dealing with the frame.
    		
    	BufferedImage tmp = frame.getBufferedImage();
    	if (frameCount % 1000 < 10){
        	boundaries = imageProcessor.getBoundaries(tmp);	
    	}
    	Image img = imageProcessor.trackWorld(tmp, boundaries[0], boundaries[2], boundaries[1], boundaries[3]);
    		
            // draw the new frame onto the JLabel
        label.getGraphics().drawImage(img, 0, 0, width, height, null);
            
            // recycle the frame
        frame.recycle();
        frameCount++;
    }

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
