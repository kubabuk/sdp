package vision;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import au.edu.jcu.v4l4j.CaptureCallback;
import au.edu.jcu.v4l4j.ControlList;
import au.edu.jcu.v4l4j.FrameGrabber;
import au.edu.jcu.v4l4j.V4L4JConstants;
import au.edu.jcu.v4l4j.VideoDevice;
import au.edu.jcu.v4l4j.VideoFrame;
import au.edu.jcu.v4l4j.examples.SimpleViewer;
import au.edu.jcu.v4l4j.exceptions.StateException;
import au.edu.jcu.v4l4j.exceptions.V4L4JException;

public class VisionRunner implements CaptureCallback, WindowListener {
	static String device = "/dev/video0";
	static int width = 640;
	static int height = 480;
	static int channel = 0;
	static int std = V4L4JConstants.STANDARD_PAL;
	static int compressionQuality = 80;

    private VideoDevice     videoDevice;
    private FrameGrabber    frameGrabber;

    private JLabel          label;
    private JFrame          frame;



    public static void main(String args[]){

            SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                            new VisionRunner();
                    }
            });
    }

    /**
     * Builds a WebcamViewer object
     * @throws V4L4JException if any parameter if invalid
     */
    public VisionRunner(){
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
            
            // start capture
            try {
                    frameGrabber.startCapture();
            } catch (V4L4JException e){
                    System.err.println("Error starting the capture");
                    e.printStackTrace();
            }
    }

    /**
     * Initialises the FrameGrabber object
     * @throws V4L4JException if any parameter if invalid
     */
    private void initFrameGrabber() throws V4L4JException{
            videoDevice = new VideoDevice(device);
            //ControlList controlList = videoDevice.getControlList();
            //System.out.println(controlList.getList());
            frameGrabber = videoDevice.getJPEGFrameGrabber(width, height, channel, std, 80);
            frameGrabber.setCaptureCallback(this);
            width = frameGrabber.getWidth();
            height = frameGrabber.getHeight();
            System.out.println("Starting capture at "+width+"x"+height);
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
            
            // draw the new frame onto the JLabel
            label.getGraphics().drawImage(frame.getBufferedImage(), 0, 0, width, height, null);
            
            // recycle the frame
            frame.recycle();
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
