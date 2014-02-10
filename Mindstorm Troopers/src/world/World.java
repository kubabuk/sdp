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
	private Vector yLeft, yRight, bLeft, bRigth;
	
	private int pitchWidth, pitchHeight, pitchCentre;
	
	//TODO: For vision. Maximum dimensions of the pitch! you might want to make these constants.
	private double maxY, minY, maxX, minX;
	//TODO: For Aris: finish the Area definition
	private Area areaA , areaB, areaC, areaD;
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
		this.yellowRight = yellowRightXY;
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
		this.blueLeft = blueLeftXY;
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
		this.blueRight = blueRightXY;
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
	
	public Area getOurDefenderArea(){ //TODO!!
		return areaA;
	}

	public double getMaxX() {
		return maxX;
	}

	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}

	public double getMaxY() {
		return maxY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}

	public double getMinY() {
		return minY;
	}

	public void setMinY(double minY) {
		this.minY = minY;
	}

	public double getMinX() {
		return minX;
	}

	public void setMinX(double minX) {
		this.minX = minX;
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
