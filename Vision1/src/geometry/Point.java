package geometry;
import java.lang.Math;
public class Point {

	private final double x;
	private final double y;

	public Point(double x, double y){
		this.x = x;
		this.y = y;}

	public double getX() { return x; }
	public double getY() { return y; }

	public String toString()
		{return "(" + getX() + "," + getY() + ")" ;}
	
	public boolean equals(Point p) {
		return (this.x == p.getX() && this.y == p.getY());
	}
	public static boolean colinear(Point a, Point b, Point c){
		Vector v1 = new Vector(a,b);
		Vector v2 = new Vector(a,c);
		double angle = v1.getOrientation()-v2.getOrientation();
		return Math.sin(angle)==0;
	}
	public boolean isColinear(Vector v){
		return colinear(this, v.getOrigin(), v.getDestination());
	}
	}
