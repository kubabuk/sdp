import geometry.Point;
import geometry.Vector;

public class Robot {
	
	private Point coordinate;
	private Vector orientation;
	private int speed;
	private boolean carryball;
	
	public Point getPos() 
	{
		return coordinate;
	}
	
	public void setPos(Point xy) 
	{
		 this.coordinate = xy;
	}
	
	public Vector getDir()
	{
		return orientation;
	}
	
	public void setDir(Vector xy)
	{
		this.orientation = xy;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public void setSpeed(int v)
	{
		this.speed = v;
	}
	
	public boolean isCB()
	{
		return carryball;
	}
	
	public void setCB(boolean a) 
	{ 
		this.carryball = a; 
	}
	
	public boolean isHandsome()
	{
		return true;
	}
	
}
