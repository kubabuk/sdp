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
	public Vector latitude(){
		
		//returns a unit vector in the direction of the latitude
		return new Vector (this,0,1);
	}
	
	public Vector longtitude(){
		
		//returns a unit vector in the direction of the longtitude 
		return new Vector (this, Math.PI/2,1);
	}
	
	public static double pointDistance(Point a , Point b){
		Vector v = new Vector(a,b);
		return v.getMagnitude();
	}
	
	public double distanceFromLine(Vector v){
		Line l = v.getLine();
		
		return (Math.abs(l.a()*x + l.b()*y +l.c())/Math.sqrt(l.a()*l.a()+l.b()*l.b()));
	}
	
	public boolean isIn(Area a){
		return true;
	}

}
