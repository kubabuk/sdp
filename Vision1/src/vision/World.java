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
	private  Point yellowAt , yellowDef;
	private  Point blueAt , blueDef;
	// private double ballSpeedX, ballSpeedY;
	// add everything for the pitch here
	
	public World(BufferedImage img)
	{
		this.img=img;
		// initialize everything with -1.0 to make it easier to check if
		// the camera detected the objects
	}
	
	// methods for ball
	public void setBallXY (Point ballXY)
	{
		this.ball = ballXY;
	}
	
	public Point getBallX ()
	{
		return this.ball;
	}

	
	// methods for yellow Attacker robot.
	public void setYellowAttacker (Point yellowAttackerXY)
	{
		this.yellowAt = yellowAttackerXY;
	}
	
	public Point getYellowAttacker()
	{
		return this.yellowAt;
	}
	
	// methods for yellow Defender.
	
	public void setYellowDefender (Point yellowDefenderXY)
	{
		this.yellowDef = yellowDefenderXY;
	}
	
	public Point getYellowDefender()
	{
		return this.yellowDef;
	}

	// methods for blue Attacker robot.
	public void setBlueAttacker (Point blueAttackerXY)
	{
		this.blueAt = blueAttackerXY;
	}
	
	public Point getBlueAttacker()
	{
		return this.blueAt;
	}
	
	// methods for blue Defender robot.
	
	public void setBlueDefender (Point blueDefenderXY)
	{
		this.blueDef = blueDefenderXY;
	}
	
	public Point getBlueDefender()
	{
		return this.blueDef;
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
