package world;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import vision.VisionRunner;
import geometry.Point;
import geometry.Vector;
import geometry.Area;

/* This class is meant to be used as a helper in the imageprocessor
 * makes everithing more object oriented
 */

public class World {
	
	// image generated only once
	private Image img;
	
	private Ball ballObject ;
	private Robot attacker,defender;
	private  Point yellowLeft , yellowRight;
	private  Point blueLeft , blueRight;
	private Point ball;
	
	private int pitchWidth, pitchHeight, pitchCentre;
	private Vector yLeft, yRight, bLeft, bRight;
	private int pitchLeft, pitchTop;
	
	//TODO: For vision. Maximum dimensions of the pitch! you might want to make these constants.
	private double maxY, minY, maxX, minX;
	//TODO: For Aris: finish the Area definition
	private Area areaA , areaB, areaC, areaD;
	// private double ballSpeedX, ballSpeedY;
	// add everything for the pitch here
	
	public World()
	{
		// initialize the world here
		runVision(this);
		ballObject = new Ball(new Point(10,10));
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
		ballObject.setPos(new Point(x,y));
		this.ball = new Point(x,y);
	}
	
	public void setBallTrajectory (Vector ballTrajectory){
		ballObject.setDir(ballTrajectory);
	}
	
	public Point getBallPos ()
	{
		return this.ball;
	}

	public Ball getBall(){
		return this.ballObject;
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
		int x = (int) ((100 * (yellowLeftXY.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((100 * (yellowLeftXY.getY() - this.pitchTop)) / this.pitchWidth);
		this.yellowLeft = new Point(x,y);
	}
	
	public void setVectorYellowLeft(Point dot)
	{
		int x = (int) ((100 * (dot.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((100 * (dot.getY() - this.pitchTop)) / this.pitchWidth);
		this.setyLeft(new Vector(new Point(x,y), yellowLeft));
	}
	
	public void setVectorYellowRight(Point dot)
	{
		int x = (int) ((100 * (dot.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((100 * (dot.getY() - this.pitchTop)) / this.pitchWidth);
		this.setyRight(new Vector(new Point(x,y), yellowRight));
	}
	
	public Point getYellowLeft()
	{
		return this.yellowLeft;
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

	// methods for blue robot LEFT
	public void setBlueLeft (Point blueLeftXY)
	{
		int x = (int) ((100 * (blueLeftXY.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((100 * (blueLeftXY.getY() - this.pitchTop)) / this.pitchWidth);
		this.blueLeft = new Point(x,y);
	}
	
	public void setVectorBlueLeft(Point dot)
	{
		int x = (int) ((100 * (dot.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((100 * (dot.getY() - this.pitchTop)) / this.pitchWidth);
		this.setbLeft(new Vector(new Point(x,y), blueLeft));
	}
	
	public Point getBlueLeft()
	{
		return this.blueLeft;
	}
	
	// methods for blue robot RIGHT
	
	public void setBlueRight (Point blueRightXY)
	{
		int x = (int) ((100 * (blueRightXY.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((100 * (blueRightXY.getY() - this.pitchTop)) / this.pitchWidth);
		this.blueRight = new Point(x,y);
	}
	
	public void setVectorBlueRight(Point dot)
	{
		int x = (int) ((100 * (dot.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((100 * (dot.getY() - this.pitchTop)) / this.pitchWidth);
		this.setbRight(new Vector(new Point(x,y), blueRight));
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

	public void setyLeft(Vector yLeft) {
		this.yLeft = yLeft;
	}

	public Vector getyLeft() {
		return yLeft;
	}

	public void setyRight(Vector yRight) {
		this.yRight = yRight;
	}

	public Vector getyRight() {
		return yRight;
	}

	public void setbLeft(Vector bLeft) {
		this.bLeft = bLeft;
	}

	public Vector getbLeft() {
		return bLeft;
	}

	public void setbRight(Vector bRight) {
		this.bRight = bRight;
	}

	public Vector getbRight() {
		return bRight;
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
