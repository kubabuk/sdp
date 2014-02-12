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
	
	private Boolean color;
	private Boolean direction;
	
	private int gridConstant;
	
	// Used to track the direction of ball movement.
	private Point lastBallLocation;
	private int count;
	
	//TODO: For vision. Maximum dimensions of the pitch! you might want to make these constants.
	private double maxY, minY, maxX, minX;
	//TODO: For Aris: finish the Area definition
	private Area areaA , areaB, areaC, areaD;
	// private double ballSpeedX, ballSpeedY;
	// add everything for the pitch here
	
	// Color - true means yellow, false means blue
	// Direction - true mean right, false means left
	public World(Boolean color, Boolean direction)
	{
		// initialize the world here
		runVision(this);
		this.color = color;
		this.direction = direction;
		Point start = new Point(10,10);
		Vector startVector = new Vector(start, new Point(11,10));
		ballObject = new Ball(start);
		yLeft = startVector;
		attacker = new Robot(start, new Vector(start,start));
		defender = new Robot(start, new Vector(start,start));
		lastBallLocation = start;
		gridConstant = 474;
		count = 0;
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
		int x = (int) ((gridConstant * (ballXY.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((gridConstant * (ballXY.getY() - this.pitchTop)) / this.pitchWidth);
		ballObject.setPos(new Point(x,y));
		this.ball = new Point(x,y);
	}
	
	public void setBallDirection (){
		if (count == 10){
			Vector direction = new Vector(lastBallLocation,ballObject.getPos());
			ballObject.setDir(direction);
			if (Math.abs(direction.getX() + direction.getY()) > 2){
				ballObject.setMoving(true);
			} else {
				ballObject.setMoving(false);
			}
			lastBallLocation = ballObject.getPos();
			count = 0;
		} else {
			count++;
		}
	}
	
	public Vector getBallDirection(){
		return ballObject.getDir();
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
		int x = (int) ((gridConstant * (yellowLeftXY.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((gridConstant * (yellowLeftXY.getY() - this.pitchTop)) / this.pitchWidth);
		this.yellowLeft = new Point(x,y);
		if (color){
			if (direction){
				defender.setPos(new Point(x,y));
			} else {
				attacker.setPos(new Point(x,y));
			}
		}
	}
	
	public void setVectorYellowLeft(Point dot)
	{
		int x = (int) ((gridConstant * (dot.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((gridConstant * (dot.getY() - this.pitchTop)) / this.pitchWidth);
		this.setyLeft(new Vector(new Point(x,y), yellowLeft));
		if (color){
			if (direction){
				defender.setDir(new Vector(new Point(x,y), yellowLeft));
			} else {
				attacker.setDir(new Vector(new Point(x,y), yellowLeft));
			}
		}
	}
	
	public void setVectorYellowRight(Point dot)
	{
		int x = (int) ((gridConstant * (dot.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((gridConstant * (dot.getY() - this.pitchTop)) / this.pitchWidth);
		this.setyRight(new Vector(new Point(x,y), yellowRight));
		if (color){
			if (direction){
				attacker.setDir(new Vector(new Point(x,y), yellowRight));
			} else {
				defender.setDir(new Vector(new Point(x,y), yellowRight));
			}
		}
	}
	
	public Point getYellowLeft()
	{
		return this.yellowLeft;
	}
	
	// methods for yellow robot RIGHT
	
	public void setYellowRight (Point yellowRightXY)
	{
		int x = (int) ((gridConstant * (yellowRightXY.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((gridConstant * (yellowRightXY.getY() - this.pitchTop)) / this.pitchWidth);
		this.yellowRight = new Point(x,y);
		if (color){
			if (direction){
				attacker.setPos(new Point(x,y));
			} else {
				defender.setPos(new Point(x,y));
			}
		}
	}
	
	public Point getYellowRight()
	{
		return this.yellowRight;
	}

	// methods for blue robot LEFT
	public void setBlueLeft (Point blueLeftXY)
	{
		int x = (int) ((gridConstant * (blueLeftXY.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((gridConstant * (blueLeftXY.getY() - this.pitchTop)) / this.pitchWidth);
		this.blueLeft = new Point(x,y);
	}
	
	public void setVectorBlueLeft(Point dot)
	{
		int x = (int) ((gridConstant * (dot.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((gridConstant * (dot.getY() - this.pitchTop)) / this.pitchWidth);
		this.setbLeft(new Vector(new Point(x,y), blueLeft));
	}
	
	public Point getBlueLeft()
	{
		return this.blueLeft;
	}
	
	// methods for blue robot RIGHT
	
	public void setBlueRight (Point blueRightXY)
	{
		int x = (int) ((gridConstant * (blueRightXY.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((gridConstant * (blueRightXY.getY() - this.pitchTop)) / this.pitchWidth);
		this.blueRight = new Point(x,y);
	}
	
	public void setVectorBlueRight(Point dot)
	{
		int x = (int) ((gridConstant * (dot.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((gridConstant * (dot.getY() - this.pitchTop)) / this.pitchWidth);
		this.setbRight(new Vector(new Point(x,y), blueRight));
	}
	
	public Point getBlueRight()
	{
		return this.blueRight;
	}
	
	public Point getAttackerPos(){
		return attacker.getPos();
	}
	
	public Point getDefenderPos(){
		return defender.getPos();
	}
	
	public Vector getAttackerDir(){
		return attacker.getDir();
	}
	
	public Vector getDefenderDir(){
		return defender.getDir();
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
