import geometry.Point;
import geometry.Vector;

public class Robot {
	
	private Point coordinate;
	private Vector orientation;
	private int speed;
	private boolean carryball;
	
	public Robot(Point coordinate, Vector orientation)
	{
		this.coordinate = coordinate;
		this.orientation = orientation;
	}
	
	public Point getPos() 
	{
		return coordinate;
	}
	
	public void setPos(Point postion) 
	{
		 this.coordinate = position;
	}
	
	public Vector getDir()
	{
		return orientation;
	}
	
	public void setDir(Vector direction)
	{
		this.orientation = direction;
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
	
	public void setCB(boolean carryball) 
	{ 
		this.carryball = carryball; 
	}
	
	public boolean isHandsome()
	{
		return true;
	}
	
}
