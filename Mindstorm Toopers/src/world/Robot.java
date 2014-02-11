package world;
import geometry.Point;
import geometry.Vector;

public class Robot {
	
	private Point position;
	private Vector orientation;
	private int speed;
	private boolean carryball;
	
	public Robot(Point position, Vector orientation)
	{
		this.position = position;
		this.orientation = orientation;
	}
	
	public Point getPos() 
	{
		return position;
	}
	
	public void setPos(Point position) 
	{
		 this.position = position;
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
