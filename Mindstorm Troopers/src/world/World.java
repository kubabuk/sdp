package world;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import vision.VisionRunner;

import geometry.Point;
import geometry.Vector;

/* This class is meant to be used as a helper in the imageprocessor
 * makes everything more object oriented
 */

public class World {
	
	// image generated only once
	private Image img;
	
	private Point ball ;
	private  Point yellowLeft , yellowRight;
	private  Point blueLeft , blueRight;
	private Vector yLeft, yRight, bLeft, bRigth;
	
	private int pitchWidth, pitchHeight, pitchCentre;
	private int pitchLeft, pitchTop;
	// private double ballSpeedX, ballSpeedY;
	// add everything for the pitch here
	
	public World()
	{
		// Initialise the world here
		ball = new Point(100,100);
		runVision(this);
	}
	
	public void runVision(final World world){
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                        new VisionRunner(world);
                }
        });
	}
	
	// methods for ball
	public void setBallXY (Point ballXY)
	{
		int x = (int) ((100 * (ballXY.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((100 * (ballXY.getY() - this.pitchTop)) / this.pitchWidth);
		this.ball = new Point(x,y);
	}
	
	public Point getBall ()
	{
		return this.ball;
	}

	
	// methods for yellow robot LEFT
	public void setYellowLeft (Point yellowLeftXY)
	{
		int x = (int) ((100 * (yellowLeftXY.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((100 * (yellowLeftXY.getY() - this.pitchTop)) / this.pitchWidth);
		this.yellowLeft = new Point(x,y);
	}
	
	public Point getYellowLeft()
	{
		return this.yellowLeft;
	}
	
	// method to represent the LEFT yellow robot as a vector
	public void setVectorYellowLeft(Point dot)
	{
		int x = (int) ((100 * (dot.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((100 * (dot.getY() - this.pitchTop)) / this.pitchWidth);
		this.yLeft = new Vector(yellowLeft, new Point(x,y));
	}
	
	// methods for yellow robot RIGHT
	
	public void setYellowRight (Point yellowRightXY)
	{
		int x = (int) ((100 * (yellowRightXY.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((100 * (yellowRightXY.getY() - this.pitchTop)) / this.pitchWidth);
		this.yellowRight = new Point(x,y);
	}
	
	public Point getYellowRight()
	{
		return this.yellowRight;
	}
	
	// method to represent the RIGHT yellow robot as a vector
		public void setVectorYellowRight(Point dot)
		{
			int x = (int) ((100 * (dot.getX() - this.pitchLeft)) / this.pitchWidth);
			int y = (int) ((100 * (dot.getY() - this.pitchTop)) / this.pitchWidth);
			this.yRight = new Vector(yellowRight, new Point(x,y));
		}
		

	// methods for blue robot LEFT
	public void setBlueLeft (Point blueLeftXY)
	{
		int x = (int) ((100 * (blueLeftXY.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((100 * (blueLeftXY.getY() - this.pitchTop)) / this.pitchWidth);
		this.blueLeft = new Point(x,y);
	}
	
	public Point getBlueLeft()
	{
		return this.blueLeft;
	}
	
	// method to represent the LEFT blue robot as a vector
		public void setVectorBlueLeft(Point dot)
		{
			int x = (int) ((100 * (dot.getX() - this.pitchLeft)) / this.pitchWidth);
			int y = (int) ((100 * (dot.getY() - this.pitchTop)) / this.pitchWidth);
			this.bLeft = new Vector(blueLeft, new Point(x,y));
		}
		
	
	// methods for blue robot RIGHT
	
	public void setBlueRight (Point blueRightXY)
	{
		int x = (int) ((100 * (blueRightXY.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((100 * (blueRightXY.getY() - this.pitchTop)) / this.pitchWidth);
		this.blueRight = new Point(x,y);
	}
	
	public Point getBlueRight()
	{
		return this.blueRight;
	}
	
	// method to represent the RIGHT blue robot as a vector
		public void setVectorBlueRight(Point dot)
		{
			int x = (int) ((100 * (dot.getX() - this.pitchLeft)) / this.pitchWidth);
			int y = (int) ((100 * (dot.getY() - this.pitchTop)) / this.pitchWidth);
			this.bRigth = new Vector(blueRight, new Point(x,y));
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

	public void setPitchLeft(int pitchLeft) {
		this.pitchLeft = pitchLeft;
	}

	public int getPitchLeft() {
		return pitchLeft;
	}

	public void setPitchTop(int pitchTop) {
		this.pitchTop = pitchTop;
	}

	public int getPitchTop() {
		return pitchTop;
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
