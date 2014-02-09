package geometry;


//Initial Code by SDP Group 4 2013


import java.lang.Math;


public class Vector {
	/**
	 * The Vector class is meant to be used to track trajectories. 
	 * A Vector here is not used in the same way as in linear algebra,
	 * but rather to fashion resembling classical geometry.
	 * The Vector Objects stores the starting and ending point of the vector,
	 * the angle it forms with the x'x axis, its magnitude and the coordinates x,y
	 * of the ending point of an equal vector starting at (0,0). i.e its coordinates in the Linear Algebra sense.
	 * 
	 * Take note of the fact that the vector object can essentially be used to define a new coordinate system (similar to the main one) but centered around the origin point of the vector (in our case the robot).
	 */
	private Point origin;
	private Point destination;
	private double r;
	private double theta;
	private double x;
	private double y;

	
	//get functions
	
	public double getX() { return x; }
	
	public double getY() { return y; }
	
	public Point getOrigin(){ return origin;}
	
	public Point getDestination(){ return destination;}
	
	public double getOrientation() {return theta;}
	
	public double getMagnitude() {
		return r;
	}
	
	public Vector(Point begin , Point end) {
		
		//Constructor for Vector that takes its starting and ending points as args
		
		this.origin  = begin;
		this.destination = end;
		this.x = end.getX()-begin.getX();
		this.y = end.getY()-begin.getY();
		this.r=(Math.sqrt(x*x +y*y));
		this.theta=Math.atan2(y,x);
	}
	
	public Vector(Point origin, double length, double bearing){
		//Defines a UNIT vector given it's starting point and orientation (angle with xx' axis)
		this.theta = bearing;
		this.origin = origin;
		this.r = length;
		this.x = r * Math.cos(bearing);
		this.y = r * Math.sin(bearing);
		this.destination = new Point(origin.getX()+x,origin.getY()+y);
	}
	
	public String toString() {
		return String.format("(%.2f, %.2f)", x, y);
	}
	
	public boolean isParallel (Vector v){
		return Math.tan(theta)==Math.tan(v.getOrientation());
	}
	
	public boolean isColinear (Vector v){
		return this.isParallel(v) && this.origin.isColinear(v);
	}
	
	public boolean sameSegment (Vector v){
		return this.isColinear(v) && this.r==v.getMagnitude();
	}
	
	public double angleFrom (Vector v){
		//angle between vectors starting counting from Vector V.
		
		return Angle.toRange2PI(this.theta-v.getOrientation());
		
		
	}
	
	// To be used from the Command module for calculating turns.
	
	public static double innerAngle(Vector v1 , Vector v2){
		double theta1 = v1.getOrientation();
		double theta2 = v2.getOrientation();
		return Math.acos(Math.cos(theta1-theta2));
	}
	
	public Line getLine(){
		return new Line(this);
		
	}
		
	//TODO: Figure out what operations we're going to need and add methods for them
	
	/*

	******Under Construction ******

	
	public static double dotProduct(Vector v1, Vector v2) {
		return v1.x * v2.x + v1.y * v2.y;
	}
	public double dotProduct(Vector v) {
		return dotProduct(this, v);
	}
	
	public static Vector add(Vector v1, Vector v2) {
		return new Vector(v1.x + v2.x, v1.y + v2.y);
	}
	public Vector add(Vector v) {
		return add(this, v);
	}
	
	public static Vector subtract(Vector v1, Vector v2) {
		return new Vector(v1.x - v2.x, v1.y - v2.y);
	}
	public Vector subtract(Vector v) {
		return subtract(this, v);
	}
	
	public static Vector multiply(Vector v1, Vector v2) {
		return new Vector(v1.x * v2.x, v1.y * v2.y);
	}
	public Vector multiply(Vector v) {
		return multiply(this, v);
	}
	
	public static Vector multiply(Vector v, double m) {
		return new Vector(v.x * m, v.y * m);
	}
	public Vector multiply(double m) {
		return multiply(this, m);
	}
	
	public static Vector divide(Vector v1, Vector v2) {
		return new Vector(v1.x / v2.x, v1.y / v2.y);
	}
	public Vector divide(Vector v) {
		return divide(this, v);
	}
	public static Vector divide(Vector v, double d) {
		return new Vector(v.x / d, v.y / d);
	}
	public Vector divide(double d) {
		return divide(this, d);
	}
	
	public static Vector square(Vector v) {
		return multiply(v, v);
	}
	public Vector square() {
		return square(this);
	}
	
	public static double distanceSquared(Vector v1, Vector v2) {
		return magnitudeSquared(subtract(v1, v2));
	}
	public double distanceSquared(Vector v) {
		return distanceSquared(this, v);
	}
	
	public static double distance(Vector v1, Vector v2) {
		return magnitude(subtract(v1, v2));
	}
	public double distance(Vector v) {
		return distance(this, v);
	}
	
	public static double angle(Vector v) {
		return mod((Math.PI / 2) - Math.atan2(-v.y, v.x));
	}
	public double angle() {
		return angle(this);
	}
	
	public static double angleToVector(Vector v1, Vector v2) {
		return mod(v2.angle() - v1.angle());
	}
	public double angleToVector(Vector v) {
		return angleToVector(this, v);
	}
	
	public static double angleBetweenPoints(Vector point, Vector target) {
		return target.subtract(point).angle();
	}
	public double angleBetweenPoints(Vector target) {
		return angleBetweenPoints(this, target);
	}

	private static double mod(double d) {
		double result = d % (Math.PI*2);
		if (result < 0) {
			result += Math.PI*2;
		}
		return result;
	}


	public static Vector intersectX(Vector position, Vector direction, double x) {
		// Cases where it never intersects
		if (direction.x > 0 && position.x > x) return null;
		if (direction.x < 0 && position.x < x) return null;
		double m = (x - position.x) / direction.x;
		return new Vector(x, position.y + m * direction.y);
	}
	public Vector intersectX(Vector direction, double x) {
		return intersectX(this, direction, x);
	}

	public static Vector intersectY(Vector position, Vector direction, double y) {
		// Cases where it never intersects
		if (direction.y > 0 && position.y > y) return null;
		if (direction.y < 0 && position.y < y) return null;
		double m = (y - position.y) / direction.y;
		return new Vector(position.x + m * direction.y, y);
	}
	public Vector intersectY(Vector direction, double y) {
		return intersectY(this, direction, y);
	}
*/

	public static void main(String[] args){

	}
	
}

