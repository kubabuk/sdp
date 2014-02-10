package geometry;


//Helper Object for Vector and Points not to be called outside geometry.
public class Line {
 
	private double a ,b ,c , theta;
	
	public Line (Vector v){
		double x1 = v.getOrigin().getX();
		double y1 = v.getOrigin().getY();
		double x2 = v.getDestination().getX();
		double y2 = v.getDestination().getY();
		this.theta = Angle.toRangePI(v.getOrientation());
		this.setA(y2 - y1);
		this.b = x1 - x2;
		this.c = x2*y1 - y2*x1;
		
	}

	public double a() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}
	
	public double b() {
		return b;
	}
	public double c() {
		return c;
	}
	public double getTheta(){
		return theta;
	}
}
