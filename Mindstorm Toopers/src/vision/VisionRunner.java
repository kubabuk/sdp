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

import vision.gui.CameraSettingsPanel;
import vision.gui.VisionGUI;
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
    
    private int frameCount;
    private int[] boundaries;

    private JLabel          label;
    private VisionGUI          frame;

    private int contrast = 127;
    private int brightness = 200;
    private int hue = 0;
    private int saturation = 127;
    private int chroma_gain = 0;
    private int chroma_agc = 0;

    private World world;
    /**
     * Builds a WebcamViewer object
     * @throws V4L4JException if any parameter if invalid
     */
    public VisionRunner(World world){
    		this.world = world;
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
       /*     try {
                updateVideoSettings(videoDevice,120,180,35,80,120);
            } catch (Exception e) {
            	System.err.println("error updating video settings");
            	e.printStackTrace();
            }
            
            use set/get methods instead
        */
            
    }

    /**
     * Initialises the FrameGrabber object
     * @throws V4L4JException if any parameter if invalid
     */
    private void initFrameGrabber() throws V4L4JException{
            videoDevice = new VideoDevice(device);
            updateVideoSettings();
            frameGrabber = videoDevice.getJPEGFrameGrabber(width, height, channel, std, 80);
            frameGrabber.setCaptureCallback(this);
            width = frameGrabber.getWidth();
            height = frameGrabber.getHeight();

            System.out.println("Starting capture at "+width+"x"+height);
    }
    
// ##############
    
    public void updateVideoSettings()
    {
    	try
    	{
	        List<Control> controlList = videoDevice.getControlList().getList();
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
	        		c.setValue(chroma_agc);
	        }
    	} catch(V4L4JException e) {
    		System.err.println("Cannot update video settings: " + e.getMessage());
    		e.printStackTrace();
    	}
    	
    	videoDevice.releaseControlList();
    }
    
    
    public int getSaturation() {
		return saturation;
	}

	public void setSaturation(int saturation) {
		this.saturation = saturation;
	}

	public int getBrightness() {
		return brightness;
	}

	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}

	public int getContrast() {
		return contrast;
	}

	public void setContrast(int contrast) {
		this.contrast = contrast;
	}

	public int getHue() {
		return hue;
	}

	public void setHue(int hue) {
		this.hue = hue;
	}

	public int getChromaGain() {
		return chroma_gain;
	}

	public void setChromaGain(int chromaGain) {
		this.chroma_gain = chromaGain;
	}

	public boolean getChromaAGC() {
		return (chroma_agc == 1) ? true : false;
	}

	public void setChromaAGC(boolean chromaAGC) {
		this.chroma_agc = chromaAGC ? 1 : 0;
	}
	
	
	
	// ###########################################


    /** 
     * Creates the UI components and initialises them
     */
    private void initGUI(){
            frame = new VisionGUI(this.width, this.height, this, this.world);
//            label = new JLabel();
//            frame.getContentPane().add(label);
//            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.addWindowListener(this);
//            frame.setVisible(true);
//            frame.setSize(width, height);      
//    	CameraSettingsPanel panel = new CameraSettingsPanel(this);
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
    	    		
            // draw the new frame onto the JLabel
//    	label.getGraphics().drawImage(img, 0, 0, width, height, null);
        this.frame.sendFrame(tmp, 20, frameCount);
            
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
