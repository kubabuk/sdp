package vision;

import java.awt.Image;
import java.awt.image.BufferedImage;

/* This class is meant to be used as a helper in the imageprocessor
 * makes everithing more object oriented
 */

public class World {
	
	// image generated only once
	private Image img;
	
	private double ballX, ballY;
	private  double yellowX_left, yellowY_left;
	private  double yellowX_right, yellowY_right;
	private double blueX_left, blueY_left, blueX_right, blueY_right;
	private double ballSpeedX, ballSpeedY;
	// add everything for the pitch here
	
	public World(BufferedImage img)
	{
		this.img=img;
		// initialize everything with -1.0 to make it easier to check if
		// the camera detected the objects
		this.ballX=-1.0;
		this.ballY=-1.0;
		this.yellowX_left=-1.0;
		this.yellowY_left=-1.0;
		this.yellowX_right=-1.0;
		this.yellowY_right=-1.0;
		this.blueX_left=-1.0;
		this.blueY_left=-1.0;
		this.blueX_right=-1.0;
		this.blueY_right=-1.0;
		this.ballSpeedX = 0;
		this.ballSpeedY = 0;
	}
	
	// methods for ball
	public void setBallXY (double x, double y)
	{
		this.ballX = x;
		this.ballY = y;
	}
	
	public double getBallX ()
	{
		return this.ballX;
	}
	
	public double getBallY ()
	{
		return this.ballY;
	}
	
	// methods for yellow robot on the left
	public void setYellowXY_left (double x, double y)
	{
		this.yellowX_left = x;
		this.yellowY_left = y;
	}
	
	public double getYellowX_left ()
	{
		return this.yellowX_left;
	}
	
	public double getYellowY_left ()
	{
		return this.yellowY_left;
	}
	
	// methods for yellow robot on the right
	public void setYellowXY_right (double x, double y)
	{
		this.yellowX_right = x;
		this.yellowY_right = y;
	}
	
	public double getYellowX_right ()
	{
		return this.yellowX_right;
	}
	
	public double getYellowY_right ()
	{
		return this.yellowY_right;
	}
	
	// methods for blue robot on the left
	public void setBlueXY_left (double x, double y)
	{
		this.blueX_left = x;
		this.blueY_left = y;
	}
	
	public double getBlueX_left ()
	{
		return this.blueX_left;
	}
	
	public double getBlueY_left ()
	{
		return this.blueY_left;
	}
	
	// methods for blue robot
	public void setBlueXY_right (double x, double y)
	{
		this.blueX_right = x;
		this.blueY_right = y;
	}
	
	public double getBlueX_right ()
	{
		return this.blueX_right;
	}
		
	public double getBlueY_right ()
	{
		return this.blueY_right;
	}
	
	// speed methods
	public void setBallSpeed(double x, double y)
	{
		this.ballSpeedX = x;
		this.ballSpeedY = y;
	}
	
	public double getBallSpeedX()
	{
		return this.ballSpeedX;
	}
	
	public double getBallSpeedY()
	{
		return this.ballSpeedY;
	}
		
	// methods for the video image
	public void setImage(Image img)
	{
		this.img = img;
	}
	
	public Image getImage()
	{
		return this.img;
	}

}
