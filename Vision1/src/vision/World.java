package vision;

import java.awt.Image;
import java.awt.image.BufferedImage;
import geometry.Point;
import geometry.Vector;

/* This class is meant to be used as a helper in the imageprocessor
 * makes everithing more object oriented
 */

public class World {
	
	// image generated only once
	private Image img;
	
	private Point ball ;
	private  Point yellowLeft , yellowRight;
	private  Point blueLeft , blueRight;
	// private double ballSpeedX, ballSpeedY;
	// add everything for the pitch here
	
	public World()
	{
		// initialize the world here
	}
	
	// methods for ball
	public void setBallXY (Point ballXY)
	{
		this.ball = ballXY;
	}
	
	public Point getBall ()
	{
		return this.ball;
	}

	
	// methods for yellow robot LEFT
	public void setYellowLeft (Point yellowLeftXY)
	{
		this.yellowLeft = yellowLeftXY;
	}
	
	public Point getYellowLeft()
	{
		return this.yellowLeft;
	}
	
	// methods for yellow robot RIGHT
	
	public void setYellowRight (Point yellowRightXY)
	{
		this.yellowRight = yellowRightXY;
	}
	
	public Point getYellowRight()
	{
		return this.yellowRight;
	}

	// methods for blue robot LEFT
	public void setBlueLeft (Point blueLeftXY)
	{
		this.blueLeft = blueLeftXY;
	}
	
	public Point getBlueLeft()
	{
		return this.blueLeft;
	}
	
	// methods for blue robot RIGHT
	
	public void setBlueRight (Point blueRightXY)
	{
		this.blueRight = blueRightXY;
	}
	
	public Point getBlueRight()
	{
		return this.blueRight;
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
	
	/*
	 * 	public void setBallSpeed(double x, double y)
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
	 */

}
