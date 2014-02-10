package world;

import java.awt.Image;
import java.awt.image.BufferedImage;
import geometry.Point;
import geometry.Vector;
import geometry.Area;

/* This class is meant to be used as a helper in the imageprocessor
 * makes everithing more object oriented
 */

public class World {
	
	// image generated only once
	private Image img;
	
	private Ball ball ;
	private Robot attacker,defender;
	private  Point yellowLeft , yellowRight;
	private  Point blueLeft , blueRight;
	
	private int pitchWidth, pitchHeight, pitchCentre;
	// private double ballSpeedX, ballSpeedY;
	// add everything for the pitch here
	
	public World()
	{
		// initialize the world here
	}
	
	// methods for ball
	public void setBallXY (Point ballXY)
	{
		Ball ball = new Ball(ballXY);
		this.ball = ball;
	}
	
	public Ball getBall ()
	{
		return this.ball;
	}

	
	public Robot getAttacker()
	{
		return this.attacker;
	}
	
	public Robot getDefender()
	{
		return this.defender;
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

	public void setWidth(int width) {
		this.pitchWidth = width;
	}

	public int getWidth() {
		return pitchWidth;
	}

	public void setHeight(int height) {
		this.pitchHeight = height;
	}

	public int getHeight() {
		return pitchHeight;
	}

	public void setPitchCentre(int pitchCentre) {
		this.pitchCentre = pitchCentre;
	}

	public int getPitchCentre() {
		return pitchCentre;
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
